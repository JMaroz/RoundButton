package com.marozzi.app;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marozzi.roundbutton.RoundButton;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    @OnClick(R.id.bt_corner)
    void onClickBtCorner(View view) {
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
        bt.startAnimation();
        bt.postDelayed(new Runnable() {
            @Override
            public void run() {
                bt.revertAnimation();
            }
        }, 3000);
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
                RoundButton.Builder builder = RoundButton.newRoundButtonBuilder()
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
