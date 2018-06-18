package com.example.yangchunghsuan.demo_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    String location = NearbyFragment.location_get;
    @Override
    protected Void doInBackground(Void... voids) {

        StringBuilder sb = new StringBuilder();
        try{
            String api =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+location+"&radius=1000&types=restaurant&key=AIzaSyAYbRCcmoalWbHSeqWtFbIlUzuAWbUHtWY";
            URL url = new URL(api);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = bufferedReader.readLine();
            while (line!=null){
//                Log.e("HTTP",line);
                sb.append(line);
                line = bufferedReader.readLine();
            }

//            Log.e("get",sb.toString());

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray results = jsonObject.getJSONArray("results");

            name = new String[results.length()];
            rating = new String[results.length()];
            address = new String[results.length()];


            for (int i=0;i<results.length();i++){

                JSONObject content = new JSONObject(results.get(i).toString());
                System.out.println(content.get("name").toString());
                try{
                    name[i] = content.get("name").toString();
                    MainActivity.nearby_storeName[i] =  content.get("name").toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    rating[i] = content.get("rating").toString();
                    MainActivity.rating[i] = "評分： "+content.get("rating").toString();
                }catch (Exception e){
                    MainActivity.rating[i] = "評分： 無資料";
                    e.printStackTrace();
                }
                try{
                    jsonObject = new JSONObject(content.get("geometry").toString());
                    jsonObject = new JSONObject(jsonObject.get("location").toString());
                    String lat = jsonObject.get("lat").toString();
                    String lng = jsonObject.get("lng").toString();
                    MainActivity.address[i] = lat.substring(0,lat.length()-3)+","+lng.substring(0,lng.length()-3);
                }catch (Exception e){
                    e.printStackTrace();
                }



//                Log.e("jsonArray",results.get(i).toString());
            }



            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }
}
