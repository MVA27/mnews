package com.android.mnews.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Timer {

    private SharedPreferences sharedPreferences;
    private final String HOURS = "HOURS";
    private final String MINUTES = "MINUTES";

    public Timer(Context context){
        String TIMER_FILE = "TIMER_FILE";
        sharedPreferences = context.getSharedPreferences(TIMER_FILE,Context.MODE_PRIVATE);
    }

    public void saveCurrentEnteringTime(){
        String hrs = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
        String min = new SimpleDateFormat("mm",Locale.getDefault()).format(new Date());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HOURS,hrs);
        editor.putString(MINUTES,min);
        editor.apply();
    }

    public String[] getPreviouslyEnteredTime(){
        String hrs = sharedPreferences.getString(HOURS,"0");
        String min = sharedPreferences.getString(MINUTES,"0");
        return new String[]{hrs,min};
    }

    /**
     *  NOTE : This method should be called only once
     *  If this method returns -1 : Fetch data from the API server
     *  Else : load the saved data based of the differenceInMinutes value
     */
    public int getDuration(){
		
        //Get The time user entered last time
        String[] s = getPreviouslyEnteredTime();
        String oldHrs = s[0];	//If app opens for the first time getPreviouslyEnteredTime() returns "0"
        String oldMin = s[1];	//If app opens for the first time getPreviouslyEnteredTime() returns "0"

        //Calculate Difference
        String currentHrs = new SimpleDateFormat("HH",Locale.getDefault()).format(new Date());
        String currentMin = new SimpleDateFormat("mm",Locale.getDefault()).format(new Date());

        //Calculate if the user is entering again for same hour
        int differenceInHours = Integer.parseInt(oldHrs) - Integer.parseInt(currentHrs);
        int differenceInMinutes = -1;

        if(Math.abs(differenceInHours) == 0){
            //If the hour same, calculate after how many minutes he has entered
            differenceInMinutes =  Math.abs(Integer.parseInt(oldMin) - Integer.parseInt(currentMin));
        }

        //Save Current Time
        saveCurrentEnteringTime();

        return differenceInMinutes;
    }

}
