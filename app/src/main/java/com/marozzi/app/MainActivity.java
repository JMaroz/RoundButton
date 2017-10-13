package com.marozzi.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.marozzi.roundbutton.RoundButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RoundButton bt = (RoundButton) findViewById(R.id.bt);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }, 6000);
            }
        });

    }
}
