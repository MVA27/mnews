package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.android.mnews.administrator.ThreadParseHTML;
import com.android.mnews.constants.Errors;
import com.android.mnews.mediastack.Data;
import com.android.mnews.mediastack.Holder;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Holder holder;
    public static List<Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //From Administrator Package(Check if Admin has allowed Access)
        //Internally calls ThreadDataLoader which internally starts new activity when data loads
        try {
            new ThreadParseHTML(this).start();
        }
        catch(Exception e){
            Intent intent = new Intent(this, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.ERROR_IN_ACTIVITY_MAIN);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}