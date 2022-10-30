package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ailatrieuphu.repository.QuestionRepository;

public class MainActivity extends AppCompatActivity {

    FrameLayout scoreBoard;
    LinearLayout helper, questionLayout;
    TextView tvQuestionId, tvQuestion, tvTimer;
    Button button, btnA, btnB, btnC, btnD;
    ImageButton help1, help2, help3, help4;
    ImageView logo, rightCurtain, leftCurtain, level1, level2, level3, blur;

    QuestionRepository questionRepository;
    CountDownTimer timer1, timer2;

    MediaPlayer mediaPlayer, media15;

    // Player current question
    public int currentPosition = 1;
    // Question's position in DB
    int questionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void freeHeapMemory() {

    }
}