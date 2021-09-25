package com.android.mnews.administrator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.mnews.ActivityDisplay;
import com.android.mnews.ActivityError;
import com.android.mnews.MainActivity;
import com.android.mnews.constants.Errors;
import com.android.mnews.mediastack.Data;
import com.android.mnews.persistence.PreviousData;
import com.android.mnews.persistence.Timer;
import com.android.mnews.threads.ThreadDataLoader;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.net.ssl.HttpsURLConnection;
import org.jsoup.nodes.Document;
import org.jsoup.*;
import org.jsoup.nodes.Element;


public class ThreadParseHTML extends Thread{

    Context context;
    public static Holder holder = null;

    public ThreadParseHTML(Context context){
        this.context = context;
    }

    public void process() {

        try{
            //Establish Connection
            String rawURL = "https://projectnandriod.blogspot.com/2021/09/adminflags.html";
            URL url = new URL(rawURL);
            HttpsURLConnection con =  (HttpsURLConnection) url.openConnection();

            //Get InputStream and read using BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            //Create StringBuilder to save data
            StringBuilder html = new StringBuilder(10_000);

            //Read HTML data line by line and save it into StringBuilder object
            String line = br.readLine();
            while(line != null){
                html.append(line);
                line = br.readLine();
            }

            //Extract JSON data from HTML
            Document doc = Jsoup.parse(new String(html));
            Element element = doc.getElementById("adminFlags");
            String json = element.text();

            //Convert it into equivalent Holder object
            Gson gson = new Gson();
            holder = gson.fromJson(json,Holder.class);
        }
        catch(IOException e){
            Intent intent = new Intent(context, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.NO_INTERNET_CONNECTION);
            context.startActivity(intent);
        }
        catch(Exception e){
            Intent intent = new Intent(context, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.ERROR_IN_THREAD_PARSER);
            context.startActivity(intent);
        }
    }

    public void validate(){
        if(ThreadParseHTML.holder.getApiAccess() == 0){ //If Admin dose'nt allow access, Show Error Page
            Intent intent = new Intent(context, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.ACCESS_DENIED);
            context.startActivity(intent);
        }
        else {
            //Check after how long user is entering
            Timer timer = new Timer(context);
            int duration = timer.getDuration();

            //if the application starts for first time(-1) or in the same hour after 10 minutes then make API call as well as save it
            if(duration == -1 || duration > 10){
                if (MainActivity.data == null) {
                    new ThreadDataLoader(ThreadParseHTML.holder.getKey(),context).start();
                }
            }

            //if the application is started after 10 minutes
            else{
                loadStoredJSON();
            }
        }
    }

    //Methods for JSON which is stored in shared pref
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

    public void loadStoredJSON(){
        String json = new PreviousData(context).getData();

        //If JSON object was found then convert it into java object
        if(!json.isEmpty()){

            //Convert Json to Holder Object
            Gson gson = new Gson();
            MainActivity.holder = gson.fromJson(json, com.android.mnews.mediastack.Holder.class);
            MainActivity.data = MainActivity.holder.getData();

            removeDuplicates();

            //Move to Display activity
            Intent intent = new Intent(context, ActivityDisplay.class);
            context.startActivity(intent);
        }

        //If json object it not found or any error occurs, show ActivityError
        else{
            Intent intent = new Intent(context, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.ERROR_IN_THREAD_PARSER);
            context.startActivity(intent);
        }
    }

    @Override
    public void run() {
        process();
        validate();
    }
}
