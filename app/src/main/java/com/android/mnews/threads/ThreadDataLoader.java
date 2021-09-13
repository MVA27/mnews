package com.android.mnews.threads;

import com.android.mnews.MainActivity;
import com.android.mnews.mediastack.Data;
import com.android.mnews.mediastack.Holder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

public class ThreadDataLoader extends Thread {

    String accessKey;

    public ThreadDataLoader(String accessKey){
        this.accessKey = accessKey;
    }


    //connect to the server and load data into buffer
    public void loadData() {

        String output = "";
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
            output = br.readLine();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Convert Json to Holder Object
        Gson gson = new Gson();
        MainActivity.holder = gson.fromJson(output, Holder.class);
    }

    @Override
    public void run() {
        loadData();
        //Data loaded into list
        MainActivity.data = MainActivity.holder.getData();
    }
}
