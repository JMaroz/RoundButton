package com.marozzi.roundbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.ViewGroup;
import android.widget.Button;

import com.marozzi.roundbutton.animations.BaseAnimatedDrawable;
import com.marozzi.roundbutton.animations.CircularAnimatedDrawable;
import com.marozzi.roundbutton.animations.CircularRevealAnimatedDrawable;
import com.marozzi.roundbutton.animations.DotsAnimatedDrawable;

import static com.marozzi.roundbutton.RoundButtonHelper.createDrawable;
import static com.marozzi.roundbutton.RoundButtonHelper.manipulateColor;

/**
 * Created by amarozzi on 21/09/2017.
 */

public class RoundButton extends AppCompatButton {

    public static RoundButtonHelper.Builder newBuilder() {
        return new RoundButtonHelper.Builder();
    }

    private enum AnimationState {
        IDLE, MORPHING, PROGRESS, DONE
    }

    public enum ResultState {
        NONE, SUCCESS, FAILURE,
    }

    public enum AnimationProgressStyle {
        CIRCLE, DOTS
    }

    private BaseAnimatedDrawable progressDrawable;
    private CircularRevealAnimatedDrawable resultDrawable;
    private AnimatorSet morpSet;

    private RoundButtonAnimationListener listener;
    private ButtonProperty property;

    private ResultState resultState = ResultState.NONE;
    private AnimationState animationState = AnimationState.IDLE;
    private AnimationProgressStyle animationProgressStyle = AnimationProgressStyle.CIRCLE;

    /**
     * Duration of the animations in milliseconds
     */
    private int animationDurations;

    /**
     * The corner radius during animations
     */
    private int animationCornerRadius;

    /**
     * The width of the bar during the animations
     */
    private int animationProgressWidth;

    /**
     * The color of the bar during the animations
     */
    private int animationProgressColor;

    /**
     * The padding of the bar during the animations
     */
    private int animationProgressPadding;

    /**
     * Indicate if animate with alpha
     */
    private boolean animationAlpha;

    /**
     * Resource for show an image while animating, only work with circle animation
     */
    private int animationInnerResource;

    /**
     * Tint color for the resource to show while animating
     */
    private int animationInnerResourceColor;

    /**
     * Background color for success result
     */
    private int resultSuccessColor;

    /**
     * Resource for success result
     */
    private int resultSuccessResource;

    /**
     * Background color for failure result
     */
    private int resultFailureColor;

    /**
     * Resource for failure result
     */
    private int resultFailureResource;

    /**
     * The corner radius
     */
    private int cornerRadius;

    /**
     * The width of the corner
     */
    private int cornerWidth;

    private int cornerColor;
    private int cornerColorPressed;
    private int cornerColorDisabled = -1;

    private int backgroundColor;
    private int backgroundColorPressed;
    private int backgroundColorDisabled = -1;

    private int textColor;
    private int textColorPressed;
    private int textColorDisabled = -1;

    public RoundButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundButton, defStyleAttr, 0);

        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundButton_rb_corner_radius, 0);
        cornerWidth = a.getDimensionPixelSize(R.styleable.RoundButton_rb_corner_width, 0);

        cornerColor = a.getColor(R.styleable.RoundButton_rb_corner_color, Color.TRANSPARENT);
        cornerColorDisabled = a.getColor(R.styleable.RoundButton_rb_corner_color_disabled, cornerColorDisabled);
        if (a.hasValue(R.styleable.RoundButton_rb_corner_color_pressed))
            cornerColorPressed = a.getColor(R.styleable.RoundButton_rb_corner_color_pressed, cornerColor);
        else
            cornerColorPressed = manipulateColor(cornerColor, .8f);

        backgroundColor = a.getColor(R.styleable.RoundButton_rb_background_color, Color.TRANSPARENT);
        backgroundColorDisabled = a.getColor(R.styleable.RoundButton_rb_background_color_disabled, backgroundColorDisabled);
        if (a.hasValue(R.styleable.RoundButton_rb_background_color_pressed))
            backgroundColorPressed = a.getColor(R.styleable.RoundButton_rb_background_color_pressed, backgroundColor);
        else
            backgroundColorPressed = manipulateColor(backgroundColor, .8f);

        textColor = a.getColor(R.styleable.RoundButton_rb_text_color, Color.TRANSPARENT);
        textColorDisabled = a.getColor(R.styleable.RoundButton_rb_text_color_disabled, textColorDisabled);
        if (a.hasValue(R.styleable.RoundButton_rb_text_color_pressed))
            textColorPressed = a.getColor(R.styleable.RoundButton_rb_text_color_pressed, textColor);
        else
            textColorPressed = manipulateColor(textColor, .8f);

        animationDurations = a.getInt(R.styleable.RoundButton_rb_animation_duration, 300);
        animationCornerRadius = a.getDimensionPixelSize(R.styleable.RoundButton_rb_animation_corner_radius, 0);
        animationAlpha = a.getBoolean(R.styleable.RoundButton_rb_animation_alpha, false);
        animationProgressWidth = a.getDimensionPixelSize(R.styleable.RoundButton_rb_animation_progress_width, 20);
        animationProgressColor = a.getColor(R.styleable.RoundButton_rb_animation_progress_color, backgroundColor);
        animationProgressPadding = a.getDimensionPixelSize(R.styleable.RoundButton_rb_animation_progress_padding, 10);
        if (a.hasValue(R.styleable.RoundButton_rb_animation_progress_style)) {
            animationProgressStyle = AnimationProgressStyle.values()[a.getInt(R.styleable.RoundButton_rb_animation_progress_style, 0)];
        }
        animationInnerResource = a.getResourceId(R.styleable.RoundButton_rb_animation_inner_resource, 0);
        if (a.hasValue(R.styleable.RoundButton_rb_animation_inner_resource_color)) {
            animationInnerResourceColor = a.getColor(R.styleable.RoundButton_rb_animation_inner_resource_color, Color.BLACK);
        }

        resultSuccessColor = a.getColor(R.styleable.RoundButton_rb_success_color, Color.GREEN);
        resultSuccessResource = a.getResourceId(R.styleable.RoundButton_rb_success_resource, 0);
        resultFailureColor = a.getColor(R.styleable.RoundButton_rb_failure_color, Color.RED);
        resultFailureResource = a.getResourceId(R.styleable.RoundButton_rb_failure_resource, 0);

        a.recycle();

        update();
    }

    private void update() {
        StateListDrawable background = new StateListDrawable();
        background.addState(new int[]{android.R.attr.state_pressed}, createDrawable(
                backgroundColorPressed, cornerColorPressed, cornerWidth, cornerRadius));

        if (backgroundColorDisabled != -1)
            background.addState(new int[]{-android.R.attr.state_enabled}, createDrawable(
                    backgroundColorDisabled, cornerColorDisabled, cornerWidth, cornerRadius));

        background.addState(StateSet.WILD_CARD, createDrawable(
                backgroundColor, cornerColor, cornerWidth, cornerRadius));
        setBackground(background);

        setTextColor(textColorDisabled != -1 ?
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_pressed},
                                new int[]{-android.R.attr.state_enabled},
                                new int[]{}
                        },
                        new int[]{
                                textColorPressed,
                                textColorDisabled,
                                textColor
                        }) :
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_pressed},
                                new int[]{}
                        },
                        new int[]{
                                textColorPressed,
                                textColor
                        }
                ));
    }


    public void setButtonAnimationListener(RoundButtonAnimationListener listener) {
        this.listener = listener;
    }

    public void setCustomizations(RoundButtonHelper.Builder builder) {
        if (builder.cornerRadius != null)
            cornerRadius = builder.cornerRadius;

        if (builder.cornerWidth != null)
            cornerWidth = builder.cornerWidth;

        if (builder.cornerColor != null)
            cornerColor = builder.cornerColor;

        if (builder.cornerColorSelected != null)
            cornerColorPressed = builder.cornerColorSelected;

        if (builder.cornerColorDisabled != null)
            cornerColorDisabled = builder.cornerColorDisabled;

        if (builder.backgroundColor != null)
            backgroundColor = builder.backgroundColor;

        if (builder.backgroundColorSelected != null)
            backgroundColorPressed = builder.backgroundColorSelected;

        if (builder.backgroundColorDisabled != null)
            backgroundColorDisabled = builder.backgroundColorDisabled;

        if (builder.textColor != null)
            textColor = builder.textColor;

        if (builder.textColorSelected != null)
            textColorPressed = builder.textColorSelected;

        if (builder.textColorDisabled != null)
            textColorDisabled = builder.textColorDisabled;

        if (builder.textColorDisabled != null)
            textColorDisabled = builder.textColorDisabled;

        if (builder.animationInnerResource != null)
            animationInnerResource = builder.animationInnerResource;

        if (builder.animationDurations != null)
            animationDurations = builder.animationDurations;

        if (builder.animationCornerRadius != null)
            animationCornerRadius = builder.animationCornerRadius;

        if (builder.animationProgressWidth != null)
            animationProgressWidth = builder.animationProgressWidth;

        if (builder.animationProgressColor != null)
            animationProgressColor = builder.animationProgressColor;

        if (builder.animationProgressPadding != null)
            animationProgressPadding = builder.animationProgressPadding;

        if (builder.animationAlpha != null)
            animationAlpha = builder.animationAlpha;

        if (builder.animationProgressStyle != null)
            animationProgressStyle = builder.animationProgressStyle;

        if (builder.resultSuccessColor != null)
            resultSuccessColor = builder.resultSuccessColor;

        if (builder.resultSuccessResource != null)
            resultSuccessResource = builder.resultSuccessResource;

        if (builder.resultFailureColor != null)
            resultFailureColor = builder.resultFailureColor;

        if (builder.resultFailureResource != null)
            resultFailureResource = builder.resultFailureResource;

        if (builder.text != null) {
            if (animationState == AnimationState.PROGRESS || animationState == AnimationState.MORPHING) {
                property.setText(builder.text);
            } else {
                setText(builder.text);
            }
        }

        update();
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
        showResultState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (animationState == AnimationState.PROGRESS) {
            drawIndeterminateProgress(canvas);
        } else if (animationState == AnimationState.DONE && resultState != ResultState.NONE) {
            drawDoneAnimation(canvas);
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        if (!progressDrawable.isRunning())
            progressDrawable.start();
        progressDrawable.draw(canvas);
    }

    private void drawDoneAnimation(Canvas canvas) {
        resultDrawable.draw(canvas);
    }

    public void startAnimation() {
        if (animationState != AnimationState.IDLE) {
            return;
        }

        stopAnimation();
        stopMorphing();

        property = new ButtonProperty(this);

        resultState = ResultState.NONE;
        animationState = AnimationState.MORPHING;

        int fromAlpha = 255, toAlpha = 255;
        if (animationAlpha) {
            toAlpha = 0;
        }
        morphTrasformations(property.getWidth(), property.getHeight(), property.getHeight(), property.getHeight(), cornerRadius, animationCornerRadius, fromAlpha, toAlpha, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onApplyMorphingEnd();
                }

                switch (animationProgressStyle) {
                    case CIRCLE:
                        progressDrawable = new CircularAnimatedDrawable(RoundButton.this, animationProgressWidth, animationProgressColor, animationInnerResource, animationInnerResourceColor);
                        if (animationInnerResource != 0)
                            setClickable(true);
                        break;
                    case DOTS:
                        progressDrawable = new DotsAnimatedDrawable(RoundButton.this, animationProgressColor);
                        break;
                }

                int offset = (getWidth() - getHeight()) / 2;

                int left = offset + animationProgressPadding;
                int right = getWidth() - offset - animationProgressPadding;
                int bottom = getHeight() - animationProgressPadding;
                int top = animationProgressPadding;

                progressDrawable.setBounds(left, top, right, bottom);
                progressDrawable.setCallback(RoundButton.this);
                progressDrawable.start();

                animationState = AnimationState.PROGRESS;

                if (listener != null) {
                    listener.onShowProgress();
                }

                if (resultState != ResultState.NONE) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showResultState();
                        }
                    }, 50);
                }
            }
        });
    }

    public void revertAnimation() {
        if (animationState == AnimationState.IDLE) {
            return;
        }

        stopAnimation();
        stopMorphing();

        resultState = ResultState.NONE;
        animationState = AnimationState.MORPHING;

        int fromAlpha = 255, toAlpha = 255;
        if (animationAlpha) {
            fromAlpha = 0;
        }

        morphTrasformations(getWidth(), getHeight(), property.getWidth(), property.getHeight(), animationCornerRadius, cornerRadius, fromAlpha, toAlpha, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setText(property.getText());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    setCompoundDrawablesRelative(property.getDrawables()[0], property.getDrawables()[1], property.getDrawables()[2], property.getDrawables()[3]);
                } else {
                    setCompoundDrawables(property.getDrawables()[0], property.getDrawables()[1], property.getDrawables()[2], property.getDrawables()[3]);
                }
                setClickable(true);
                animationState = AnimationState.IDLE;
                if (listener != null) {
                    listener.onRevertMorphingEnd();
                }
            }
        });
    }

    private void showResultState() {
        if (animationState != AnimationState.PROGRESS) {
            return;
        }

        stopAnimation();

        if (resultDrawable != null) {
            resultDrawable.stop();
            resultDrawable.dispose();
        }

        if (resultState == ResultState.SUCCESS) {
            Bitmap bitmap = resultSuccessResource != 0 ? BitmapFactory.decodeResource(getResources(), resultSuccessResource) : Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            resultDrawable = new CircularRevealAnimatedDrawable(RoundButton.this, resultSuccessColor, bitmap);
        } else {
            Bitmap bitmap = resultFailureResource != 0 ? BitmapFactory.decodeResource(getResources(), resultFailureResource) : Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            resultDrawable = new CircularRevealAnimatedDrawable(RoundButton.this, resultFailureColor, bitmap);
        }

        int left = 0;
        int right = getWidth();
        int bottom = getHeight();
        int top = 0;

        resultDrawable.setBounds(left, top, right, bottom);
        resultDrawable.setCallback(RoundButton.this);
        resultDrawable.start();

        if (listener != null) {
            listener.onShowResultState();
        }
    }

    private void stopMorphing() {
        if (morpSet != null && morpSet.isRunning()) {
            morpSet.cancel();
        }
    }

    public void stopAnimation() {
        if (animationState == AnimationState.PROGRESS && progressDrawable != null && progressDrawable.isRunning()) {
            progressDrawable.stop();
            animationState = AnimationState.DONE;
        }
    }

    private void morphTrasformations(int fromWidth, int fromHeight, int toWidth, int toHeight, int fromCorner, int toCorner, int fromAlpha, int toAlpha, Animator.AnimatorListener listener) {

        setCompoundDrawables(null, null, null, null);
        setText(null);
        setClickable(false);

        ValueAnimator cornerAnimation = ValueAnimator.ofInt(fromCorner, toCorner);
        cornerAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((GradientDrawable) getBackground().getCurrent()).setCornerRadius((Integer) animation.getAnimatedValue());
            }
        });

        ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = val;
                setLayoutParams(layoutParams);
            }
        });

        ValueAnimator heightAnimation = ValueAnimator.ofInt(fromHeight, toHeight);
        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = val;
                setLayoutParams(layoutParams);
            }
        });

        ValueAnimator alphaAnimator = ValueAnimator.ofInt(fromAlpha, toAlpha);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getBackground().getCurrent().setAlpha((Integer) animation.getAnimatedValue());
            }
        });

        morpSet = new AnimatorSet();
        morpSet.setDuration(animationDurations);
        morpSet.playTogether(cornerAnimation, widthAnimation, heightAnimation, alphaAnimator);
        morpSet.addListener(listener);
        morpSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (progressDrawable != null) {
            progressDrawable.dispose();
        }

        if (resultDrawable != null) {
            resultDrawable.dispose();
        }
    }

    public boolean isAnimating() {
        return animationState != AnimationState.IDLE;
    }

    public interface RoundButtonAnimationListener {
        /**
         * When the revert morphing animations end
         */
        void onRevertMorphingEnd();

        /**
         * When the morphing animations end
         */
        void onApplyMorphingEnd();

        /**
         * When the progress animations start showing, call in the same momenth of onApplyMorphingEnd
         */
        void onShowProgress();

        /**
         * When show the result state animations
         */
        void onShowResultState();
    }

    private class ButtonProperty {

        /**
         * The height of the button
         */
        private int height;

        /**
         * The width of the button
         */
        private int width;

        /**
         * The text of the button
         */
        private String text;

        /**
         * The drawable (left, top, right, bottom) of the button
         */
        private Drawable[] drawables;

        public ButtonProperty(Button button) {
            width = button.getWidth();
            height = button.getHeight();
            text = button.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                drawables = button.getCompoundDrawablesRelative();
            } else {
                drawables = button.getCompoundDrawables();
            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public String getText() {
            return text;
        }

        public Drawable[] getDrawables() {
            return drawables;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}