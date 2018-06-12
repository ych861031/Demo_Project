package com.example.yangchunghsuan.demo_project;

import android.os.AsyncTask;
import android.util.Log;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class getRestaurant extends AsyncTask<Void,Void,Void>{


    @Override
    protected Void doInBackground(Void... voids) {

        StringBuilder sb = new StringBuilder();
        try{
            String api =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=24.1803,120.6459&radius=1000&types=restaurant&key=AIzaSyAYbRCcmoalWbHSeqWtFbIlUzuAWbUHtWY";
            URL url = new URL(api);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = bufferedReader.readLine();
            while (line!=null){
                Log.e("HTTP",line);
                sb.append(line);
                line = bufferedReader.readLine();
            }

            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }
}
