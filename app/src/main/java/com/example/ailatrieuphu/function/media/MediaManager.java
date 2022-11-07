package com.example.ailatrieuphu.function.media;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.ailatrieuphu.R;

public class MediaManager {
    MediaPlayer sound, backgroundMusic;
    Context context;

    public MediaManager() {
    }

    public MediaManager(Context context) {
        this.context = context;
    }


    public void play(int number) {
        switch (number) {
            case 1:
                sound = MediaPlayer.create(context, R.raw.ques_no1);
                break;
            case 2:
                sound = MediaPlayer.create(context, R.raw.ques_no2);
                break;
            case 3:
                sound = MediaPlayer.create(context, R.raw.ques_no3);
                break;
            case 4:
                sound = MediaPlayer.create(context, R.raw.ques_no4);
                break;
            case 5:
                sound = MediaPlayer.create(context, R.raw.ques_no5);
                break;
            case 6:
                sound = MediaPlayer.create(context, R.raw.ques_no6);
                break;
            case 7:
                sound = MediaPlayer.create(context, R.raw.ques_no7);
                break;
            case 8:
                sound = MediaPlayer.create(context, R.raw.ques_no8);
                break;
            case 9:
                sound = MediaPlayer.create(context, R.raw.ques_no9);
                break;
            case 10:
                sound = MediaPlayer.create(context, R.raw.ques_no10);
                break;
            case 11:
                sound = MediaPlayer.create(context, R.raw.ques_no11);
                break;
            case 12:
                sound = MediaPlayer.create(context, R.raw.ques_no13);
                break;
            case 14:
                sound = MediaPlayer.create(context, R.raw.ques_no14);
                break;
        }
        sound.start();
        if (number == 1) {
            backgroundMusic = MediaPlayer.create(context, R.raw.phase1);
        }
        if (number == 6) {
            backgroundMusic.reset();
            backgroundMusic = MediaPlayer.create(context, R.raw.phase2);
        }
        if (number == 11) {
            backgroundMusic.reset();
            backgroundMusic = MediaPlayer.create(context, R.raw.phase3);
        }
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    public void releaseMedia() {
        sound.release();
        backgroundMusic.release();
    }
}
