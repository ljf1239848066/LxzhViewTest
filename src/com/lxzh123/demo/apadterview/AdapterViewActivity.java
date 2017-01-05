package com.lxzh123.demo.apadterview;

import com.lxzh123.demo.testview.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AdapterViewActivity extends ListActivity {
	/* ��̬�������� */
	String[] weekStrings = new String[] { "������", "����һ", "���ڶ�", "������", "������",
			"������", "������" };
	/* ���������� */
	ArrayAdapter<String> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* �������������� */
		adapter = new ArrayAdapter<String>(this, R.layout.adapterview, weekStrings);
		/* ���������� */
		this.setListAdapter(adapter);
	}
}