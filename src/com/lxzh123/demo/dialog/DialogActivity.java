package com.lxzh123.demo.dialog;

import com.lxzh123.demo.testview.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Color;

public class DialogActivity extends Activity {
	/** Called when the activity is first created. */
	private Button btnCondimentDialog;
	private CondimentDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertdialog);

		Button btnSelectDialog = (Button) findViewById(R.id.btnSelectDialog);
		Button btnCondimentDialog = (Button) findViewById(R.id.btnCondimentDialog);
		
		btnSelectDialog.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				final EditText edittext = (EditText) findViewById(R.id.et_alteredText);
				final CharSequence[] items = { "Black", "Red", "Blue", "Yellow" };
				final int[] color = { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW };
				final StringBuffer selectedItem = new StringBuffer("0");
				new AlertDialog.Builder(DialogActivity.this)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setTitle("��Button��Builder����ʾ�ı�����")
						// .setMessage(et_show.getText())
						.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								selectedItem.delete(0, selectedItem.length() - 1);
								selectedItem.append(which);
							}
						})
						.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {// ���һ��Button��ע������Ӧ�¼�
									public void onClick(DialogInterface dialog, int which) {
										int colorID = Integer.parseInt(selectedItem.toString());
										edittext.setBackgroundColor(color[colorID]);
									}
								}).setNegativeButton("���", new DialogInterface.OnClickListener() { // ע�⣬�����SetNegativeButton�������Ǹ���SetPositiveButton
									// �����ظ����֣���������Ǹ��Ḳ��ǰ���Ǹ���Ҳ����˵ÿ��Buttonֻ��ʾһ����
									public void onClick(DialogInterface dialog, int which) {
										edittext.setText("");
									}
								}).setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						}).show();

			}
		});
		dialog = new CondimentDialog(DialogActivity.this, R.layout.layout_dialog);
		dialog.setCanceledOnTouchOutside(false);
		btnCondimentDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("activity", "onclick....");
				dialog.show();
			}
		});
		Button btnDialogOk = (Button) dialog.findViewById(R.id.btnDialogOk);
		Button btnDialogCancel = (Button) dialog.findViewById(R.id.btnDialogCancel);
		
		btnDialogOk.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
				Log.e("dialog dismiss", "dismiss+++++++++++++++++++++++++++");
			}
		});
		btnDialogCancel.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "�Ͳ�ȡ��Ŷ~", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onClick(DialogInterface dialog, int which) {
		final EditText edittext = (EditText) findViewById(R.id.et_alteredText);
		final CharSequence[] items = { "Black", "Red", "Blue", "Yellow" };
		final int[] color = { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW };
		final StringBuffer selectedItem = new StringBuffer("0");
		new AlertDialog.Builder(DialogActivity.this)
				.setIcon(android.R.drawable.ic_dialog_info).setTitle("��Button��Builder����ʾ�ı�����")
				// .setMessage(et_show.getText())
				.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						selectedItem.delete(0, selectedItem.length() - 1);
						selectedItem.append(which);
					}
				}).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// ���һ��Button��ע������Ӧ�¼�
							public void onClick(DialogInterface dialog, int which) {
								int colorID = Integer.parseInt(selectedItem.toString());
								edittext.setBackgroundColor(color[colorID]);
							}
						}).setNegativeButton("���", new DialogInterface.OnClickListener() { // ע�⣬�����SetNegativeButton�������Ǹ���SetPositiveButton
							// �����ظ����֣���������Ǹ��Ḳ��ǰ���Ǹ���Ҳ����˵ÿ��Buttonֻ��ʾһ����
							public void onClick(DialogInterface dialog, int which) {
								edittext.setText("");
							}
						}).setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	
	class CondimentDialog extends Dialog {

		private Context mContext;

		public CondimentDialog(Context context, int theme) {
			super(context, theme);
			mContext = context;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.layout_dialog);

			LayoutParams params = getWindow().getAttributes();
			params.height = 1022;
			params.width = 928;
			getWindow().setAttributes(
					(android.view.WindowManager.LayoutParams) params);
		}

		protected void onStop() {
			Log.e("dialog onStop", "dialog onStop+++++++++++");
		}
	}
}