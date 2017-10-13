# RoundButton
Android Button with corner, morph and animations

## Contents

- [Installation](#installation)
- [How to use](#how-to-use)
- [Configure XML](#configure-xml)
- [Bugs and feedback](#bugs-and-feedback)
- [Credits](#credits)

## Installation

    compile 'com.marozzi.roundbutton:round-bottom:[latest-version]'

## How to use

### Add to Layout

    <com.marozzi.roundbutton.RoundButton
            android:id="@+id/bt"
            style="@style/TextStyle194"
            android:layout_width="88dp"
            android:layout_height="35dp"
            android:layout_alignParentEnd="true"
            android:layout_alignParentRight="true"
            android:layout_centerVertical="true"
            android:layout_marginEnd="2dp"
            android:layout_marginRight="2dp"
            android:gravity="center"
            android:text="prenota"
            app:rb_animation_alpha="false"
            app:rb_animation_bar_color="@color/colorAccent"
            app:rb_animation_bar_padding="8dp"
            app:rb_animation_bar_width="2dp"
            app:rb_animation_corner_radius="20dp"
            app:rb_animation_duration="500"
            app:rb_background_color="@android:color/transparent"
            app:rb_success_resource="@drawable/ic_done_white_48dp"
            app:rb_background_color_selected="@color/colorPrimary"
            app:rb_corner_color="@color/colorPrimary"
            app:rb_corner_color_selected="@color/colorPrimary"
            app:rb_corner_radius="2dp"
            app:rb_corner_width="1dp"
            app:rb_text_color="@color/colorPrimary"
            app:rb_text_color_selected="@android:color/white" />


### Start the animation

To start the animation and the morphing of the button

        RoundButton btn = (RoundButton) findViewById(R.id.bt)

        btn.startAnimation();

### Revert the animation

When you are done and you want to morph back the button

        btn.revertAnimation();

### Show result animation

You can show a result of your task by calling

    btn.etResultState(ResultState.SUCCESS | ResultState.FAILURE);

If the button is in the 'progress state' the button will show the result otherwise it will show after the morph phase is endend

### Listener

You can set a listener for every action

    bt.setButtonAnimationListener(new RoundButton.RoundButtonAnimationListener() {
                @Override
                public void onRevertMorphingEnd() {

                }

                @Override
                public void onApplyMorphingEnd() {

                }

                @Override
                public void onShowProgress() {

                }

                @Override
                public void onShowResultState() {

                }
            });

## Configure XML

###  Animations

    - rb_animation_duration: duration of the animations in millisecond
    - rb_animation_bar_width: the width of the progress bar
    - rb_animation_bar_color: the color of the progress bar
    - rb_animation_bar_padding: the padding from the outer border of the progress bar
    - rb_animation_corner_radius: the radius of the botton corner
    - rb_animation_alpha: true to perfom the morphing with alpha animation, false otherwise

### Results

    - rb_success_color: background color for the success state
    - rb_success_resource: image for the success state
    - rb_failure_color: background color for the failure state
    - rb_failure_resource: image for the failure state

### Button

    - rb_corner_radius: radius of the button corner
    - rb_corner_width: width of the button corner
    - rb_corner_color: the color of the corner
    - rb_corner_color_selected: the color when the button is pressed
    - rb_background_color: the color of the background
    - rb_background_color_selected: the color of the background when the button is pressed
    - rb_text_color: the color of the text
    - rb_text_color_selected: the color of the text when the button is pressed

## Bugs and Feedback

For bugs, feature requests, and discussion please use [GitHub Issues](https://github.com/JMaroz/RoundButton/issues)

## Credits

This library was inspired by this repos:
- https://github.com/dmytrodanylyk/android-morphing-button
- https://github.com/leandroBorgesFerreira/LoadingButtonAndroid

