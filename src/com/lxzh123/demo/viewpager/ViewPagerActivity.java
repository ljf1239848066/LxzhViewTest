package com.lxzh123.demo.viewpager;

import java.util.ArrayList;
import java.util.List;

import com.lxzh123.demo.testview.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ViewPagerActivity extends Activity {

	private View view1, view2, view3;// ��Ҫ������ҳ��
	private ViewPager viewPager;// viewpager
	private PagerTitleStrip pagerTitleStrip;// viewpager�ı���
	private PagerTabStrip pagerTabStrip;// һ��viewpager��ָʾ����Ч������һ����Ĵֵ��»���
	private List<View> viewList;// ����Ҫ������ҳ����ӵ����list��
	private List<String> titleList;// viewpager�ı���
	private Button weibo_button;// button����һ����������ڶ���Viewpager��ʾ��
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpage);
		initView();
	}

	/*
	 * ��������Ҫ˵��һ�£��������ͼƬ�����ǿ����ˣ�PagerTabStrip��PagerTitleStrip��������ʵ��viewpager��һ��ָʾ����
	 * ǰ��Ч������һ����Ĵֵ��»���
	 * ������������ʾ����ҳ���ı��⣬��Ȼ����Ҳ���Թ��档��ʹ�����ǵ�ʱ����Ҫע�⣬������Ĳ����ļ���Ҫ��android.support
	 * .v4.view.ViewPager�������
	 * android.support.v4.view.PagerTabStrip�Լ�android.support
	 * .v4.view.PagerTitleStrip��
	 */
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pagertitle);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(
				android.R.color.primary_text_dark));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setBackgroundColor(getResources().getColor(
				android.R.color.white));
		pagerTabStrip.setTextSpacing(50);
		/*
		 * weibo_button=(Button) findViewById(R.id.button1);
		 * weibo_button.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { intent=new
		 * Intent(ViewPagerDemo.this,WeiBoActivity.class);
		 * startActivity(intent); } });
		 */

		view1 = findViewById(R.layout.viewpageitem1);
		view2 = findViewById(R.layout.viewpageitem2);
		view3 = findViewById(R.layout.viewpageitem3);

		LayoutInflater lf = getLayoutInflater().from(this);
		view1 = lf.inflate(R.layout.viewpageitem1, null);
		view2 = lf.inflate(R.layout.viewpageitem2, null);
		view3 = lf.inflate(R.layout.viewpageitem3, null);

		viewList = new ArrayList<View>();// ��Ҫ��ҳ��ʾ��Viewװ��������
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);

		titleList = new ArrayList<String>();// ÿ��ҳ���Title����
		titleList.add("wp");
		titleList.add("jy");
		titleList.add("jh");

		PagerAdapter pagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,Object object) {
				container.removeView(viewList.get(position));
			}

			@Override
			public int getItemPosition(Object object) {
				return super.getItemPosition(object);
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return titleList.get(position);// ֱ��������������ɱ������ʾ�����Դ�������Կ���������û��ʹ��PagerTitleStrip����Ȼ�����ʹ�á�
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				if(position==1){
					 weibo_button=(Button)findViewById(R.id.button1);//�����Ҫע�⣬����������дadapter����ʵ����button����ģ��������onCreate()�������������ᱨ��ġ�
					 weibo_button.setOnClickListener(new OnClickListener() {
					
						 public void onClick(View v) {
							 intent=new Intent(ViewPagerActivity.this,ViewPagerActivity1.class);
							 startActivity(intent);
						 }
					 });
				}
				return viewList.get(position);
			}

		};
		viewPager.setAdapter(pagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}