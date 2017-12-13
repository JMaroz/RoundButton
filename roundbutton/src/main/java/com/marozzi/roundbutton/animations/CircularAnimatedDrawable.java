package com.marozzi.roundbutton.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.marozzi.roundbutton.RoundButtonHelper;

public class CircularAnimatedDrawable extends BaseAnimatedDrawable {

    private ValueAnimator mValueAnimatorAngle;
    private ValueAnimator mValueAnimatorSweep;
    private AnimatorSet mAnimatorSet;
    private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator SWEEP_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final int ANGLE_ANIMATOR_DURATION = 2000;
    private static final int SWEEP_ANIMATOR_DURATION = 700;
    private static final Float MIN_SWEEP_ANGLE = 50f;

    private final RectF fBounds = new RectF();
    private Paint mPaint;
    private View mAnimatedView;

    private float mBorderWidth;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;
    private float mCurrentGlobalAngleOffset;

    private boolean mModeAppearing;
    private boolean mRunning;

    private boolean shouldDraw;

    private RectF innerBounds = new RectF();
    private Paint innerPaint;
    private Bitmap innerImage;

    /**
     * @param view        View to be animated
     * @param borderWidth The width of the spinning bar
     * @param arcColor    The color of the spinning bar
     */
    public CircularAnimatedDrawable(View view, float borderWidth, int arcColor) {
        this(view, borderWidth, arcColor, 0, Color.BLACK);
    }

    /**
     * @param view        View to be animated
     * @param borderWidth The width of the spinning bar
     * @param arcColor    The color of the spinning bar
     */
    public CircularAnimatedDrawable(View view, float borderWidth, int arcColor, @DrawableRes int innerResource, @ColorInt int innerResourceColorFilter) {
        mAnimatedView = view;
        mBorderWidth = borderWidth;

        if (innerResource != 0) {
            setInnerResource(innerResource);
            setInnerResourceColorFilter(innerResourceColorFilter);
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(arcColor);

        setupAnimations();

        shouldDraw = true;

        mAnimatorSet = new AnimatorSet();
    }

    public void setInnerResource(@DrawableRes int innerResource) {
        innerImage = RoundButtonHelper.getBitmap(ContextCompat.getDrawable(mAnimatedView.getContext(), innerResource));
    }

    public void setInnerResourceColorFilter(@ColorInt int resourceColorFilter) {
        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setColorFilter(new PorterDuffColorFilter(resourceColorFilter, PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        fBounds.left = bounds.left + mBorderWidth / 2f + .5f;
        fBounds.right = bounds.right - mBorderWidth / 2f - .5f;
        fBounds.top = bounds.top + mBorderWidth / 2f + .5f;
        fBounds.bottom = bounds.bottom - mBorderWidth / 2f - .5f;

        innerBounds.left = fBounds.left + 2.5f;
        innerBounds.right = fBounds.right - 2.5f;
        innerBounds.top = fBounds.top + 2.5f;
        innerBounds.bottom = fBounds.bottom - 2.5f;

        if (innerImage != null) {
            int bitMapWidth = (int) (innerBounds.right - innerBounds.left);
            int bitMapHeight = (int) (innerBounds.bottom - innerBounds.top);
            innerImage = Bitmap.createScaledBitmap(innerImage, bitMapWidth, bitMapHeight, false);
        }
    }

    /**
     * Start the animation
     */
    @Override
    public void start() {
        if (isRunning()) {
            return;
        }

        mRunning = true;

        mAnimatorSet.playTogether(mValueAnimatorAngle, mValueAnimatorSweep);
        mAnimatorSet.start();
    }

    /**
     * Stops the animation
     */
    @Override
    public void stop() {
        if (!isRunning()) {
            return;
        }

        mRunning = false;
        mAnimatorSet.cancel();
    }

    /**
     * Method the inform if the animation is in process
     *
     * @return
     */
    @Override
    public boolean isRunning() {
        return mRunning;
    }

    /**
     * Method called when the drawable is going to draw itself.
     *
     * @param canvas
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        float startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset;
        float sweepAngle = mCurrentSweepAngle;

        if (!mModeAppearing) {
            startAngle = startAngle + sweepAngle;
            sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE;
        } else {
            sweepAngle += MIN_SWEEP_ANGLE;
        }

        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint);

        if (innerImage != null)
            canvas.drawBitmap(innerImage, null, innerBounds, innerPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public void setupAnimations() {
        mValueAnimatorAngle = ValueAnimator.ofFloat(0, 360f);
        mValueAnimatorAngle.setInterpolator(ANGLE_INTERPOLATOR);
        mValueAnimatorAngle.setDuration(ANGLE_ANIMATOR_DURATION);
        mValueAnimatorAngle.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimatorAngle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentGlobalAngle = (float) animation.getAnimatedValue();
            }
        });

        mValueAnimatorSweep = ValueAnimator.ofFloat(0, 360f - 2 * MIN_SWEEP_ANGLE);
        mValueAnimatorSweep.setInterpolator(SWEEP_INTERPOLATOR);
        mValueAnimatorSweep.setDuration(SWEEP_ANIMATOR_DURATION);
        mValueAnimatorSweep.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimatorSweep.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                toggleAppearingMode();
                shouldDraw = false;
            }
        });

        mValueAnimatorSweep.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentSweepAngle = (float) animation.getAnimatedValue();

                if (mCurrentSweepAngle < 5) {
                    shouldDraw = true;
                }

                if (shouldDraw) {
                    mAnimatedView.invalidate();
                }
            }
        });

    }

    /**
     * This method is called in every repetition of the animation, so the animation make the sweep
     * growing and then make it shirinking.
     */
    private void toggleAppearingMode() {
        mModeAppearing = !mModeAppearing;

        if (mModeAppearing) {
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360;
        }
    }

    @Override
    public void dispose() {
        if (mValueAnimatorAngle != null) {
            mValueAnimatorAngle.end();
            mValueAnimatorAngle.removeAllUpdateListeners();
            mValueAnimatorAngle.cancel();
        }

        mValueAnimatorAngle = null;

        if (mValueAnimatorSweep != null) {
            mValueAnimatorSweep.end();
            mValueAnimatorSweep.removeAllUpdateListeners();
            mValueAnimatorSweep.cancel();
        }

        mValueAnimatorSweep = null;

        if (mAnimatorSet != null) {
            mAnimatorSet.end();
            mAnimatorSet.cancel();
        }
    }
}
