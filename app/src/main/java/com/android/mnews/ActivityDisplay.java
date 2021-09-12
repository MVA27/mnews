package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.android.mnews.adapters.AdapterDisplay;

public class ActivityDisplay extends AppCompatActivity {

    ListView activityDisplayLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        try {
            activityDisplayLView = findViewById(R.id.activity_display_list_view_ID);
            AdapterDisplay adapterDisplay = new AdapterDisplay(this);
            adapterDisplay.notifyDataSetChanged();
            activityDisplayLView.setAdapter(adapterDisplay);
        }
        catch (Exception e){
            //Toast.makeText(this, "in Activity class "+e, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}