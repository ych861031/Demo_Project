package com.example.yangchunghsuan.demo_project;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NearbyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NearbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearbyFragment extends Fragment {
    public static NearbyFragment newInstance() {

        Bundle args = new Bundle();

        NearbyFragment fragment = new NearbyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NearbyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearbyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearbyFragment newInstance(String param1, String param2) {
        NearbyFragment fragment = new NearbyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<HashMap<String,String>> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    double lat;
    double lon;
    View view;
    TextView latText;
    TextView lonText;
    public static String location_get;


    //主要處理區塊
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //取得目前這個view的內容
        view = inflater.inflate(R.layout.fragment_nearby, container, false);
        if (MainActivity.nearby_storeName[0]!=null){
            for (int i=0;MainActivity.nearby_storeName[i]!=null;i++){
                HashMap<String,String> item = new HashMap<>();
                item.put("name",MainActivity.nearby_storeName[i]);
                item.put("rating",MainActivity.rating[i]);
                list.add(item);
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(view.getContext(),list,R.layout.nearby_row,new String[]{"name","rating"},new int[]{R.id.naerby_name,R.id.nearby_rating});
        final ListView listView = view.findViewById(R.id.nearby_list);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(view.getContext(),MainActivity.address[position],Toast.LENGTH_SHORT).show();
//                Uri uri = Uri.parse("geo:"+MainActivity.address[position]+"?z=19");
                Uri uri = Uri.parse("google.navigation:q="+MainActivity.address[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

            }
        });

        //從這個view找button
        LocationManager mgr = (LocationManager) view.getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(view.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)view.getContext(),new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},1000);
        }

        boolean gps = mgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps){
            Toast.makeText(view.getContext(),"GPS定位模式",Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(view.getContext(),"網路定位模式",Toast.LENGTH_SHORT).show();
        }
        mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                lat = location.getLatitude();
                lon = location.getLongitude();
                location_get = String.valueOf(lat).substring(0,String.valueOf(lat).length()-3)+","+String.valueOf(lon).substring(0,String.valueOf(lon).length()-3);
                Log.e("get",location_get);
                getRestaurant get = new getRestaurant();
                try{
                    get.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

//                update();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                location_get = String.valueOf(lat).substring(0,String.valueOf(lat).length()-3)+","+String.valueOf(lon).substring(0,String.valueOf(lon).length()-3);
                getRestaurant get = new getRestaurant();
                get.execute();
                try{
                    get.execute();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        //回傳這個view讓MainActivity更改fragment
        return view;
    }

    public void update(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame,NearbyFragment.newInstance()).commitAllowingStateLoss();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
