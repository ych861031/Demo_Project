package com.example.yangchunghsuan.demo_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener
,UploadFragment.OnFragmentInteractionListener,NearbyFragment.OnFragmentInteractionListener,PersonalFragment.OnFragmentInteractionListener,
LoginFragment.OnFragmentInteractionListener{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Home");
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,SearchFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Search");
                    return true;
                case R.id.navigation_upload:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,UploadFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Upload");
                    return true;
                case R.id.navigation_nearby:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,NearbyFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Nearby");
                    return true;
                case R.id.navigation_personal:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,PersonalFragment.newInstance()).commitAllowingStateLoss();
//                    setTitle("Personal");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,LoginFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Login");
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("!!!","homeOnCreate");
        //get nearby api
//        getRestaurant get = new getRestaurant();
//        get.execute();
    }



    @Override
    protected void onStart() {
        super.onStart();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();

    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        setTitle("Home");
//        navigation.setSelectedItemId(R.id.navigation_home);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();
        setTitle("Search");
        navigation.setSelectedItemId(R.id.navigation_search);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,SearchFragment.newInstance()).commitAllowingStateLoss();

    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
