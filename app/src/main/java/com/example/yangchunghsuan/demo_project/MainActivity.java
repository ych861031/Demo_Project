package com.example.yangchunghsuan.demo_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener
,UploadFragment.OnFragmentInteractionListener,NearbyFragment.OnFragmentInteractionListener,PersonalFragment.OnFragmentInteractionListener,
LoginFragment.OnFragmentInteractionListener{

    public static String[] userName = new String[100];
    public static String[] email = new String[100];
    public static boolean login = false;
    public static String[] nearby_storeName = new String[100];
    public static String[] rating = new String[100];
    public static String[] address = new String[100];



    SearchView searchView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();
                    searchView.setVisibility(View.GONE);
                    toolbar.setTitle("官方推薦");
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,SearchFragment.newInstance()).commitAllowingStateLoss();
                    searchView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_upload:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,UploadFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Upload");
                    searchView.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_nearby:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,NearbyFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Nearby");
                    searchView.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_personal:
                    if (!login){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,LoginFragment.newInstance()).commitAllowingStateLoss();
                        setTitle("Login");
                    }else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,PersonalFragment.newInstance()).commitAllowingStateLoss();
                        setTitle("Personal");
                    }
                    searchView.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    public static BottomNavigationView navigation ;
    public static Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("!!!","homeOnCreate");
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();
        searchView = findViewById(R.id.searchView);

        if (ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,NearbyFragment.newInstance()).commitAllowingStateLoss();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},1000);
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.frame,SearchFragment.newInstance()).commitAllowingStateLoss();
        setTitle("Login");
        navigation.setSelectedItemId(R.id.navigation_personal);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,LoginFragment.newInstance()).commitAllowingStateLoss();

        //get nearby api
//        getRestaurant get = new getRestaurant();
//        get.execute();
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1000:
                if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,NearbyFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("Login");
                    navigation.setSelectedItemId(R.id.navigation_personal);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,LoginFragment.newInstance()).commitAllowingStateLoss();
                }
                break;
            default:
                Toast.makeText(this,"未取得權限Nearby無法使用",Toast.LENGTH_SHORT);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null){
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 111:
                Log.e("activity result","result ok!!!!!!!!!");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,LoginFragment.newInstance()).commitAllowingStateLoss();
                navigation.setSelectedItemId(R.id.navigation_personal);
                break;
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
