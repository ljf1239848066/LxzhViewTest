package com.lxzh123.demo.pendingskip;

import com.lxzh123.demo.testview.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PendingSkipActivity1 extends Activity {

	Button btnSkip;
	private float startX;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pendingskip1);

		btnSkip = (Button) findViewById(R.id.button1);
		btnSkip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PendingSkipActivity1.this, PendingSkipActivity2.class));
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// �����Ļ��MotionEvent.ACTION_DOWN Ϊ��ָ�����Ļ�¼�
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// ��ȡ��ָ��ʼ���������
			startX = event.getX();
			// ��ָ̧�𣬽�������
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			// ��ȡ��ָ̧�𣬽����������
			float endX = event.getX();
			// ����������������ʼ������꣬˵����ָ�����һ���
			if (endX > startX) {
				Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "��~���ǵ�һҳ,���û��!", Toast.LENGTH_SHORT)
						.show();
				// finish();
				// �����������С����ʼ������꣬˵����ָ�����󻬶�
			} else if (endX < startX) {
				Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("pid", 12);
				intent.setClass(PendingSkipActivity1.this, PendingSkipActivity2.class);
				startActivity(intent);

				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				finish();
			}
			return true;
		}
		return super.onTouchEvent(event);
	}
}
