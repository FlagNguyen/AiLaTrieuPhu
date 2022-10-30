package com.example.ailatrieuphu.function.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ailatrieuphu.MainActivity;
import com.example.ailatrieuphu.R;

import java.util.Random;

public class AskingDialog extends Dialog implements View.OnClickListener {
    public Activity activity;

    RelativeLayout columnA, columnB, columnC, columnD;
    TextView tvPercentA, tvPercentB, tvPercentC, tvPercentD;
    Random random;
    int percentA, percentB, percentC, percentD;
    int btnPosition;
    RelativeLayout layout;
//    RelativeLayout.LayoutParams layoutParams;

    public AskingDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_asking);

        ((MainActivity) activity).help2.setBackgroundResource(R.drawable.help2_x);
        ((MainActivity) activity).help2.setEnabled(false);

        layout = findViewById(R.id.layout);
        layout.setOnClickListener(this);

        columnA = findViewById(R.id.columnA);
        columnB = findViewById(R.id.columnB);
        columnC = findViewById(R.id.columnC);
        columnD = findViewById(R.id.columnD);

        random = new Random();
        randomPercent();

        for (int i = 0; i < ((MainActivity) activity).buttonList.size(); i++) {
            if ((((MainActivity) activity).buttonList.get(i)).getText().toString().trim()
                    .equals(((MainActivity) activity).correctAnswer)) {
                btnPosition = i;
            }
        }
        if (btnPosition == 0) {
            while (percentA < percentB || percentA < percentC || percentA < percentD || percentA + percentB + percentC + percentD == 0)
                randomPercent();
        }
        if (btnPosition == 1) {
            while (percentB < percentA || percentB < percentC || percentB < percentD || percentA + percentB + percentC + percentD == 0)
                randomPercent();
        }
        if (btnPosition == 2) {
            while (percentC < percentA || percentC < percentB || percentC < percentD || percentA + percentB + percentC + percentD == 0)
                randomPercent();
        }
        if (btnPosition == 3) {
            while (percentD < percentA || percentD < percentB || percentD < percentC || percentA + percentB + percentC + percentD == 0)
                randomPercent();
        }

        columnA.getLayoutParams().height = percentA * 500 / 100;
        tvPercentA.setText(percentA + "%");

        columnB.getLayoutParams().height = percentB * 500 / 100;
        tvPercentB.setText(percentB + "%");

        columnC.getLayoutParams().height = percentC * 500 / 100;
        tvPercentC.setText(percentC + "%");

        columnD.getLayoutParams().height = percentD * 500 / 100;
        tvPercentD.setText(percentD + "%");
    }

    public void randomPercent() {
        if (((MainActivity) activity).isDropAnswer) {
            int pos1 = ((MainActivity) activity).position1;
            int pos2 = ((MainActivity) activity).position2;
            if (pos1 == 0 || pos2 == 0) {
                percentA = 0;
            } else {
                percentA = random.nextInt(100 + 1);
            }
            if (pos1 == 1 || pos2 == 1) {
                percentB = 0;
            } else {
                percentB = random.nextInt((100 - percentA) + 1);
            }
            if (pos1 == 2 || pos2 == 2) {
                percentC = 0;
            } else {
                percentC = random.nextInt((100 - percentA - percentB) + 1);
            }
            if (pos1 == 3 || pos2 == 3) {
                percentD = 0;
            } else {
                percentD = random.nextInt((100 - percentA - percentB - percentC) + 1);
            }
        } else {
            percentA = random.nextInt(100 + 1);
            percentB = random.nextInt((100 - percentA) + 1);
            percentC = random.nextInt((100 - percentA - percentB) + 1);
            percentD = random.nextInt((100 - percentA - percentB - percentA) + 1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}