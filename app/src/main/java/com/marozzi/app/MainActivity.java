package com.marozzi.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.marozzi.roundbutton.RoundButton;
import com.marozzi.roundbutton.RoundButtonHelper;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_custom)
    void onClickCustom(final RoundButton view) {
        view.startAnimation();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.revertAnimation();
            }
        }, 5000);
    }

    @OnClick(R.id.bt_messanger)
    void onClickBtMessanger(View view) {
        final RoundButton bt = (RoundButton) view;
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.setResultState(RoundButton.ResultState.SUCCESS);
            }
        }, 3000);
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                RoundButtonHelper.Builder builder = RoundButton.newBuilder()
                        .withText("Sended")
                        .withBackgroundColor(Color.TRANSPARENT)
                        .withTextColor(Color.DKGRAY)
                        .withWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .withCornerRadius(0)
                        .withCornerWidth(0);
                bt.setCustomizations(builder);
                bt.revertAnimation();
            }
        }, 5000);
    }

    @OnClick(R.id.bt_dot)
    void onClickBtDot(View view) {
        final RoundButton bt = (RoundButton) view;
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.revertAnimation();
            }
        }, 3000);
    }

    @OnClick(R.id.bt)
    void onClickBt(View view) {
        final RoundButton bt = (RoundButton) view;
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.revertAnimation();
            }
        }, 3000);
    }

    @OnClick(R.id.bt_round)
    void onClickBtRound(View view) {
        final RoundButton bt = (RoundButton) view;
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.revertAnimation();
            }
        }, 3000);
    }

    @OnClick(R.id.bt_alpha)
    void onClickBtAlpha(View view) {
        final RoundButton bt = (RoundButton) view;
        if (bt.isAnimating()) {
            bt.setResultState(RoundButton.ResultState.FAILURE);
        } else {
            bt.startAnimation();
            bt.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bt.revertAnimation();
                }
            }, 3000);
        }
    }

    @OnClick(R.id.bt_success)
    void onClickBtSuccess(View view) {
        final RoundButton bt = (RoundButton) view;
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.setResultState(RoundButton.ResultState.SUCCESS);
            }
        }, 3000);
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.revertAnimation();
            }
        }, 7000);
    }

    @OnClick(R.id.bt_failure)
    void onClickBtFilure(View view) {
        final RoundButton bt = (RoundButton) view;
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.setResultState(RoundButton.ResultState.FAILURE);
            }
        }, 3000);
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.revertAnimation();
            }
        }, 7000);
    }

    @OnClick(R.id.bt_bonus)
    void onClickBtBonus(View view) {
        final RoundButton bt = (RoundButton) view;
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                RoundButtonHelper.Builder builder = RoundButton.newBuilder()
                        .withText("Bonus")
                        .withBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                        .withTextColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                        .withCornerColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                        .withCornerRadius(random.nextInt(20))
                        .withCornerWidth(random.nextInt(20));
                bt.setCustomizations(builder);
                bt.revertAnimation();
            }
        }, 3000);
    }
}
