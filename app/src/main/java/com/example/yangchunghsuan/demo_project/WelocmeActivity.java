package com.example.yangchunghsuan.demo_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class WelocmeActivity extends Activity {


    public static String item_lenght;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welocme);
        ImageView imageView = findViewById(R.id.imageView_welcome);
        imageView.setImageResource(R.drawable.welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseRef =  database.getReference("homepage/0");

//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                storage.getReference();

                Intent intent = new Intent();
                intent.setClass(WelocmeActivity.this,MainActivity.class);
                startActivity(intent);
//                finish();
            }
        },2000);

    }

}
