package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.mnews.administrator.ThreadParseHTML;
import com.android.mnews.mediastack.Data;
import com.android.mnews.mediastack.Holder;
import com.android.mnews.persistence.Timer;
import com.android.mnews.threads.ThreadDataLoader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Holder holder;
    public static List<Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //From Administrator Package (Check if Admin has allowed Access)
        new ThreadParseHTML().start();
        while(ThreadParseHTML.holder == null);

        if(ThreadParseHTML.holder.getApiAccess() == 0){ //If Admin dose'nt allow access, Show Error Page
            Intent intent = new Intent(MainActivity.this,ActivityError.class);
            startActivity(intent);
        }
        else {  //If Admin allows access, Get Api access key from AdminFlags and make API call

            if (MainActivity.data == null) {
                new ThreadDataLoader(ThreadParseHTML.holder.getKey()).start();
            }
            //If data is not loaded, loop until it does
            while (MainActivity.data == null);

            //Move to Display activity
            Intent intent = new Intent(MainActivity.this, ActivityDisplay.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}