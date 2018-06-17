package com.example.yangchunghsuan.demo_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class home2 extends PageView{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    HomeAdapter homeAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    List<HomeInfo> items = new ArrayList<>();
    public static Bitmap[] bitmaps = new Bitmap[100];
    int i=0;

    final long ONE_MEGABYTE = 1024 * 1024 * 10;
    public home2(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.home_2,null);
        recyclerView = view.findViewById(R.id.recyclerview2);

        storageRef = storage.getReference("Users/Jqob0F9hEAXHYfIuc3CR8FeXQvU2/1.jpg");
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmaps[i] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            }
        });

//        items.add(new HomeInfo("test","testtsts",null));
        if (bitmaps[0]!=null){
            items.add(new HomeInfo("test","testtststst",bitmaps[0]));
        }


        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(items,view.getContext()){
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };

        recyclerView.setAdapter(homeAdapter);
        addView(view);
    }


}
