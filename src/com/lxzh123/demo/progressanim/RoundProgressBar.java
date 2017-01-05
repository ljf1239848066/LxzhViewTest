package com.lxzh123.demo.progressanim;

import java.util.Timer;
import java.util.TimerTask;

import com.lxzh123.demo.testview.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class RoundProgressBar extends TextView{
	
	 private Paint mFramePaint;      
 
	 //--------------------
     private Paint  mRoundPaints;		// ������������
     private RectF  mRoundOval;			// ��������
     private int    mPaintWidth;		// ���ʿ��
     private int    mPaintColor;		// ������ɫ
    
     private int mStartProgress;	    // ��������ʼλ��
     private int mCurProgress;    		// ����������ǰλ��
	 private int mMaxProgress;			// ����������λ��
	 
	 private boolean mBRoundPaintsFill;	// �Ƿ��������
	 //---------------------
	 private int   mSidePaintInterval;	// Բ�����������ľ���	 
	 private Paint mSecondaryPaint;     // ��������������	 
	 private int   mSecondaryCurProgress;	// ������������ǰλ��	 
	 private Paint mBottomPaint;		// ����������ͼ����
	 private boolean mBShowBottom;		// �Ƿ���ʾ����������ɫ
 
	 //----------------------
	 private Handler mHandler;
	 
	 private boolean mBCartoom;			// �Ƿ�����������	 
	 private Timer   mTimer;			// ������������TIMER
	 
	 private MyTimerTask	mTimerTask;		// ��������	
	 private int 	 mSaveMax;			// ��������ʱ����ʱ�ı�MAXֵ���ñ������ڱ���ֵ�Ա�ָ�	 
	 private int     mTimerInterval;	// ��ʱ���������ʱ��(ms)	 
	 private float   mCurFloatProcess;	// ������ʱ��ǰ����ֵ	 
	 private float   mProcessRInterval;	// ������ʱÿ�����ӵĽ���ֵ	 
	 private final static int TIMER_ID = 0x100;		// ��ʱ��ID
 
	 private long mCurTime;
	
	 public RoundProgressBar(Context context) {
		 super(context);
		 initParam();
	 }

	 public RoundProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub	
		initParam();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);      
        
        mMaxProgress = array.getInt(R.styleable.RoundProgressBar_max, 100); 	
        mSaveMax = mMaxProgress;
        mBRoundPaintsFill = array.getBoolean(R.styleable.RoundProgressBar_fill, true);	// ����Ƿ������ģʽ
        if (mBRoundPaintsFill == false)
        {
        	mRoundPaints.setStyle(Paint.Style.STROKE);
        	mSecondaryPaint.setStyle(Paint.Style.STROKE);
        	mBottomPaint.setStyle(Paint.Style.STROKE);
        }
      
        mSidePaintInterval = array.getInt(R.styleable.RoundProgressBar_Inside_Interval, 0);// Բ����������    
        mBShowBottom = array.getBoolean(R.styleable.RoundProgressBar_Show_Bottom, true);
        
        mPaintWidth = array.getInt(R.styleable.RoundProgressBar_Paint_Width, 10);
        if (mBRoundPaintsFill)						// ���ģʽ�򻭱ʳ��ȸ�Ϊ0
        {
        	mPaintWidth = 0;
        }
        
        mRoundPaints.setStrokeWidth(mPaintWidth);
        mSecondaryPaint.setStrokeWidth(mPaintWidth);
        mBottomPaint.setStrokeWidth(mPaintWidth);
        
        mPaintColor = array.getColor(R.styleable.RoundProgressBar_Paint_Color, 0xffffcc00);
        mRoundPaints.setColor(mPaintColor);
        int color = mPaintColor & 0x00ffffff | 0x66000000;
        mSecondaryPaint.setColor(color);
        
        array.recycle(); //һ��Ҫ���ã������������     
	}
 
    private void initParam()
    {
    	mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setStrokeWidth(0);
               
        mPaintWidth = 0;
        mPaintColor = 0xffffcc00;
                 
        mRoundPaints = new Paint();
        mRoundPaints.setAntiAlias(true);
        mRoundPaints.setStyle(Paint.Style.FILL);

        mRoundPaints.setStrokeWidth(mPaintWidth);
        mRoundPaints.setColor(mPaintColor);
        
        mSecondaryPaint = new Paint();
        mSecondaryPaint.setAntiAlias(true);
        mSecondaryPaint.setStyle(Paint.Style.FILL);
        mSecondaryPaint.setStrokeWidth(mPaintWidth);
        
        int color = mPaintColor & 0x00ffffff | 0x66000000;
        mSecondaryPaint.setColor(color);
  
        mBottomPaint = new Paint();
        mBottomPaint.setAntiAlias(true);
        mBottomPaint.setStyle(Paint.Style.FILL);
        mBottomPaint.setStrokeWidth(mPaintWidth);
        mBottomPaint.setColor(Color.GRAY);
       
        mStartProgress = -90;
        mCurProgress = 0;
        mMaxProgress = 100;
        mSaveMax = 100;
        
        mBRoundPaintsFill = true;
        mBShowBottom = true;
        
        mSidePaintInterval = 0;       
        mSecondaryCurProgress = 0;           
        
        mRoundOval = new RectF(0, 0,  0, 0);
        mTimerInterval = 25;      
        mCurFloatProcess = 0;	 
        mProcessRInterval = 0;
        
        mBCartoom = false;
        
        mHandler = new Handler()
		{
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub				
				if (msg.what == TIMER_ID)
				{
//					long now = System.currentTimeMillis();
//					if (mCurTime != 0)
//					{
//						Log.i("", "interval time = " + (now - mCurTime));
//					}					
//					mCurTime = now;				
					if (mBCartoom == false)
					{
						return ;
					}
			
					mCurFloatProcess += mProcessRInterval;
					setProgress((int) mCurFloatProcess);
					
					if (mCurFloatProcess > mMaxProgress)
					{
						mBCartoom = false;
						mMaxProgress = mSaveMax;
						if (mTimerTask != null)
						{
							mTimerTask.cancel();
							mTimerTask = null;
						}
					}
				}
			}	
		};	
		mTimer = new Timer();        
    }
	
    public synchronized void setProgress (int progress)
    {
    	mCurProgress = progress;
    	if (mCurProgress < 0)
    	{
    		mCurProgress = 0;
    	}
    	
    	if (mCurProgress > mMaxProgress)
    	{
    		mCurProgress = mMaxProgress;
    	}
    	
    	invalidate();
    }
    
    public synchronized int getProgress()
    {
    	return mCurProgress;
    }
    
    public synchronized void setSecondaryProgress (int progress)
    {
    	mSecondaryCurProgress = progress;
    	if (mSecondaryCurProgress < 0)
    	{
    		mSecondaryCurProgress = 0;
    	}
    	
    	if (mSecondaryCurProgress > mMaxProgress)
    	{
    		mSecondaryCurProgress = mMaxProgress;
    	}
    	
    	invalidate();
    }
    
    public synchronized int getSecondaryProgress()
    {
    	return mSecondaryCurProgress;
    }
	
    public synchronized void setMax(int max)
    {
    	if (max <= 0)
    	{
    		return ;
    	} 	
    	mMaxProgress = max;
    	if (mCurProgress > max)
    	{
    		mCurProgress = max;
    	} 	
    	if (mSecondaryCurProgress > max)
    	{
    		mSecondaryCurProgress = max;
    	}  	
    	mSaveMax = mMaxProgress; 	
    	invalidate();
    }  
    public synchronized int getMax()
    {
    	return mMaxProgress;
    }
 
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		Log.i("", "W = " + w + ", H = " + h);
		
		
		if (mSidePaintInterval != 0)
		{
			mRoundOval.set(mPaintWidth/2 + mSidePaintInterval, mPaintWidth/2 + mSidePaintInterval,
			w - mPaintWidth/2 - mSidePaintInterval, h - mPaintWidth/2 - mSidePaintInterval);	
		}
		else{
			int sl = getPaddingLeft();
			int sr = getPaddingRight();
			int st = getPaddingTop();
			int sb = getPaddingBottom();
			mRoundOval.set(sl + mPaintWidth/2, st + mPaintWidth/2, w - sr - mPaintWidth/2, h - sb - mPaintWidth/2);	
		}
	}

	public synchronized void  startCartoom(int time)
	{
		if (time <= 0 || mBCartoom == true)
		{
			return ;
		}
		mBCartoom = true;		
		if (mTimerTask != null)
		{
			mTimerTask.cancel();
			mTimerTask = null;
		}		
		setProgress(0);
		setSecondaryProgress(0);		
		mSaveMax = mMaxProgress;
		mMaxProgress = (1000 / mTimerInterval) * time;			
		mProcessRInterval = 1;
		mCurFloatProcess = 0;
		mCurTime = 0;
		mTimerTask = new MyTimerTask();
		mTimer.schedule(mTimerTask, mTimerInterval, mTimerInterval);
	}
	
	public synchronized void  stopCartoom()
	{
		mBCartoom = false;
		mMaxProgress = mSaveMax;
		
		setProgress(0);
		if (mTimerTask != null)
		{
			mTimerTask.cancel();
			mTimerTask = null;
		}
	}

	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (mBShowBottom)
		{
			canvas.drawArc(mRoundOval, 0, 360, mBRoundPaintsFill, mBottomPaint);	
		}
		
		float secondRate = (float)mSecondaryCurProgress / mMaxProgress;
		float secondSweep = 360 * secondRate;
		canvas.drawArc(mRoundOval, mStartProgress, secondSweep, mBRoundPaintsFill, mSecondaryPaint);
		
		float rate = (float)mCurProgress / mMaxProgress;
		float sweep = 360 * rate;
		canvas.drawArc(mRoundOval, mStartProgress, sweep, mBRoundPaintsFill, mRoundPaints);
	}
	
	class MyTimerTask extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = mHandler.obtainMessage(TIMER_ID);
			msg.sendToTarget();	
		}		
	}
}