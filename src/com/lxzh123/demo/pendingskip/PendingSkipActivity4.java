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

public class PendingSkipActivity4 extends Activity {

	Button btnSkip;
	private float startX;
	int pid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pendingskip4);
		Intent intent = getIntent();
		pid = intent.getIntExtra("pid", 0);

		btnSkip = (Button) findViewById(R.id.button4);
		btnSkip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "û��һҳ��!", 5000).show();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (pid == 34) {
				startActivity(new Intent(PendingSkipActivity4.this, PendingSkipActivity3.class));
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
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
				Intent intent = new Intent();
				intent.putExtra("pid", 43);
				intent.setClass(PendingSkipActivity4.this, PendingSkipActivity3.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				finish();
				// �����������С����ʼ������꣬˵����ָ�����󻬶�
			} else if (endX < startX) {
				Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "û����һҳ��!", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onTouchEvent(event);
	}
}
