package com.example.yangchunghsuan.demo_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class home2 extends PageView{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    HomeAdapter homeAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    DatabaseReference databaseRefName;
    DatabaseReference databaseRefAddress;
    List<HomeInfo> items = new ArrayList<>();
    public static Bitmap[] bitmaps = new Bitmap[100];
    public static String[] store_name = new String[100];
    public static String[] address = new String[100];
    int name_length = 0;
    int loc_length = 0;
    int i=0;
    String get;
    int length = 0;
    DatabaseReference databaseReffolder;

    final long ONE_MEGABYTE = 1024 * 1024 * 10;
    public home2(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.home_2,null);
        recyclerView = view.findViewById(R.id.recyclerview2);

        databaseReference = database.getReference("userpage/0/length");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("length2",dataSnapshot.getValue().toString());
                get =  dataSnapshot.getValue().toString();
                if (get!=null){
                    length = Integer.parseInt(get);
                    for (i= 1;i<=length;i++){
                        databaseReffolder = database.getReference("userpage/" + i + "/info/picture");
                        databaseReffolder.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                storageRef = storage.getReference(dataSnapshot.getValue().toString());

                                storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        String[] split = dataSnapshot.getRef().toString().split("/");
                                        bitmaps[Integer.parseInt(split[4])-1] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseRefName = database.getReference("userpage/" + i + "/info/name");
                        databaseRefName.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e("get value!!!",dataSnapshot.getValue().toString());
                                get = dataSnapshot.getValue().toString();
                                store_name[name_length++] = get;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        databaseRefAddress = database.getReference("userpage/" + i + "/info/address");
                        databaseRefAddress.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e("get value",dataSnapshot.getValue().toString());
                                get = dataSnapshot.getValue().toString();
                                address[loc_length++] = get;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        items.add(new HomeInfo("test","testtsts",null));
        if (address[0]!=null){
            Log.e("in","!!!!");
            for (int i =0;address[i]!=null;i++){
                items.add(new HomeInfo(store_name[i],address[i],bitmaps[i]));
            }
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
