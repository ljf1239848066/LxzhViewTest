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

public class PendingSkipActivity3 extends Activity{

	Button btnSkip;
	private float startX;
	int pid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pendingskip3);
		Intent intent=getIntent();
		pid=intent.getIntExtra("pid", 0);
		
		btnSkip = (Button) findViewById(R.id.button3);
		btnSkip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PendingSkipActivity3.this, PendingSkipActivity4.class));
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(pid==23)
			{
				startActivity(new Intent(PendingSkipActivity3.this, PendingSkipActivity2.class));
				overridePendingTransition(R.anim.push_right_in,
    					R.anim.push_right_out);
			}else if(pid==43)
			{
				startActivity(new Intent(PendingSkipActivity3.this, PendingSkipActivity4.class));
				overridePendingTransition(R.anim.push_left_in,
    					R.anim.push_left_out);
			}
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        //�����Ļ��MotionEvent.ACTION_DOWN Ϊ��ָ�����Ļ�¼�
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //��ȡ��ָ��ʼ���������
            startX = event.getX();
            //��ָ̧�𣬽�������
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            //��ȡ��ָ̧�𣬽����������
            float endX = event.getX();
            Intent intent=new Intent();
            //����������������ʼ������꣬˵����ָ�����һ���
            if(endX > startX) {
    			Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();
    			intent.putExtra("pid", 32);
    			intent.setClass(PendingSkipActivity3.this, PendingSkipActivity2.class);
    			startActivity(intent);
    			overridePendingTransition(R.anim.push_right_in,
    					R.anim.push_right_out);
    			finish();
            //�����������С����ʼ������꣬˵����ָ�����󻬶�
            } else if (endX < startX) {
    			Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();
    			intent.putExtra("pid", 34);
    			intent.setClass(PendingSkipActivity3.this, PendingSkipActivity4.class);
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
