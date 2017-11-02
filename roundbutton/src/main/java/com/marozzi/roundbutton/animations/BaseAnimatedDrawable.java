package com.marozzi.roundbutton.animations;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Animatable;

/**
 * Created by amarozzi on 02/11/2017.
 */

public abstract class BaseAnimatedDrawable extends Drawable implements Animatable {

    /**
     * Clear all resource
     */
    public abstract void dispose();

    /**
     * Set up the animations.
     */
    public abstract void setupAnimations();
}
