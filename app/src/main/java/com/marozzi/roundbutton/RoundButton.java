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
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by amarozzi on 21/09/2017.
 */

public class RoundButton extends AppCompatButton {

    public static Builder newRoundButtonBuilder() {
        return new Builder();
    }

    // start animations
    private enum AnimationState {
        IDLE, MORPHING, PROGRESS, DONE
    }

    public enum ResultState {
        NONE, SUCCESS, FAILURE,
    }

    private CircularAnimatedDrawable progressDrawable;
    private CircularRevealAnimatedDrawable resultDrawable;
    private AnimatorSet morpSet;

    private RoundButtonAnimationListener listener;
    private ButtonProperty property;

    private ResultState resultState = ResultState.NONE;
    private AnimationState animationState = AnimationState.IDLE;

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
    private int animationBarWidth;

    /**
     * The color of the bar during the animations
     */
    private int animationBarColor;

    /**
     * The padding of the bar during the animations
     */
    private int animationBarPadding;

    /**
     * Indicate if animate with alpha
     */
    private boolean animationAlpha;

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
    private int cornerWidth = 20;

    private int cornerColor;
    private int cornerColorSelected;

    private int backgroundColor;
    private int backgroundColorSelected;

    private int textColor;
    private int textColorSelected;

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

        cornerColor = cornerColorSelected = a.getColor(R.styleable.RoundButton_rb_corner_color, Color.TRANSPARENT);
        if (a.hasValue(R.styleable.RoundButton_rb_corner_color_selected))
            cornerColorSelected = a.getColor(R.styleable.RoundButton_rb_corner_color_selected, Color.TRANSPARENT);

        backgroundColor = backgroundColorSelected = a.getColor(R.styleable.RoundButton_rb_background_color, Color.TRANSPARENT);
        if (a.hasValue(R.styleable.RoundButton_rb_background_color_selected))
            backgroundColorSelected = a.getColor(R.styleable.RoundButton_rb_background_color_selected, Color.TRANSPARENT);

        textColor = textColorSelected = a.getColor(R.styleable.RoundButton_rb_text_color, Color.TRANSPARENT);
        if (a.hasValue(R.styleable.RoundButton_rb_text_color_selected))
            textColorSelected = a.getColor(R.styleable.RoundButton_rb_text_color_selected, Color.TRANSPARENT);

        animationDurations = a.getInt(R.styleable.RoundButton_rb_animation_duration, 300);
        animationBarWidth = a.getDimensionPixelSize(R.styleable.RoundButton_rb_animation_bar_width, 20);
        animationBarColor = a.getColor(R.styleable.RoundButton_rb_animation_bar_color, backgroundColor);
        animationBarPadding = a.getDimensionPixelSize(R.styleable.RoundButton_rb_animation_bar_padding, 10);
        animationCornerRadius = a.getDimensionPixelSize(R.styleable.RoundButton_rb_animation_corner_radius, 0);
        animationAlpha = a.getBoolean(R.styleable.RoundButton_rb_animation_alpha, false);

        resultSuccessColor = a.getColor(R.styleable.RoundButton_rb_success_color, Color.GREEN);
        resultSuccessResource = a.getResourceId(R.styleable.RoundButton_rb_success_resource, 0);
        resultFailureColor = a.getColor(R.styleable.RoundButton_rb_failure_color, Color.RED);
        resultFailureResource = a.getResourceId(R.styleable.RoundButton_rb_failure_resource, 0);

        a.recycle();

        update();
    }

    public void update() {
        StateListDrawable background = new StateListDrawable();
        background.addState(new int[]{android.R.attr.state_pressed}, createDrawable(backgroundColorSelected, cornerColorSelected, cornerWidth, cornerRadius));
        background.addState(StateSet.WILD_CARD, createDrawable(backgroundColor, cornerColor, cornerWidth, cornerRadius));
        setBackground(background);

        setTextColor(new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        textColorSelected,
                        textColor
                }
        ));
    }

    private GradientDrawable createDrawable(int color, int cornerColor, int cornerSize, int cornerRadius) {
        GradientDrawable out = new GradientDrawable();
        out.setColor(color);
        out.setStroke(cornerSize, cornerColor);
        out.setCornerRadius(cornerRadius);
        return out;
    }

    public void setButtonAnimationListener(RoundButtonAnimationListener listener) {
        this.listener = listener;
    }

    public void setCustomizations(Builder builder) {
        if (builder.cornerRadius != null)
            cornerRadius = builder.cornerRadius;

        if (builder.cornerWidth != null)
            cornerWidth = builder.cornerWidth;

        if (builder.cornerColor != null)
            cornerColor = builder.cornerColor;

        if (builder.cornerColorSelected != null)
            cornerColorSelected = builder.cornerColorSelected;

        if (builder.backgroundColor != null)
            backgroundColor = builder.backgroundColor;

        if (builder.backgroundColorSelected != null)
            backgroundColorSelected = builder.backgroundColorSelected;

        if (builder.textColor != null)
            textColor = builder.textColor;

        if (builder.textColorSelected != null)
            textColorSelected = builder.textColorSelected;

        if (builder.animationDurations != null)
            animationDurations = builder.animationDurations;

        if (builder.animationCornerRadius != null)
            animationCornerRadius = builder.animationCornerRadius;

        if (builder.animationBarWidth != null)
            animationBarWidth = builder.animationBarWidth;

        if (builder.animationBarColor != null)
            animationBarColor = builder.animationBarColor;

        if (builder.animationBarPadding != null)
            animationBarPadding = builder.animationBarPadding;

        if (builder.animationAlpha != null)
            animationAlpha = builder.animationAlpha;

        if (builder.resultSuccessColor != null)
            resultSuccessColor = builder.resultSuccessColor;

        if (builder.resultSuccessResource != null)
            resultSuccessResource = builder.resultSuccessResource;

        if (builder.resultFailureColor != null)
            resultFailureColor = builder.resultFailureColor;

        if (builder.resultFailureResource != null)
            resultFailureResource = builder.resultFailureResource;

        if (builder.text != null) {
            if (animationState == AnimationState.PROGRESS) {
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

    /**
     * This method is called when the button and its dependencies are going to draw it selves.
     *
     * @param canvas Canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (animationState == AnimationState.PROGRESS) {
            drawIndeterminateProgress(canvas);
        } else if (animationState == AnimationState.DONE && resultState != ResultState.NONE) {
            drawDoneAnimation(canvas);
        }
    }

    /**
     * If the mAnimatedDrawable is null or its not running, it get created. Otherwise its draw method is
     * called here.
     *
     * @param canvas Canvas
     */
    private void drawIndeterminateProgress(Canvas canvas) {
        if (!progressDrawable.isRunning())
            progressDrawable.start();
        progressDrawable.draw(canvas);
    }

    /**
     * Method called on the onDraw when the button is on DONE status
     *
     * @param canvas Canvas
     */
    private void drawDoneAnimation(Canvas canvas) {
        resultDrawable.draw(canvas);
    }

    /**
     * Method called to start the animation. Morphs in to a ball and then starts a loading spinner.
     */
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

                progressDrawable = new CircularAnimatedDrawable(RoundButton.this, animationBarWidth, animationBarColor);

                int offset = (getWidth() - getHeight()) / 2;

                int left = offset + animationBarPadding;
                int right = getWidth() - offset - animationBarPadding;
                int bottom = getHeight() - animationBarPadding;
                int top = animationBarPadding;

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
                setCompoundDrawablesRelative(property.getDrawables()[0], property.getDrawables()[1], property.getDrawables()[2], property.getDrawables()[3]);
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
            if (resultDrawable.isRunning())
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

    /**
     * Stops the animation and sets the button in the STOPPED state.
     */
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
         *  When show the result state animations
         */
        void onShowResultState();
    }

    public static final class Builder {
        private Integer animationDurations;
        private Integer animationCornerRadius;
        private Integer animationBarWidth;
        private Integer animationBarColor;
        private Integer animationBarPadding;
        private Boolean animationAlpha;
        private Integer resultSuccessColor;
        private Integer resultSuccessResource;
        private Integer resultFailureColor;
        private Integer resultFailureResource;
        private Integer cornerRadius;
        private Integer cornerWidth;
        private Integer cornerColor;
        private Integer cornerColorSelected;
        private Integer backgroundColor;
        private Integer backgroundColorSelected;
        private Integer textColor;
        private Integer textColorSelected;
        private String text;

        private Builder() {
        }

        public Builder withAnimationDurations(int animationDurations) {
            this.animationDurations = animationDurations;
            return this;
        }

        public Builder withAnimationCornerRadius(int animationCornerRadius) {
            this.animationCornerRadius = animationCornerRadius;
            return this;
        }

        public Builder withAnimationBarWidth(int animationBarWidth) {
            this.animationBarWidth = animationBarWidth;
            return this;
        }

        public Builder withAnimationBarColor(int animationBarColor) {
            this.animationBarColor = animationBarColor;
            return this;
        }

        public Builder withAnimationBarPadding(int animationBarPadding) {
            this.animationBarPadding = animationBarPadding;
            return this;
        }

        public Builder withAnimationAlpha(boolean animationAlpha) {
            this.animationAlpha = animationAlpha;
            return this;
        }

        public Builder withResultSuccessColor(int resultSuccessColor) {
            this.resultSuccessColor = resultSuccessColor;
            return this;
        }

        public Builder withResultSuccessResource(int resultSuccessResource) {
            this.resultSuccessResource = resultSuccessResource;
            return this;
        }

        public Builder withResultFailureColor(int resultFailureColor) {
            this.resultFailureColor = resultFailureColor;
            return this;
        }

        public Builder withResultFailureResource(int resultFailureResource) {
            this.resultFailureResource = resultFailureResource;
            return this;
        }

        public Builder withCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        public Builder withCornerWidth(int cornerWidth) {
            this.cornerWidth = cornerWidth;
            return this;
        }

        public Builder withCornerColor(int cornerColor) {
            this.cornerColor = cornerColor;
            return this;
        }

        public Builder withCornerColorSelected(int cornerColorSelected) {
            this.cornerColorSelected = cornerColorSelected;
            return this;
        }

        public Builder withBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder withBackgroundColorSelected(int backgroundColorSelected) {
            this.backgroundColorSelected = backgroundColorSelected;
            return this;
        }

        public Builder withTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder withTextColorSelected(int textColorSelected) {
            this.textColorSelected = textColorSelected;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }
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
            drawables = button.getCompoundDrawablesRelative();
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