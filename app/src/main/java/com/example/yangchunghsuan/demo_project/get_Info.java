package com.example.yangchunghsuan.demo_project;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class get_Info {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseRef  = database.getReference("user");
    private JSONObject jsonObject ;
    private ArrayList<HashMap<String,String>> list = new ArrayList<>();
    private int length = 0;

    public void getInfo(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                databaseRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.e("users",dataSnapshot.getValue().toString());
                        try {
                            jsonObject = new JSONObject(dataSnapshot.getValue().toString());
                            MainActivity.userName[length]  = jsonObject.get("userName").toString();
                            MainActivity.email[length] = jsonObject.get("email").toString();
                            length++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        thread.start();
        thread.interrupt();


        if (MainActivity.userName[0]!=null){
            for (int i=0;MainActivity.userName[i]!=null;i++){
                System.out.println(MainActivity.userName[i]);
                HashMap<String,String> item = new HashMap<>();
                item.put("userName",MainActivity.userName[i]);
                item.put("email",MainActivity.email[i]);
                list.add(item);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), list, R.layout.search_user_row, new String[]{"userName", "email"}, new int[]{R.id.textView_userName, R.id.textView_email});
        ListView listView = view.findViewById(R.id.user_list);
        listView.setAdapter(adapter);

    }
}
