package com.android.mnews.persistence;

import android.content.Context;
import android.content.SharedPreferences;

public class PreviousData {

    private Context context;
    private final String PREVIOUS_DATA = "PREVIOUS_DATA";

    public PreviousData(Context context) {
        this.context = context;
    }

    public void saveData(String json){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREVIOUS_DATA,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREVIOUS_DATA,json);
        editor.apply();
    }

    public String getData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREVIOUS_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREVIOUS_DATA,"default"); //TODO : Put default values
    }
}
