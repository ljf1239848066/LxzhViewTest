package com.lxzh123.demo.cyclewheelview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import com.lxzh123.demo.testview.R;

/**
 * ��ѭ��������ѡ����
 * 
 * @author
 *
 */
public class CycleWheelView extends ListView {

	public static final String TAG = CycleWheelView.class.getSimpleName();
	private static final int COLOR_DIVIDER_DEFALUT = Color.parseColor("#747474");
	private static final int HEIGHT_DIVIDER_DEFAULT = 2;
	private static final int COLOR_SOLID_DEFAULT = Color.parseColor("#3e4043");
	private static final int COLOR_SOLID_SELET_DEFAULT = Color.parseColor("#323335");
	private static final int WHEEL_SIZE_DEFAULT = 3;

	private Handler mHandler;

	private CycleWheelViewAdapter mAdapter;

	/**
	 * Labels
	 */
	private List<String> mLabels;

	/**
	 * Color Of Selected Label
	 */
	private int mLabelSelectColor = Color.WHITE;

	/**
	 * Color Of Unselected Label
	 */
	private int mLabelColor = Color.GRAY;

	/**
	 * Gradual Alph
	 */
	private float mAlphaGradual = 0.7f;

	/**
	 * Color Of Divider
	 */
	private int dividerColor = COLOR_DIVIDER_DEFALUT;

	/**
	 * Height Of Divider
	 */
	private int dividerHeight = HEIGHT_DIVIDER_DEFAULT;

	/**
	 * Color of Selected Solid
	 */
	private int seletedSolidColor = COLOR_SOLID_SELET_DEFAULT;

	/**
	 * Color of Unselected Solid
	 */
	private int solidColor = COLOR_SOLID_DEFAULT;

	/**
	 * Size Of Wheel , it should be odd number like 3 or greater
	 */
	private int mWheelSize = WHEEL_SIZE_DEFAULT;

	/**
	 * res Id of Wheel Item Layout
	 */
	private int mItemLayoutId;

	/**
	 * res Id of Label TextView
	 */
	private int mItemLabelTvId;

	/**
	 * Height of Wheel Item
	 */
	private int mItemHeight;

	private boolean cylceEnable;

	private int mCurrentPositon;

	private WheelItemSelectedListener mItemSelectedListener;

	public CycleWheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CycleWheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CycleWheelView(Context context) {
		super(context);
	}

	private void init() {
		mHandler = new Handler();
		mItemLayoutId = R.layout.item_cyclewheel;
		mItemLabelTvId = R.id.tv_label_item_wheel;
		mAdapter = new CycleWheelViewAdapter();
		setVerticalScrollBarEnabled(false);
		setScrollingCacheEnabled(false);
		setCacheColorHint(Color.TRANSPARENT);
		setFadingEdgeLength(0);
		setOverScrollMode(OVER_SCROLL_NEVER);
		setDividerHeight(0);
		setAdapter(mAdapter);
		setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == SCROLL_STATE_IDLE) {
					View itemView = getChildAt(0);
					if (itemView != null) {
						float deltaY = itemView.getY();
						if (deltaY == 0) {
							return;
						}
						if (Math.abs(deltaY) < mItemHeight / 2) {
							smoothScrollBy(getDistance(deltaY), 50);
						} else {
							smoothScrollBy(getDistance(mItemHeight + deltaY),50);
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				refreshItems();
			}
		});
	}

	private int getDistance(float scrollDistance) {
		if (Math.abs(scrollDistance) <= 2) {
			return (int) scrollDistance;
		} else if (Math.abs(scrollDistance) < 12) {
			return scrollDistance > 0 ? 2 : -2;
		} else {
			return (int) (scrollDistance / 6);
		}
	}

	private void refreshItems() {
		int offset = mWheelSize / 2;
		int firstPosition = getFirstVisiblePosition();
		int position = 0;
		if (getChildAt(0) == null) {
			return;
		}
		if (Math.abs(getChildAt(0).getY())<=(mItemHeight / 2)) {
			position = firstPosition + offset;
		} else {
			position = firstPosition + offset + 1;
		}
		if (position == mCurrentPositon) {
			return;
		}
		mCurrentPositon = position;
		if (mItemSelectedListener != null) {
			mItemSelectedListener.onItemSelected(getSelection(),
					getSelectLabel());
		}
		resetItems(firstPosition, position, offset);
	}

	private void resetItems(int firstPosition, int position, int offset) {
		for (int i = position - offset - 1; i < position + offset + 1; i++) {
			View itemView = getChildAt(i - firstPosition);
			if (itemView == null) {
				continue;
			}
			TextView labelTv = (TextView) itemView.findViewById(mItemLabelTvId);
			if (position == i) {
				labelTv.setTextColor(mLabelSelectColor);
				itemView.setAlpha(1f);
			} else {
				labelTv.setTextColor(mLabelColor);
				int delta = Math.abs(i - position);
				double alpha = Math.pow(mAlphaGradual, delta);
				itemView.setAlpha((float)alpha);
			}
		}
	}

	/**
	 * ���ù��ֵĿ̶��б�
	 * 
	 * @param labels
	 */
	public void setLabels(List<String> labels) {
		mLabels = labels;
		mAdapter.setData(mLabels);
		mAdapter.notifyDataSetChanged();
		initView();
	}

	/**
	 * ���ù��ֹ�������
	 * 
	 * @param mItemSelectedListener
	 */
	public void setOnWheelItemSelectedListener(
			WheelItemSelectedListener mItemSelectedListener) {
		this.mItemSelectedListener = mItemSelectedListener;
	}

	/**
	 * ��ȡ���ֵĿ̶��б�
	 * 
	 * @return
	 */
	public List<String> getLabels() {
		return mLabels;
	}

	/**
	 * ���ù����Ƿ�Ϊѭ������
	 * 
	 * @param enable
	 *            true-ѭ�� false-����
	 */
	public void setCycleEnable(boolean enable) {
		if (cylceEnable != enable) {
			cylceEnable = enable;
			mAdapter.notifyDataSetChanged();
			setSelection(getSelection());
		}
	}

	/*
	 * ������ָ��λ��
	 */
	@Override
	public void setSelection(final int position) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				CycleWheelView.super.setSelection(getPosition(position));
			}
		});
	}

	private int getPosition(int positon) {
		if (mLabels == null || mLabels.size() == 0) {
			return 0;
		}
		if (cylceEnable) {
			int d = Integer.MAX_VALUE / 2 / mLabels.size();
			return positon + d * mLabels.size();
		}
		return positon;
	}

	/**
	 * ��ȡ��ǰ����λ��
	 * 
	 * @return
	 */
	public int getSelection() {
		if (mCurrentPositon == 0) {
			mCurrentPositon = mWheelSize / 2;
		}
		return (mCurrentPositon - mWheelSize / 2) % mLabels.size();
	}

	/**
	 * ��ȡ��ǰ����λ�õĿ̶�
	 * 
	 * @return
	 */
	public String getSelectLabel() {
		int position = getSelection();
		position = position < 0 ? 0 : position;
		try {
			return mLabels.get(position);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * �����Ҫ�Զ������ÿ��Item�����ô˷��������Զ���Item���֣��Զ��岼������Ҫһ��TextView����ʾ���̶ֿ�
	 * 
	 * @param itemResId
	 *            �����ļ�Id
	 * @param labelTvId
	 *            �̶�TextView����ԴId
	 */
	public void setWheelItemLayout(int itemResId, int labelTvId) {
		mItemLayoutId = itemResId;
		mItemLabelTvId = labelTvId;
		mAdapter = new CycleWheelViewAdapter();
		mAdapter.setData(mLabels);
		setAdapter(mAdapter);
		initView();
	}

	/**
	 * ����δѡ�п̶�������ɫ
	 * 
	 * @param labelColor
	 */
	public void setLabelColor(int labelColor) {
		this.mLabelColor = labelColor;
		resetItems(getFirstVisiblePosition(), mCurrentPositon, mWheelSize / 2);
	}

	/**
	 * ����ѡ�п̶�������ɫ
	 * 
	 * @param labelSelectColor
	 */
	public void setLabelSelectColor(int labelSelectColor) {
		this.mLabelSelectColor = labelSelectColor;
		resetItems(getFirstVisiblePosition(), mCurrentPositon, mWheelSize / 2);
	}

	/**
	 * ���ù��̶ֿ�͸������ֵ
	 * 
	 * @param alphaGradual
	 */
	public void setAlphaGradual(float alphaGradual) {
		this.mAlphaGradual = alphaGradual;
		resetItems(getFirstVisiblePosition(), mCurrentPositon, mWheelSize / 2);
	}

	/**
	 * ���ù��ֿ���ʾ�Ŀ̶�����������Ϊ�������Ҵ��ڵ���3
	 * 
	 * @param wheelSize
	 * @throws CycleWheelViewException
	 *             ������������
	 */
	public void setWheelSize(int wheelSize) throws CycleWheelViewException {
		if (wheelSize < 3 || wheelSize % 2 != 1) {
			throw new CycleWheelViewException("Wheel Size Error , Must Be 3,5,7,9...");
		} else {
			mWheelSize = wheelSize;
			initView();
		}
	}

	/**
	 * ���ÿ����ɫ
	 * 
	 * @param unselectedSolidColor
	 *            δѡ�еĿ����ɫ
	 * @param selectedSolidColor
	 *            ѡ�еĿ����ɫ
	 */
	public void setSolid(int unselectedSolidColor, int selectedSolidColor) {
		this.solidColor = unselectedSolidColor;
		this.seletedSolidColor = selectedSolidColor;
		initView();
	}

	/**
	 * ���÷ָ�����ʽ
	 * 
	 * @param dividerColor
	 *            �ָ�����ɫ
	 * @param dividerHeight
	 *            �ָ��߸߶�(px)
	 */
	public void setDivider(int dividerColor, int dividerHeight) {
		this.dividerColor = dividerColor;
		this.dividerHeight = dividerHeight;
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		mItemHeight = measureHeight();
		ViewGroup.LayoutParams lp = getLayoutParams();
		lp.height = mItemHeight * mWheelSize;
		mAdapter.setData(mLabels);
		mAdapter.notifyDataSetChanged();
		Drawable backgroud = new Drawable() {
			@Override
			public void draw(Canvas canvas) {
				int viewWidth = getWidth();
				Paint dividerPaint = new Paint();
				dividerPaint.setColor(dividerColor);
				dividerPaint.setStrokeWidth(dividerHeight);
				Paint seletedSolidPaint = new Paint();
				seletedSolidPaint.setColor(seletedSolidColor);
				Paint solidPaint = new Paint();
				solidPaint.setColor(solidColor);
				canvas.drawRect(0, 0, viewWidth,mItemHeight * (mWheelSize / 2), solidPaint);
				canvas.drawRect(0, mItemHeight * (mWheelSize / 2 + 1),viewWidth, mItemHeight * (mWheelSize), solidPaint);
				canvas.drawRect(0, mItemHeight * (mWheelSize / 2), viewWidth, mItemHeight * (mWheelSize / 2 + 1), seletedSolidPaint);
				canvas.drawLine(0, mItemHeight * (mWheelSize / 2), viewWidth, mItemHeight * (mWheelSize / 2), dividerPaint);
				canvas.drawLine(0, mItemHeight * (mWheelSize / 2 + 1),viewWidth, mItemHeight * (mWheelSize / 2 + 1), dividerPaint);
			}

			@Override
			public void setAlpha(int alpha) {
			}

			@Override
			public void setColorFilter(ColorFilter cf) {
			}

			@Override
			public int getOpacity() {
				return 0;
			}
		};
		setBackgroundDrawable(backgroud);
	}

	private int measureHeight() {
		View itemView = LayoutInflater.from(getContext()).inflate(mItemLayoutId, null);
		itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		itemView.measure(w, h);
		int height = itemView.getMeasuredHeight();
		// int width = view.getMeasuredWidth();
		return height;
	}

	public interface WheelItemSelectedListener {
		public void onItemSelected(int position, String label);
	}

	public class CycleWheelViewException extends Exception {
		private static final long serialVersionUID = 1L;

		public CycleWheelViewException(String detailMessage) {
			super(detailMessage);
		}
	}

	public class CycleWheelViewAdapter extends BaseAdapter {

		private List<String> mData = new ArrayList<String>();

		public void setData(List<String> mWheelLabels) {
			mData.clear();
			mData.addAll(mWheelLabels);
		}

		@Override
		public int getCount() {
			if (cylceEnable) {
				return Integer.MAX_VALUE;
			}
			return mData.size() + mWheelSize - 1;
		}

		@Override
		public Object getItem(int position) {
			return "";
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(mItemLayoutId, null);
			}
			TextView textView = (TextView) convertView.findViewById(mItemLabelTvId);
			if (position < mWheelSize / 2|| (!cylceEnable && position >= mData.size() + mWheelSize/ 2)) {
				textView.setText("");
				convertView.setVisibility(View.INVISIBLE);
			} else {
				textView.setText(mData.get((position - mWheelSize / 2) % mData.size()));
				convertView.setVisibility(View.VISIBLE);
			}
			return convertView;
		}
	}
}