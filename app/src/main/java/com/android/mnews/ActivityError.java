package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.mnews.constants.Errors;

public class ActivityError extends AppCompatActivity {

    TextView message;
    Button buttonExit,buttonRestart;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        message = findViewById(R.id.activity_error_textview_message_ID);
        buttonExit = findViewById(R.id.activity_error_button_exit_ID);
        buttonRestart = findViewById(R.id.activity_error_button_restart_ID);

        String errorNumber = getIntent().getStringExtra(Errors.ERROR_KEY);
        String errorText = null;

        if(errorNumber != null){

            switch (errorNumber)
            {
                case Errors.NO_INTERNET_CONNECTION:
                    errorText = "ERROR "+Errors.NO_INTERNET_CONNECTION+" has occurred : \nPlease connect to internet and restart the application";
                    break;

                case Errors.ACCESS_DENIED:
                    errorText = "ERROR "+Errors.ERROR_IN_THREAD_PARSER+" has occurred : \nThe administrator has decided to temporarily stop the service. Please come back after sometime";
                    break;

                case Errors.ERROR_IN_THREAD_DATA_LOADER:
                    errorText = "ERROR "+Errors.ERROR_IN_THREAD_DATA_LOADER+" has occurred : \nServer down!";
                    break;

                case Errors.ERROR_IN_THREAD_PARSER:
                    errorText = "ERROR "+Errors.ERROR_IN_THREAD_PARSER+" has occurred : \nInternal application error has occurred, Keep the application updated";
                    break;

                case Errors.ERROR_IN_ACTIVITY_MAIN:
                    errorText = "ERROR "+Errors.ERROR_IN_ACTIVITY_MAIN+" has occurred : \nUnexpected error has occurred";
                    break;

                case Errors.ERROR_IN_ACTIVITY_DISPLAY:
                    errorText = "ERROR "+Errors.ERROR_IN_ACTIVITY_DISPLAY+" has occurred : \nThis might be due to lack of system memory";
                    break;

                case Errors.ERROR_IN_ADAPTER_DISPLAY:
                    errorText = "ERROR "+Errors.ERROR_IN_ADAPTER_DISPLAY+" has occurred : \nThis might be due to lack of system memory";
                    break;

                default:
                    errorText = "Unknown Error!";
            }

            message.setText(errorText);
        }

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityError.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}