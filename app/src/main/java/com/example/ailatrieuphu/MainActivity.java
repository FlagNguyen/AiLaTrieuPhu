package com.example.ailatrieuphu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.ailatrieuphu.function.end_game.FinishDialog;
import com.example.ailatrieuphu.function.helper.AskingDialog;
import com.example.ailatrieuphu.function.helper.CallingDialog;
import com.example.ailatrieuphu.function.helper.ChangingDialog;
import com.example.ailatrieuphu.function.helper.DroppingDialog;
import com.example.ailatrieuphu.function.media.MediaManager;
import com.example.ailatrieuphu.repository.QuestionRepository;
import com.example.ailatrieuphu.repository.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    public FrameLayout scoreBoard;
    public LinearLayout helper, questionLayout;
    public TextView tvQuestionId, tvQuestion, tvTimer;
    public Button button, btnA, btnB, btnC, btnD;
    public ImageButton help1, help2, help3, help4;
    public ImageView logo, rightCurtain, leftCurtain, level1, level2, level3, blur;

    public QuestionRepository questionRepository;
    public CountDownTimer timer1, timer2;

    public MediaPlayer mediaPlayer, media15;

    // Player current question
    public int currentPosition = 1;
    // Question's position in DB
    public int questionId;
    public int positionA, positionB, positionC, position1, position2, totalCountDownTime;
    public boolean isStarted = false, isDropAnswer = false, selectTrue = false;

    public Random random;
    public Question question;
    public String correctAnswer;
    public Animation animation, animation1;
    public Handler handler = new Handler();

    // List question's randomer in DB which is chosen
    public ArrayList<Integer> passedList;
    // List answers
    public ArrayList<String> answerDataList;
    // List button A, B, C, D
    public ArrayList<Button> buttonList;
    // Adapter convert DB into list
    public ArrayList<Question> questionAdapter;
    public MediaManager mediaManager;
    public FinishDialog finishDialog;

    // Runnable: pre-load question and animations
    Runnable runnableMain = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            isDropAnswer = false;
            makeInvisible(false);
            makeButtonEnable(true);
            if (questionId == 1)
                helperAnimation();

            loadNextQuestion();
            questionAnimation();
            if (currentPosition < 15)
                mediaManager.play(currentPosition);
        }
    };

    // Runnable: Animation pick right/wrong
    Runnable runnableResult = new Runnable() {
        @Override
        public void run() {
            if (selectTrue) {
                setButtonSound(button);
                timer2 = new CountDownTimer(1000, 500) { // chạy từ 1s-0.5s
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        button.setBackgroundResource(R.drawable.select_right);
                        button.startAnimation(animation1);
                    }
                };
                timer2.start();
            } else {
                button.setBackgroundResource(R.drawable.select_wrong);
                for (int i = 0; i < buttonList.size(); i++) {
                    if ((buttonList.get(i)).getText().toString().trim().equals(correctAnswer)) {
                        (buttonList.get(i)).setBackgroundResource(R.drawable.select_right);
                        (buttonList.get(i)).startAnimation(animation1);
                        setButtonSound(buttonList.get(i));
                    }
                }
            }

            if (!selectTrue || currentPosition == 16) {
                timer2 = new CountDownTimer(4000, 2500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        blur.setVisibility(View.VISIBLE);
                        finishDialog.show();
                    }
                };
                timer2.start();
            }
        }
    };

    //Runnable: scoreBoard animation
    Runnable runnableAnim = new Runnable() {
        @Override
        public void run() {
            if (button != null)
                button.clearAnimation();
            makeInvisible(true);
            if (currentPosition == 1 || currentPosition == 6 || currentPosition == 11 || currentPosition == 15) {
                switch (currentPosition) {
                    case 6:
                        level1.startAnimation(animation1);
                        break;
                    case 11:
                        level1.clearAnimation();
                        level2.startAnimation(animation1);
                        break;
                    case 15:
                        level1.clearAnimation();
                        level2.clearAnimation();
                        level3.startAnimation(animation1);
                        media15 = MediaPlayer.create(getApplicationContext(), R.raw.ques_no15);
                        media15.start();
                        break;
                    default:
                        level1.clearAnimation();
                        level2.clearAnimation();
                        level3.clearAnimation();
                        break;
                }
                scoreBoardAnimation();
            }
        }
    };

    /**
     * Set up score board animation
     */
    private void scoreBoardAnimation() {
        scoreBoard.setVisibility(View.VISIBLE);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_out);
        scoreBoard.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_in);
                timer2 = new CountDownTimer(1000, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        scoreBoard.clearAnimation();
                    }

                    @Override
                    public void onFinish() {
                        scoreBoard.startAnimation(anim);
                        scoreBoard.setVisibility(View.INVISIBLE);
                    }
                };
                timer2.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void questionAnimation() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up_in);
        questionLayout.startAnimation(animation);
    }

    private void helperAnimation() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear);
        helper.startAnimation(animation);
    }

    public void freeHeapMemory() {
        // UI
        scoreBoard.setBackground(null);
        helper.setBackground(null);
        questionLayout.setBackground(null);
        tvQuestionId.setBackground(null);
        tvTimer.setBackground(null);
        tvQuestion.setBackground(null);
        btnA.setBackground(null);
        btnA.setOnClickListener(null);
        btnB.setBackground(null);
        btnB.setOnClickListener(null);
        btnC.setBackground(null);
        btnC.setOnClickListener(null);
        btnD.setBackground(null);
        btnD.setOnClickListener(null);
        help1.setImageDrawable(null);
        help1.setOnClickListener(null);
        help2.setImageDrawable(null);
        help2.setOnClickListener(null);
        help3.setImageDrawable(null);
        help3.setOnClickListener(null);
        help4.setImageDrawable(null);
        help4.setOnClickListener(null);
        leftCurtain.setImageDrawable(null);
        rightCurtain.setImageDrawable(null);
        logo.setImageDrawable(null);
        level1.setImageDrawable(null);
        level2.setImageDrawable(null);
        level3.setImageDrawable(null);

        // MediaPlayer
        mediaManager.releaseMedia();

        //Thread
        handler.removeCallbacks(runnableMain);
        handler.removeCallbacks(runnableResult);
        timer1.cancel();
        timer2.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_select);
        random = new Random();
        question = new Question();
        mediaPlayer = new MediaPlayer();

        passedList = new ArrayList<>();
        answerDataList = new ArrayList<>();
        buttonList = new ArrayList<>();
        questionAdapter = new ArrayList<>();

        buttonList = new ArrayList<>(Arrays.asList(btnA, btnB, btnC, btnD));
        mediaPlayer = MediaPlayer.create(this, R.raw.start);

        loadDatabase();
        makeInvisible(true);
        mediaManager = new MediaManager(MainActivity.this);
        finishDialog = new FinishDialog(MainActivity.this);
    }

    private void addControls() {
        rightCurtain = findViewById(R.id.right_curtain);
        leftCurtain = findViewById(R.id.left_curtain);
        logo = findViewById(R.id.imgLogo);
        blur = findViewById(R.id.blur);

        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);

        tvTimer = findViewById(R.id.tv_timer);
        tvQuestion = findViewById(R.id.tv_question);
        tvQuestionId = findViewById(R.id.tv_question_id);

        btnA = findViewById(R.id.btnA);
        btnA.setTag(btnA);
        btnA.setOnClickListener(this);

        btnB = findViewById(R.id.btnB);
        btnB.setTag(btnB);
        btnB.setOnClickListener(this);

        btnC = findViewById(R.id.btnC);
        btnC.setTag(btnC);
        btnC.setOnClickListener(this);

        btnD = findViewById(R.id.btnD);
        btnD.setTag(btnD);
        btnD.setOnClickListener(this);

        help1 = findViewById(R.id.help1);
        help1.setOnClickListener(this);
        help2 = findViewById(R.id.help2);
        help2.setOnClickListener(this);
        help3 = findViewById(R.id.help3);
        help3.setOnClickListener(this);
        help4 = findViewById(R.id.help4);
        help4.setOnClickListener(this);

        // Layouts
        helper = findViewById(R.id.helper);
        scoreBoard = findViewById(R.id.score_board);
        questionLayout = findViewById(R.id.question_layout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.help1:
                CallingDialog callingDialog = new CallingDialog(MainActivity.this);
                callingDialog.show();
                break;
            case R.id.help2:
                AskingDialog askingDialog = new AskingDialog(MainActivity.this);
                askingDialog.show();
                break;
            case R.id.help3:
                DroppingDialog droppingDialog = new DroppingDialog(MainActivity.this);
                droppingDialog.show();
                break;
            case R.id.help4:
                ChangingDialog changingDialog = new ChangingDialog(MainActivity.this);
                changingDialog.show();
                break;
            default:
                timer1.cancel();
                makeButtonEnable(false);
                button = (Button) view;
                button.setBackgroundResource(R.drawable.selected);

                if (!button.getText().toString().trim().equals("")) {
                    if (button.getText().toString().trim().equalsIgnoreCase(correctAnswer.trim())) {
                        selectTrue = true;
                        currentPosition++;
                        handler.postDelayed(runnableResult, 500);
                        if (currentPosition < 16) {
                            if (currentPosition == 6 || currentPosition == 11 || currentPosition == 15) {
                                handler.postDelayed(runnableAnim, 2000); // Delay 2s then execute
                                handler.postDelayed(runnableMain, 4000);
                            } else {
                                handler.postDelayed(runnableMain, 2500);
                            }
                        }
                    } else {
                        selectTrue = false;
                        handler.postDelayed(runnableResult, 1000);
                    }
                }
                break;
        }
    }

    /**
     * Set sound for A,B,C,D answer
     */
    public void setButtonSound(Button button) {
        if (button.getTag() == btnA) {
            mediaPlayer.reset();
            if (selectTrue) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.true_a);
            } else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong_a);
            }
        }

        if (button.getTag() == btnB) {
            mediaPlayer.reset();
            if (selectTrue) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.true_b);
            } else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong_b);
            }
        }

        if (button.getTag() == btnC) {
            mediaPlayer.reset();
            if (selectTrue) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.true_c);
            } else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong_c);
            }
        }

        if (button.getTag() == btnD) {
            mediaPlayer.reset();
            if (selectTrue) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.true_d);
            } else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong_d);
            }
        }
    }

    /**
     * Make buttons invisible when chosen answer
     */
    private void makeButtonEnable(boolean isEnable) {
        if (isEnable) {
            btnA.setClickable(true);
            btnB.setClickable(true);
            btnC.setClickable(true);
            btnD.setClickable(true);
        } else {
            btnA.setClickable(false);
            btnB.setClickable(false);
            btnC.setClickable(false);
            btnD.setClickable(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        curtainAnimation();
        logoAnimation();

        if (currentPosition == 1 && !isStarted) {
            handler.postDelayed(runnableAnim, 1000);
            handler.postDelayed(runnableMain, 3000);
            isStarted = true;
            mediaPlayer.start();
        }
    }

    private void logoAnimation() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        logo.startAnimation(animation);
    }

    private void curtainAnimation() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out);
        leftCurtain.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_out);
        rightCurtain.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                leftCurtain.setVisibility(View.GONE);
                rightCurtain.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        freeHeapMemory();
        super.onBackPressed();
    }

    /**
     * Count down timer
     */
    public void countDownTimer(String type) {
        if (currentPosition <= 5) {
            totalCountDownTime = 31000;
        } else if (currentPosition <= 10) {
            totalCountDownTime = 41000;
        } else if (currentPosition <= 14) {
            totalCountDownTime = 51000;
        } else {
            totalCountDownTime = 61000;
        }
        if (type.equals("start")) {
            timer1 = new CountDownTimer(totalCountDownTime, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTimer.setText("" + millisUntilFinished / 1000);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timeout);
                    if (millisUntilFinished / 1000 == 1) {
                        mediaPlayer.start();
                    }
                }

                @Override
                public void onFinish() {
                    tvTimer.setText("0");
                    blur.setVisibility(View.VISIBLE);
                    finishDialog.show();
                }
            };
            timer1.start();
        } else {
            if (timer1 != null) {
                timer1.cancel();
            }
        }
    }

    /**
     * Load data from database add into questionAdapter List
     */
    private void loadDatabase() {
        questionRepository = new QuestionRepository(MainActivity.this);
        questionRepository.create();
        questionRepository.open();
        questionAdapter = questionRepository.getQuestionBank();
        questionRepository.close();
    }

    private void makeInvisible(boolean isHide) {
        if (isHide) {
            tvQuestionId.setVisibility(View.INVISIBLE);
            tvQuestion.setVisibility(View.INVISIBLE);
            tvTimer.setVisibility(View.INVISIBLE);
            btnA.setVisibility(View.INVISIBLE);
            btnB.setVisibility(View.INVISIBLE);
            btnC.setVisibility(View.INVISIBLE);
            btnD.setVisibility(View.INVISIBLE);
            helper.setVisibility(View.INVISIBLE);
        } else {
            tvQuestionId.setVisibility(View.VISIBLE);
            tvQuestion.setVisibility(View.VISIBLE);
            tvTimer.setVisibility(View.VISIBLE);
            btnA.setVisibility(View.VISIBLE);
            btnB.setVisibility(View.VISIBLE);
            btnC.setVisibility(View.VISIBLE);
            btnD.setVisibility(View.VISIBLE);
            helper.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Next question
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadNextQuestion() {
        // Remove helper
        if (currentPosition > 1) {
            button.clearAnimation();
        }

        question = questionAdapter.stream().findFirst().get();
        answerDataList = new ArrayList<>(Arrays.asList(question.getAnswerA(), question.getAnswerB(),
                question.getAnswerC(), question.getAnswerD()));

        tvQuestionId.setText("Câu hỏi số " + currentPosition + ": ");
        tvQuestion.setText(question.getQuestion());
        btnA.setText(randomAnswer('A'));
        btnB.setText(randomAnswer('B'));
        btnC.setText(randomAnswer('C'));
        btnD.setText(randomAnswer('D'));
        correctAnswer = question.getCorrectAnswer();

        buttonList = new ArrayList<>(Arrays.asList(btnA, btnB, btnC, btnD));
        questionAdapter.remove(question);

        countDownTimer("stop");
        countDownTimer("start");
    }

    /**
     * Shuffle answers
     */
    private String randomAnswer(char answerPosition) {
        int answerData = random.nextInt(answerDataList.size());
        switch (answerPosition) {
            case 'A':
                positionA = answerData;
                btnA.setBackgroundResource(R.drawable.a);
                break;
            case 'B':
                while (answerData == positionA) {
                    answerData = random.nextInt(answerDataList.size());
                }
                positionB = answerData;
                btnB.setBackgroundResource(R.drawable.b);
                break;
            case 'C':
                while (answerData == positionA || answerData == positionB) {
                    answerData = random.nextInt(answerDataList.size());
                }
                positionC = answerData;
                btnC.setBackgroundResource(R.drawable.c);
                break;
            case 'D':
                while (answerData == positionA || answerData == positionB || answerData == positionC) {
                    answerData = random.nextInt(answerDataList.size());
                }
                btnD.setBackgroundResource(R.drawable.d);
                break;
        }
        return answerDataList.get(answerData);
    }

    public void dropHalfAnswer() {
        isDropAnswer = true;
        position1 = 0;
        position2 = 0;
        while (position1 == position2 || (buttonList.get(position1)).getText().toString().equalsIgnoreCase(correctAnswer)
                || (buttonList.get(position2)).getText().toString().equalsIgnoreCase(correctAnswer)) {
            position1 = random.nextInt(buttonList.size());
            position2 = random.nextInt(buttonList.size());
        }
        (buttonList.get(position1)).setText("");
        (buttonList.get(position1)).setClickable(false);
        (buttonList.get(position2)).setText("");
        (buttonList.get(position2)).setClickable(false);
    }

}