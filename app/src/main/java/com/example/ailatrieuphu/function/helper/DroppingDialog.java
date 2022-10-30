package com.example.ailatrieuphu.function.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.ailatrieuphu.MainActivity;
import com.example.ailatrieuphu.R;

public class DroppingDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    ImageButton btnYes, btnNo;

    public DroppingDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_dropping);

        btnYes = findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                ((MainActivity) activity).dropHalfAnswer();
                ((MainActivity) activity).help3.setBackgroundResource(R.drawable.help3_x);
                ((MainActivity) activity).help3.setEnabled(false);
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }
}