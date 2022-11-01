package com.example.ailatrieuphu.function.end_game;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ailatrieuphu.MainActivity;
import com.example.ailatrieuphu.R;

public class FinishDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    ImageButton btnOk;
    FrameLayout layout, effect;
    TextView tvTitle, tvMoney;
    Animation animation;
    MediaPlayer mediaPlayer, mediaPlayer1;
    CountDownTimer countDownTimer;

    public FinishDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_finish);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_money);

        btnOk = (ImageButton) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        layout = (FrameLayout) findViewById(R.id.layout);
        layout.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvMoney.startAnimation(animation);

        int successNumber = ((MainActivity) activity).currentPosition;
        if (successNumber <= 15) {
            tvTitle.setText("Rất tiếc! Bạn phải dừng cuộc chơi tại đây!");
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.lose);
            mediaPlayer.start();

            if (successNumber <= 5) {
                tvMoney.setText("0");
            } else if (successNumber <= 10) {
                tvMoney.setText("2.000.000");
            } else {
                tvMoney.setText("22.000.000");
            }
        } else {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.win_1);
            mediaPlayer1 = MediaPlayer.create(getContext(), R.raw.win_2);
            mediaPlayer.start();
            mediaPlayer.setNextMediaPlayer(mediaPlayer1);
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
        ((MainActivity) activity).finish();
        ((MainActivity) activity).freeHeapMemory();
    }
}
