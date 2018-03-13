package com.marozzi.roundbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by amarozzi on 06/12/2017.
 */

public class RoundButtonHelper {

    public static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        return drawable instanceof BitmapDrawable ? ((BitmapDrawable) drawable).getBitmap() : getBitmap(drawable);
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

    public static final class Builder implements Parcelable {

        Integer animationDurations;
        Integer animationCornerRadius;
        Boolean animationAlpha;
        Integer animationProgressWidth;
        Integer animationProgressColor;
        Integer animationProgressPadding;
        Integer animationInnerResource;
        RoundButton.AnimationProgressStyle animationProgressStyle;
        Integer resultSuccessColor;
        Integer resultSuccessResource;
        Integer resultFailureColor;
        Integer resultFailureResource;
        Integer cornerRadius;
        Integer cornerWidth;
        Integer cornerColor;
        Integer cornerColorSelected;
        Integer cornerColorDisabled;
        Integer backgroundColor;
        Integer backgroundColorSelected;
        Integer backgroundColorDisabled;
        Integer textColor;
        Integer textColorSelected;
        Integer textColorDisabled;
        String text;

        Builder() {
        }

        Builder(Parcel in) {
            this.animationDurations = (Integer) in.readValue(Integer.class.getClassLoader());
            this.animationCornerRadius = (Integer) in.readValue(Integer.class.getClassLoader());
            this.animationAlpha = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.animationProgressWidth = (Integer) in.readValue(Integer.class.getClassLoader());
            this.animationProgressColor = (Integer) in.readValue(Integer.class.getClassLoader());
            this.animationProgressPadding = (Integer) in.readValue(Integer.class.getClassLoader());
            this.animationInnerResource = (Integer) in.readValue(Integer.class.getClassLoader());
            int tmpAnimationProgressStyle = in.readInt();
            this.animationProgressStyle = tmpAnimationProgressStyle == -1 ? null : RoundButton.AnimationProgressStyle.values()[tmpAnimationProgressStyle];
            this.resultSuccessColor = (Integer) in.readValue(Integer.class.getClassLoader());
            this.resultSuccessResource = (Integer) in.readValue(Integer.class.getClassLoader());
            this.resultFailureColor = (Integer) in.readValue(Integer.class.getClassLoader());
            this.resultFailureResource = (Integer) in.readValue(Integer.class.getClassLoader());
            this.cornerRadius = (Integer) in.readValue(Integer.class.getClassLoader());
            this.cornerWidth = (Integer) in.readValue(Integer.class.getClassLoader());
            this.cornerColor = (Integer) in.readValue(Integer.class.getClassLoader());
            this.cornerColorSelected = (Integer) in.readValue(Integer.class.getClassLoader());
            this.cornerColorDisabled = (Integer) in.readValue(Integer.class.getClassLoader());
            this.backgroundColor = (Integer) in.readValue(Integer.class.getClassLoader());
            this.backgroundColorSelected = (Integer) in.readValue(Integer.class.getClassLoader());
            this.backgroundColorDisabled = (Integer) in.readValue(Integer.class.getClassLoader());
            this.textColor = (Integer) in.readValue(Integer.class.getClassLoader());
            this.textColorSelected = (Integer) in.readValue(Integer.class.getClassLoader());
            this.textColorDisabled = (Integer) in.readValue(Integer.class.getClassLoader());
            this.text = in.readString();
        }

        public Builder withAnimationInnerResource(@DrawableRes int animationInnerResource) {
            this.animationInnerResource = animationInnerResource;
            return this;
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

        public Builder withCornerColorDisabled(int cornerColorDisabled) {
            this.cornerColorDisabled = cornerColorDisabled;
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

        public Builder withBackgroundColorDisabled(int backgroundColorDisabled) {
            this.backgroundColorDisabled = backgroundColorDisabled;
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

        public Builder withTextColorDisabled(int textColorDisabled) {
            this.textColorDisabled = textColorDisabled;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.animationDurations);
            dest.writeValue(this.animationCornerRadius);
            dest.writeValue(this.animationAlpha);
            dest.writeValue(this.animationProgressWidth);
            dest.writeValue(this.animationProgressColor);
            dest.writeValue(this.animationProgressPadding);
            dest.writeValue(this.animationInnerResource);
            dest.writeInt(this.animationProgressStyle == null ? -1 : this.animationProgressStyle.ordinal());
            dest.writeValue(this.resultSuccessColor);
            dest.writeValue(this.resultSuccessResource);
            dest.writeValue(this.resultFailureColor);
            dest.writeValue(this.resultFailureResource);
            dest.writeValue(this.cornerRadius);
            dest.writeValue(this.cornerWidth);
            dest.writeValue(this.cornerColor);
            dest.writeValue(this.cornerColorSelected);
            dest.writeValue(this.cornerColorDisabled);
            dest.writeValue(this.backgroundColor);
            dest.writeValue(this.backgroundColorSelected);
            dest.writeValue(this.backgroundColorDisabled);
            dest.writeValue(this.textColor);
            dest.writeValue(this.textColorSelected);
            dest.writeValue(this.textColorDisabled);
            dest.writeString(this.text);
        }

        public static final Parcelable.Creator<Builder> CREATOR = new Parcelable.Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel source) {
                return new Builder(source);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
    }
}
