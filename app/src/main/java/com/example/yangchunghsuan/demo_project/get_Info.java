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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef  = database.getReference("user");
    DatabaseReference databaseRef_length = database.getReference("user/0/length");
    JSONObject jsonObject ;
    String[] userName = new String[100];
    String[] email = new String[100];
    ListView listView;
    ArrayList<HashMap<String,String>> list = new ArrayList<>();
    SimpleAdapter adapter;
    int userName_length = 0;
    int length = 0;
    int email_length = 0;
    HashMap<String,String> item = new HashMap<>();




    public void getInfo(View view){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                databaseRef_length.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue()!=null){
                            try{
                                length = Integer.parseInt(dataSnapshot.getValue().toString());
                                Log.e("legth",String.valueOf(length));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.e("users",dataSnapshot.getValue().toString());
                        try {
                            jsonObject = new JSONObject(dataSnapshot.getValue().toString());
                            userName[userName_length++]  = jsonObject.get("userName").toString();
                            email[email_length++] = jsonObject.get("email").toString();
                            Log.e(userName[userName_length-1],email[email_length-1]);
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





        item.put("userName","ych861031");
        item.put("email","ych861031@gmail.com");
        list.add(item);
        adapter = new SimpleAdapter(view.getContext(),list,R.layout.search_user_row,new String[]{"userName","email"},new int[]{R.id.textView_userName,R.id.textView_email});
        listView = view.findViewById(R.id.user_list);
        listView.setAdapter(adapter);

    }
}
