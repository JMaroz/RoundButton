# RoundButton

Android Button with corner, morph and animations

![gif](https://user-images.githubusercontent.com/22586803/32603218-31af6ecc-c549-11e7-8f83-b9d44eae4010.gif)

## Contents

- [Installation](#installation)
- [How to use](#how-to-use)
- [Configure XML](#configure-xml)
- [Bugs and feedback](#bugs-and-feedback)
- [Credits](#credits)
- [License](#license)

## Installation

[ ![Download](https://api.bintray.com/packages/maro/maven/RoundButton/images/download.svg) ](https://bintray.com/maro/maven/RoundButton/_latestVersion)

    compile 'com.marozzi.roundbutton:round-button:1.0.2'

## How to use

### Add to layout

    <com.marozzi.roundbutton.RoundButton
            android:id="@+id/bt"
            style="@style/TextStyle194"
            android:layout_width="88dp"
            android:layout_height="35dp"
            android:gravity="center"
            android:text="prenota"
            app:rb_animation_alpha="false"
            app:rb_animation_progress_color="@color/colorAccent"
            app:rb_animation_progress_padding="8dp"
            app:rb_animation_progress_width="2dp"
            app:rb_animation_progress_style="circle"
            app:rb_animation_corner_radius="20dp"
            app:rb_animation_duration="500"
            app:rb_background_color="@android:color/transparent"
            app:rb_success_resource="@drawable/ic_done_white_48dp"
            app:rb_background_color_pressed="@color/colorPrimary"
            app:rb_corner_color="@color/colorPrimary"
            app:rb_corner_color_pressed="@color/colorPrimary"
            app:rb_corner_radius="2dp"
            app:rb_corner_width="1dp"
            app:rb_text_color="@color/colorPrimary"
            app:rb_text_color_pressed="@android:color/white" />


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

### Bonus: use the builder to customize the button at runtime

       Random random = new Random();
       RoundButton.Builder builder = RoundButton.newRoundButtonBuilder()
               .withText("Bonus")
               .withBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
               .withTextColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
               .withCornerColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
               .withCornerRadius(random.nextInt(20))
               .withCornerWidth(random.nextInt(20));
       bt.setCustomizations(builder);
       bt.revertAnimation();

## Configure XML

###  Animations

- rb_animation_duration: duration of the animations in millisecond
- rb_animation_corner_radius: the radius of the botton corner
- rb_animation_alpha: true to perfom the morphing with alpha animation, false otherwise
- rb_animation_progress_width: the width of the progress bar
- rb_animation_progress_color: the color of the progress bar
- rb_animation_progress_padding: the padding from the outer border of the progress bar
- rb_animation_progress_style: the style of the animation
    - circle progress indicator
    - dots progress indicator

### Results

- rb_success_color: background color for the success state
- rb_success_resource: image for the success state
- rb_failure_color: background color for the failure state
- rb_failure_resource: image for the failure state

### Button

- rb_corner_radius: radius of the button corner
- rb_corner_width: width of the button corner
- rb_corner_color: the color of the corner
- rb_corner_color_pressed: the color when the button is pressed
- rb_background_color: the color of the background
- rb_background_color_pressed: the color of the background when the button is pressed
- rb_text_color: the color of the text
- rb_text_color_pressed: the color of the text when the button is pressed

## Bugs and Feedback

For bugs, feature requests, and discussion please use [GitHub Issues](https://github.com/JMaroz/RoundButton/issues)

## Credits

This library was inspired by this repos:

- https://github.com/dmytrodanylyk/android-morphing-button
- https://github.com/leandroBorgesFerreira/LoadingButtonAndroid

## License

    MIT License

    Copyright (c) 2017 JMaroz

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.