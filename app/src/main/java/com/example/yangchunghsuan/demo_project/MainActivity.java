package com.example.yangchunghsuan.demo_project;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener
,UploadFragment.OnFragmentInteractionListener,NearbyFragment.OnFragmentInteractionListener,PersonalFragment.OnFragmentInteractionListener,
LoginFragment.OnFragmentInteractionListener{

    public static String[] userName = new String[100];
    public static String[] email = new String[100];
    public static boolean login = false;
    public static String[] nearby_storeName = new String[100];
    public static String[] rating = new String[100];
    public static String[] address = new String[100];




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,HomeFragment.newInstance()).commitAllowingStateLoss();
                    toolbar.setTitle("官方推薦");
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,SearchFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("使用者列表");
                    return true;
                case R.id.navigation_upload:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,UploadFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("照片上傳");
                    return true;
                case R.id.navigation_nearby:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,NearbyFragment.newInstance()).commitAllowingStateLoss();
                    setTitle("附近餐廳");
                    return true;
                case R.id.navigation_personal:
                    if (!login){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,LoginFragment.newInstance()).commitAllowingStateLoss();
                        setTitle("登入");
                    }else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,PersonalFragment.newInstance()).commitAllowingStateLoss();
                        setTitle("個人頁面");
                    }
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


        if (ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,NearbyFragment.newInstance()).commitAllowingStateLoss();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},1000);
        }
        if (ActivityCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED||
                ActivityCompat.checkSelfPermission(this,CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,CAMERA},1000);
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.frame,SearchFragment.newInstance()).commitAllowingStateLoss();
        setTitle("Login");
        navigation.setSelectedItemId(R.id.navigation_personal);


    }

    public void update(){
        navigation.setSelectedItemId(R.id.navigation_personal);
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
//        getMenuInflater().inflate(R.menu.menu_search_view, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//
//        return super.onCreateOptionsMenu(menu);
//    }
}
