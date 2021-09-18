package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.mnews.administrator.ThreadParseHTML;
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

        //From Administrator Package (Check if Admin has allowed Access)
        //Internally calls ThreadDataLoader which internally starts new activity when data loads
        new ThreadParseHTML(this).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}