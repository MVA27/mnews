package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.mnews.administrator.ThreadParseHTML;
import com.android.mnews.mediastack.Data;
import com.android.mnews.mediastack.Holder;
import com.android.mnews.threads.ThreadDataLoader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Holder holder;
    public static List<Data> data;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //From Administrator Package
        new ThreadParseHTML().start();
        while(ThreadParseHTML.holder == null);

        if(ThreadParseHTML.holder.getFlag() == 0){
            Intent intent = new Intent(MainActivity.this,ActivityError.class);
            startActivity(intent);
        }
        else {
            //start the thread and pass context to show toast
            if (MainActivity.data == null) {
                new ThreadDataLoader().start();
            }

            //If data is not loaded, loop until it does
            while (MainActivity.data == null) ;

            //Move to Display activity
            Intent intent = new Intent(MainActivity.this, ActivityDisplay.class);
            startActivity(intent);

            goButton = findViewById(R.id.Go_button);
            goButton.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "data size = " + data.size(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this, ActivityDisplay.class);
                startActivity(intent1);
            });

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}