package com.example.yangchunghsuan.demo_project;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class home1 extends PageView{

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<HomeInfo> items = new ArrayList<>();
    int j=0;
    int loc_length = 0;
    int name_length = 0;
    int length = 0;
    public static String a[] = new String[100];
    public static String[] address = new String[100];
    public static String[] store_name = new String[100];

    String get;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;
    public home1(Context context) {
        super(context);
        View v = LayoutInflater.from(context).inflate(R.layout.home_1,null);
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        //取得homepage item個數
        databaseRef = database.getReference("homepage/0");
        databaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                get = dataSnapshot.getValue().toString();
                if ( get != null){
//                    Log.e("length!!!!!",String.valueOf(get));
                    length = Integer.parseInt(get);
                    Log.e("length!!!!",String.valueOf(length));

                    for (int i=1;i<=length;i++) {
                        databaseRef = database.getReference("homepage/" + i + "/info");
                        databaseRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                get = dataSnapshot.getValue().toString();
                                if (get!=null){
                                    if (j%2==0){
                                        address[loc_length++]=get;
                                        Log.e(String.valueOf(loc_length-1),address[loc_length-1]);

                                    }else{
                                        store_name[name_length++] = get;
                                        Log.e(String.valueOf(name_length-1),store_name[name_length-1]);
                                    }
                                    j++;
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


        items.add(new HomeInfo(store_name[0],address[0],HomeFragment.bitmap[0]));
        items.add(new HomeInfo(store_name[1],address[1],HomeFragment.bitmap[1]));
        items.add(new HomeInfo(store_name[2],address[2],HomeFragment.bitmap[2]));
        items.add(new HomeInfo(store_name[3],address[3],HomeFragment.bitmap[3]));
        items.add(new HomeInfo(store_name[4],address[4],HomeFragment.bitmap[4]));
        items.add(new HomeInfo(store_name[5],address[5],HomeFragment.bitmap[5]));


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