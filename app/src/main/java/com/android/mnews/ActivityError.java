package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ActivityError extends AppCompatActivity {

    TextView message;
    Button buttonExit;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        message = findViewById(R.id.activity_error_textview_message_ID);
        buttonExit = findViewById(R.id.activity_error_button_exit_ID);
    }
}