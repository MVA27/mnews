package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.android.mnews.adapters.AdapterDisplay;
import com.android.mnews.constants.Errors;

public class ActivityDisplay extends AppCompatActivity {

    ListView activityDisplayLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //TODO : Remove
        Log.d("MEHUL","Activity Display stated");

        Toast.makeText(this, ""+getIntent().getStringExtra("test"), Toast.LENGTH_SHORT).show(); //TODO: Remove

        try {
            activityDisplayLView = findViewById(R.id.activity_display_list_view_ID);
            AdapterDisplay adapterDisplay = new AdapterDisplay(this);
            adapterDisplay.notifyDataSetChanged();
            activityDisplayLView.setAdapter(adapterDisplay);
        }
        catch (Exception e){
            Intent intent = new Intent(this, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.ERROR_IN_ACTIVITY_DISPLAY);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO : Remove
        Log.d("MEHUL","Thread Activity Display Destroyed");
    }
}