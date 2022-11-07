package com.example.ailatrieuphu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class StartActivity extends Activity implements View.OnClickListener {

    FrameLayout confirmFrame;
    RelativeLayout background;
    ImageView tapToPlay, logo;
    ImageButton btnMusic, btnOk, btnCancel;

    Vibrator vibe;
    Animation animation;
    AudioManager audioManager;
    MediaPlayer backgroundMusic, prepareSound;

    boolean isMusicOn = true;
    int volume = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        addControls();
    }

    // Constructor
    private void addControls() {

        // Construct for controls
        background = findViewById(R.id.background);
        confirmFrame = findViewById(R.id.confirm_frame);
        logo = findViewById(R.id.logo);
        tapToPlay = findViewById(R.id.tap_to_play);
        btnMusic = findViewById(R.id.btnMusic);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        // Set events
        background.setOnClickListener(this);
        tapToPlay.setOnClickListener(this);
        btnMusic.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        // Construct music
        backgroundMusic = new MediaPlayer();
        backgroundMusic = MediaPlayer.create(this, R.raw.opening);
        backgroundMusic.setLooping(true);
        prepareSound = MediaPlayer.create(this, R.raw.ready);

        // Access media manager to get music device info
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        // Get current device's volume
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Access vibration manager
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Handle onClick events
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tap_to_play:
            case R.id.background:
                confirmFrame.setVisibility(View.VISIBLE);
                prepareSound.start();
                break;
            case R.id.btnMusic:
                if (isMusicOn) {
                    view.setBackgroundResource(R.drawable.sound_on);
                    isMusicOn = false;
                    vibe.vibrate(200);
                } else {
                    view.setBackgroundResource(R.drawable.sound_off);
                    isMusicOn = true;
                }
                switchMusic();
                break;
            case R.id.btnCancel:
                confirmFrame.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnOk:
                // Create intent for move to next screen
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void switchMusic() {
        if (!isMusicOn) {
            // Turn on music in first volume
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, volume);
        } else {
            // Set volume to zero (Mute)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        }
    }

    // Animation of TapToPlay button
    private void runTapToPlayAnimation() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        logo.startAnimation(animation);
    }

    // Logo Animation
    public void runLogoAnimation() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        logo.startAnimation(animation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic.start();
        runTapToPlayAnimation();
        runLogoAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundMusic.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
        prepareSound.pause();
    }

    // Dialog confirm exit
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        // If user click return key on device
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("You want to exit game ?");
            dialog.setMessage("Thank you for playing this game. Have a good day!");
            dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            dialog.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            dialog.show();
        }

        return super.onKeyDown(keyCode, keyEvent);
    }
}