package com.example.ailatrieuphu.function.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ailatrieuphu.MainActivity;
import com.example.ailatrieuphu.R;

public class CallingDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    //    public Context context;
    public ImageButton call1, call2, call3, call4;
    public ImageView call_full;
    public TextView tv;

    public CallingDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_calling);

        ((MainActivity) activity).help1.setBackgroundResource(R.drawable.help1_x);
        ((MainActivity) activity).help1.setEnabled(false);

        tv = findViewById(R.id.tv);

        call1 = findViewById(R.id.call1);
        call1.setOnClickListener(this);
        call2 = findViewById(R.id.call2);
        call2.setOnClickListener(this);
        call3 = findViewById(R.id.call3);
        call3.setOnClickListener(this);
        call4 = findViewById(R.id.call4);
        call4.setOnClickListener(this);
        call_full = findViewById(R.id.call_full);
        call_full.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String ans = ((MainActivity) activity).correctAnswer;
        switch (view.getId()) {
            case R.id.call1:
                call_full.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_man_1);
                break;
            case R.id.call2:
                call_full.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_man_2);
                break;
            case R.id.call3:
                call_full.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_man_3);
                break;
            case R.id.call4:
                call_full.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_man_4);
                break;
            default:
                dismiss();
                break;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(Html.fromHtml("Tôi nghĩ &quot;" + ans + "&quot; <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "là câu trả lời đúng"));
    }
}