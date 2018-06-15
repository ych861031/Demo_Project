package com.example.yangchunghsuan.demo_project;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class home1 extends PageView{

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<HomeInfo> items = new ArrayList<>();
    int loc_length = 0;
    int name_length = 0;
    int length = 0;
    int bitmap_length = 0;
    public static String a[] = new String[100];
    public static String[] address = new String[100];
    public static String[] store_name = new String[100];
    public static Bitmap[] bitmap = new Bitmap[100];
    String get;
    int i;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;
    DatabaseReference databaseRefName;
    DatabaseReference databaseRefAddress;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;
    StorageReference sr;


    final long ONE_MEGABYTE = 1024 * 1024 * 10;

    public home1(Context context) {
        super(context);

        View v = LayoutInflater.from(context).inflate(R.layout.home_1,null);
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        //取得homepage item個數
        databaseRef = database.getReference("homepage/0/length");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                get = dataSnapshot.getValue().toString();
                if ( get != null){
                    length = Integer.parseInt(get);
                    //取圖片
                    for (i=1;i<=length;i++){
                        storageRef = storage.getReference();
                        sr = storageRef.child("Home").child(String.valueOf(i)).child("1.jpg");
                        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Log.e("!!!!",bytes.toString());
                                bitmap[bitmap_length++] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            }
                        });
                    }


                    Log.e("length!!!!",String.valueOf(length));
                    for (i= 1;i<=length;i++){
                        databaseRefName = database.getReference("homepage/" + i + "/info/name");
                        databaseRefName.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e("get value",dataSnapshot.getValue().toString());
                                get = dataSnapshot.getValue().toString();
                                store_name[name_length++] = get;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        databaseRefAddress = database.getReference("homepage/" + i + "/info/address");
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

        if (address[0]!=null&&store_name[0]!=null&&bitmap[0]!=null){
            for (int i=0;address[i]!=null&&store_name[i]!=null&& bitmap[i]!=null;i++){
                items.add(new HomeInfo(store_name[i],address[i],bitmap[i]));
            }
        }

//        if (length!=0){
//            Log.e("!!!","!!!!");
//            for (int i=1;i<=length;i++){
//                storageRef = storage.getReference("Home/"+i+"/1.jpg");
//                storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                    @Override
//                    public void onSuccess(byte[] bytes) {
//                        Log.e("!!!",String.valueOf(bitmap_length));
//                        bitmap[bitmap_length++] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                    }
//                });
//            }
//        }



//        items.add(new HomeInfo(store_name[0],address[0],HomeFragment.bitmap[0]));
//        items.add(new HomeInfo(store_name[1],address[1],HomeFragment.bitmap[1]));
//        items.add(new HomeInfo(store_name[2],address[2],HomeFragment.bitmap[2]));
//        items.add(new HomeInfo(store_name[3],address[3],HomeFragment.bitmap[3]));
//        items.add(new HomeInfo(store_name[4],address[4],HomeFragment.bitmap[4]));
//        items.add(new HomeInfo(store_name[5],address[5],HomeFragment.bitmap[5]));





        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(items,v.getContext()){
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(10);

        recyclerView.addItemDecoration(spacesItemDecoration);
        recyclerView.setAdapter(homeAdapter);
        addView(v);

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        private int space;

        public SpacesItemDecoration(int space){
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = space;
        }
    }



}