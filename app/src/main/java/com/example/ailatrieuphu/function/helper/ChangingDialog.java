package com.example.ailatrieuphu.function.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;

import com.example.ailatrieuphu.MainActivity;
import com.example.ailatrieuphu.R;

public class ChangingDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    ImageButton btnYes, btnNo;

    public ChangingDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_changing);
        btnYes = findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnYes:
                ((MainActivity) activity).loadNextQuestion();
                ((MainActivity) activity).help4.setBackgroundResource(R.drawable.help4_x);
                ((MainActivity) activity).help4.setEnabled(false);
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }
}