package com.marozzi.roundbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by amarozzi on 06/12/2017.
 */

public class RoundButtonHelper {

    public static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        return drawable instanceof BitmapDrawable ?((BitmapDrawable)drawable).getBitmap():getBitmap(drawable);
    }

    public static Bitmap getBitmap(Drawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static RoundButtonHelper.Builder newBuilder() {
        return new RoundButtonHelper.Builder();
    }

    public static final class Builder {

        Integer animationDurations;
        Integer animationCornerRadius;
        Boolean animationAlpha;
        Integer animationProgressWidth;
        Integer animationProgressColor;
        Integer animationProgressPadding;
        RoundButton.AnimationProgressStyle animationProgressStyle;
        Integer resultSuccessColor;
        Integer resultSuccessResource;
        Integer resultFailureColor;
        Integer resultFailureResource;
        Integer cornerRadius;
        Integer cornerWidth;
        Integer cornerColor;
        Integer cornerColorSelected;
        Integer backgroundColor;
        Integer backgroundColorSelected;
        Integer textColor;
        Integer textColorSelected;
        String text;

        Builder() {
        }

        public Builder withAnimationDurations(int animationDurations) {
            this.animationDurations = animationDurations;
            return this;
        }

        public Builder withAnimationCornerRadius(int animationCornerRadius) {
            this.animationCornerRadius = animationCornerRadius;
            return this;
        }

        public Builder withAnimationAlpha(boolean animationAlpha) {
            this.animationAlpha = animationAlpha;
            return this;
        }

        public Builder withAnimationProgressWidth(int animationBarWidth) {
            this.animationProgressWidth = animationBarWidth;
            return this;
        }

        public Builder withAnimationProgressColor(int animationBarColor) {
            this.animationProgressColor = animationBarColor;
            return this;
        }

        public Builder withAnimationProgressPadding(int animationBarPadding) {
            this.animationProgressPadding = animationBarPadding;
            return this;
        }

        public Builder withAnimationProgressStyle(RoundButton.AnimationProgressStyle animationProgressStyle) {
            this.animationProgressStyle = animationProgressStyle;
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
}
