package com.example.yangchunghsuan.demo_project;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class getRestaurant extends AsyncTask<Void,Void,Void>{


    String[] name;
    String[] placeId;
    String[] rating;
    String[] address;

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

            Log.e("get",sb.toString());

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray results = jsonObject.getJSONArray("results");
//            JSONObject g = new JSONObject(results.get(0).toString());
//            Log.e("g",g.get("name").toString());

            name = new String[results.length()];
            rating = new String[results.length()];
            address = new String[results.length()];


            for (int i=0;i<results.length();i++){

                JSONObject content = new JSONObject(results.get(i).toString());
                System.out.println(content.get("name").toString());
                name[i] = content.get("name").toString();
                rating[i] = content.get("rating").toString();
                address[i] = content.get("vicinity").toString();
//                Log.e("jsonArray",results.get(i).toString());
            }




            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }
}
