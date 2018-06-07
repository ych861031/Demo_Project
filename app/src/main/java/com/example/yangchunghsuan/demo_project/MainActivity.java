package com.example.yangchunghsuan.demo_project;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener
,UploadFragment.OnFragmentInteractionListener,NearbyFragment.OnFragmentInteractionListener,PersonalFragment.OnFragmentInteractionListener{


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
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,PersonalFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Personal");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
