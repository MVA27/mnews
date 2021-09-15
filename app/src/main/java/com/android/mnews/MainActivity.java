package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.mnews.administrator.ThreadParseHTML;
import com.android.mnews.mediastack.Data;
import com.android.mnews.mediastack.Holder;
import com.android.mnews.persistence.PreviousData;
import com.android.mnews.persistence.Timer;
import com.android.mnews.threads.ThreadDataLoader;
import com.google.gson.Gson;

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
        //If Admin allows access, Get Api access key from AdminFlags and make API call
        else {
            //Check after how long user is entering
            Timer timer = new Timer(this);
            int duration = timer.getDuration();

            //if the application starts for first time(-1) or in the same hour after 10 minutes then make API call as well as save it
            if(duration == -1 || duration > 10){

                if (MainActivity.data == null) {
                    new ThreadDataLoader(ThreadParseHTML.holder.getKey(),this).start();
                }

                //If data is not loaded, loop/hold until it does
                while (MainActivity.data == null);

                //Move to Display activity
                Intent intent = new Intent(MainActivity.this, ActivityDisplay.class);
                intent.putExtra("test"," loading new data , time = "+duration);
                startActivity(intent);
            }

            //if the application is started after 10 minutes
            else{
                String json = new PreviousData(this).getData();

                //If JSON object was found then convert it into java object
                if(!json.isEmpty()){

                    //Convert Json to Holder Object
                    Gson gson = new Gson();

                    MainActivity.holder = gson.fromJson(json, Holder.class);
                    MainActivity.data = MainActivity.holder.getData();

                    //Move to Display activity
                    Intent intent = new Intent(MainActivity.this, ActivityDisplay.class);
                    intent.putExtra("test","loading existing data , time = "+duration);
                    startActivity(intent);
                }

                //If json object it not found or any error occurs, show ActivityError
                else{
                    Intent intent = new Intent(MainActivity.this, ActivityError.class);
                    startActivity(intent);
                }
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}