package com.lxzh123.demo.rotate1;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3D1 extends Animation {
	private float fromDegree;	
	private float toDegree;		
	private float mCenterX;		
	private float mCenterY;		
	private Camera mCamera;

	public Rotate3D1(float fromDegree, float toDegree, float centerX, float centerY) {
		this.fromDegree = fromDegree;
		this.toDegree = toDegree;
		this.mCenterX = centerX;
		this.mCenterY = centerY;

	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float FromDegree = fromDegree;
		float degrees = FromDegree + (toDegree - fromDegree) * interpolatedTime;
		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Matrix matrix = t.getMatrix();

		if (degrees <= -76.0f) {
			degrees = -90.0f;
			mCamera.save();
			mCamera.rotateX(degrees);		
			mCamera.getMatrix(matrix);
			mCamera.restore();
		} else if (degrees >= 76.0f) {
			degrees = 90.0f; 
			mCamera.save();
			mCamera.rotateX(degrees);
			mCamera.getMatrix(matrix);
			mCamera.restore();
		} else {
			mCamera.save();
			mCamera.translate(0, 0, centerX);		
			mCamera.rotateX(degrees);
			mCamera.translate(0, 0, -centerX);
			mCamera.getMatrix(matrix);
			mCamera.restore();
		}

		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}



