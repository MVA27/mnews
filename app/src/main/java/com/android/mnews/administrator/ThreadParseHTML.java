package com.android.mnews.administrator;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.nodes.Document;
import org.jsoup.*;
import org.jsoup.nodes.Element;


public class ThreadParseHTML extends Thread{

    public static Holder holder = null;

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
        catch(Exception e){

        }

    }

    @Override
    public void run() {

        process();

    }
}
