package info.covid.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import info.covid.R;

public class RoundCornerProgressBar extends View {

	private static final int DEFAULT_BACKGROUND_COLOR = 0x00009688;
	
	private static final int DEFAULT_PROGRESS_COLOR = 0xff009688;

	private Path mHorizontalBgPath;

	private Paint mHorizontalBgPaint;

	private Path mProgressPath;

	private Paint mProgressPaint;

	private long mProgress = 0;

	private long mMax = 100;

	private long mProgressBarWidth;

	private int mPathViewHeight = 0;

	private int mInitMoveToX = 0;

	private int mInitMoveToY = 0;

	private int mDefaultRoundConrnerWidth;

	private int mRoundCornerBackgroundColor;

	private int mRoundCornerProgressColor;

	public RoundCornerProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerProgressBar);
		mDefaultRoundConrnerWidth = attributes
				.getDimensionPixelSize(R.styleable.RoundCornerProgressBar_round_corner_width, dip2px(getContext(), 4));
		mRoundCornerBackgroundColor = attributes
				.getColor(R.styleable.RoundCornerProgressBar_round_corner_background_color, DEFAULT_BACKGROUND_COLOR);
		mRoundCornerProgressColor = attributes.getColor(R.styleable.RoundCornerProgressBar_round_corner_progress_color,
				DEFAULT_PROGRESS_COLOR);
		attributes.recycle();
		init();
	}

	public RoundCornerProgressBar(Context context) {
		super(context);
		mDefaultRoundConrnerWidth = dip2px(getContext(), 4);
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mInitMoveToX = dip2px(getContext(), 7);
		mInitMoveToY = mInitMoveToX;
		int dw = getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
		int dh = mPathViewHeight + getPaddingTop() + getPaddingBottom();
		mInitMoveToX = mInitMoveToX + getPaddingLeft();
		mInitMoveToY = mInitMoveToY + getPaddingTop();
		mProgressBarWidth = getMeasuredWidth() - (getPaddingRight() + dip2px(getContext(), 7));
		setMeasuredDimension(resolveSizeAndState(dw, widthMeasureSpec, 0),
				resolveSizeAndState(dh, heightMeasureSpec, 0));
	}

	private void init() {
		mHorizontalBgPath = new Path();
		mProgressPath = new Path();
		mHorizontalBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHorizontalBgPaint.setStrokeWidth(mDefaultRoundConrnerWidth);
		mHorizontalBgPaint.setColor(mRoundCornerBackgroundColor);
		mHorizontalBgPaint.setDither(true);
		mHorizontalBgPaint.setFilterBitmap(true);
		mHorizontalBgPaint.setStrokeJoin(Join.ROUND);
		mHorizontalBgPaint.setStyle(Paint.Style.STROKE);
		mHorizontalBgPaint.setStrokeCap(Paint.Cap.ROUND);

		mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mProgressPaint.setStrokeWidth(mDefaultRoundConrnerWidth);
		mProgressPaint.setDither(true);
		mProgressPaint.setFilterBitmap(true);
		mProgressPaint.setStrokeJoin(Join.ROUND);
		mProgressPaint.setColor(mRoundCornerProgressColor);
		mProgressPaint.setStyle(Paint.Style.STROKE);
		mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

		mPathViewHeight = (int) Math.ceil(mProgressPaint.getStrokeWidth());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mHorizontalBgPath.moveTo(mInitMoveToX, mInitMoveToY);
		mHorizontalBgPath.lineTo(mProgressBarWidth, mInitMoveToY);
		mProgressPath.moveTo(mInitMoveToX, mInitMoveToY);
		float minX = (mProgressBarWidth * 1.0f * getProgress()) / mMax * 1.0f;
		if (((int) Math.floor(minX)) < mInitMoveToX) {
			minX = mInitMoveToX;
		}
		mProgressPath.lineTo(minX, mInitMoveToY);
		canvas.drawPath(mHorizontalBgPath, mHorizontalBgPaint);
		canvas.drawPath(mProgressPath, mProgressPaint);
	}

	public void setProgress(long progress) {
		mProgress = progress;
		if (mProgress > mMax) {
			mProgress = mMax;
		}
		postInvalidate();
	}

	public long getProgress() {
		return mProgress;
	}

	public void setMax(long max) {
		mMax = max;
	}

	public long getMax() {
		return mMax;
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}