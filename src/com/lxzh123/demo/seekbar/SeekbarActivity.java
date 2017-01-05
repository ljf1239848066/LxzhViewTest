package com.lxzh123.demo.seekbar;

import com.lxzh123.demo.testview.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekbarActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {

	SeekBar mSeekBar;
	TextView mProgressText;
	TextView mTrackingText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.seekbar);
		setTitle("SeekBar");

		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		// setOnSeekBarChangeListener() - ��Ӧ�϶��������¼�
		mSeekBar.setOnSeekBarChangeListener(this);

		mProgressText = (TextView) findViewById(R.id.progress);
		mTrackingText = (TextView) findViewById(R.id.tracking);
	}

	// �϶��������󣬽��ȷ����ı�ʱ�Ļص��¼�
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {
		mProgressText.setText(progress + "%");
	}

	// �϶�������ǰ��ʼ���ٴ���
	public void onStartTrackingTouch(SeekBar seekBar) {
		mTrackingText.setText("��ʼ���ٴ���");
	}

	// �϶���������ֹͣ���ٴ���
	public void onStopTrackingTouch(SeekBar seekBar) {
		mTrackingText.setText("ֹͣ���ٴ���");
	}
}
