package com.android.mnews.threads;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.mnews.ActivityDisplay;
import com.android.mnews.ActivityError;
import com.android.mnews.MainActivity;
import com.android.mnews.constants.Errors;
import com.android.mnews.mediastack.Data;
import com.android.mnews.mediastack.Holder;
import com.android.mnews.persistence.PreviousData;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class ThreadDataLoader extends Thread {

    String accessKey;
    Context context;

    //Key is taken from blogger website from admin thread, stored in Holder object and passed to this Thread
    //Context is required to persist JSON data in sharedpreferences
    public ThreadDataLoader(String accessKey,Context context){
        this.accessKey = accessKey;
        this.context = context;
    }

    //connect to the server and load data into buffer
    public void loadData() {

        String json = "";
        try {
            String date = "date="+new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String languages = "languages=en,-ar,-ru";
            String limit = "limit=100";
            String countries = "countries=-ar,-au,-at,-be,-br,-bg,-cn,-co,-cz,-eg,-fr,-de,-gr,-hk,-hu,-id,-ie,-il,-it,-jp,-lv,-lt,-my,-mx,-ma,-nl,-nz,-ng,-no,-ph,-pl,-pt,-ro,-sa,-rs,-sg,-sk,-si,-za,-kr,-se,-ch,-tw,-th,-tr,-ae,-ua,-ve";
            String sources = "sources=bostonherald,irishtimes,nesn,wtvq,globaltoronto,pedestrian,europeanvoice,whittierdailynews,euroweeklynews,ocregister,en,ESPN,Mail,Post,globalwinnipeg,Sportskeeda,dnaindia,sg,tribune,dailynews,dailybreeze,CNN";

            //Connect to server
            String rawURL = "http://api.mediastack.com/v1/news?access_key="+accessKey+"&"+date+"&"+limit+"&"+languages+"&"+countries+"&"+sources;
            URL url = new URL(rawURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

            //Read the JSON data into String
            InputStream is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            json = br.readLine();
        }
        catch(IOException e){
            Intent intent = new Intent(context, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.NO_INTERNET_CONNECTION);
            context.startActivity(intent);
        }
        catch(Exception e){
            Intent intent = new Intent(context, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.ERROR_IN_THREAD_DATA_LOADER);
            context.startActivity(intent);
        }

        //Convert Json to Holder Object
        Gson gson = new Gson();
        MainActivity.holder = gson.fromJson(json, Holder.class);


        //Save the JSON data into permanent file
        PreviousData previousData = new PreviousData(context);
        previousData.saveData(json);
    }

    public void removeDuplicates(){
        //comparator only to filter out common data based on Title
        Comparator<Data> comparatorOfData = (o1, o2) -> {
            if(o1 != null && o2 != null){
                if(o1.getTitle().equals(o2.getTitle())){
                    return 0;
                }
            }
            return 1;
        };
        Set<Data> set = new TreeSet<>(comparatorOfData);
        set.addAll(MainActivity.data);
        MainActivity.data = new ArrayList<>(set);
    }

    public void gotoActivityDisplay(){
        Intent intent = new Intent(context, ActivityDisplay.class);
        context.startActivity(intent);
    }

    @Override
    public void run(){
        loadData();

        //Data loaded into list
        MainActivity.data = MainActivity.holder.getData();

        removeDuplicates();
        gotoActivityDisplay();
    }
}
