package com.example.yangchunghsuan.demo_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Log.e("!!!","newInstance");
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    StorageReference storageRef;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    int i;
    int bitmap_length;
    public static Bitmap[] bitmap = new Bitmap[100];
    StorageReference sr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        storageRef = storage.getReference();
        final long ONE_MEGABYTE = 1024 * 1024 * 5;

//        for (i=1;i<=7;i++){
//            storageRef = storage.getReference();
//            sr = storageRef.child("Home").child(String.valueOf(i)).child("1.jpg");
//            sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                    Log.e("!!!!",bytes.toString());
//                    bitmap[bitmap_length++] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                }
//            });
//        }


//        StorageReference sr = storageRef.child("Home").child("1").child("1.jpg");
//        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Log.e("!!!",bytes.toString());
//                bitmap[0] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            }
//        });
//
//        sr = storageRef.child("Home").child("2").child("1.jpg");
//        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Log.e("!!!",bytes.toString());
//                bitmap[1] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            }
//        });
//
//        sr = storageRef.child("Home").child("3").child("1.jpg");
//        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Log.e("!!!",bytes.toString());
//                bitmap[2] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            }
//        });
//        sr = storageRef.child("Home").child("4").child("1.jpg");
//        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Log.e("!!!",bytes.toString());
//                bitmap[3] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            }
//        });
//        sr = storageRef.child("Home").child("5").child("1.jpg");
//        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Log.e("!!!",bytes.toString());
//                bitmap[4] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            }
//        });
//        sr = storageRef.child("Home").child("6").child("1.jpg");
//        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Log.e("!!!",bytes.toString());
//                bitmap[5] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            }
//        });



        Log.e("!!!","OnCreate");
    }




    private ViewPager viewPager;
    private List<PageView> pageList;
//    View view;

    //主要操作區
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("!!!","createView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initData(view);
        initView(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

//    private RecyclerView recyclerView;
//    private HomeAdapter homeAdapter;
//    private RecyclerView.LayoutManager layoutManager;
//
//    private List<HomeInfo> items;
//
//    public class home1 extends PageView{
//
//        public home1(Context context) {
//            super(context);
//            View v = LayoutInflater.from(context).inflate(R.layout.home_1,null);
//
//
//            recyclerView = v.findViewById(R.id.recyclerview);
//            recyclerView.setHasFixedSize(true);
//
//            items = new ArrayList<>();
//            items.add(new HomeInfo("1",bitmap[0]));
//            items.add(new HomeInfo("2",bitmap[1]));
//            items.add(new HomeInfo("3",bitmap[2]));
//            items.add(new HomeInfo("4",bitmap[3]));
//            items.add(new HomeInfo("5",bitmap[4]));
//
//
//
//            layoutManager = new LinearLayoutManager(v.getContext());
//            recyclerView.setLayoutManager(layoutManager);
//
//            homeAdapter = new HomeAdapter(items,v.getContext()){
//                @Override
//                public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//                    super.onBindViewHolder(holder, position);
////                    HomeInfo homeInfo = new HomeInfo(items,bitmap[position-1]);
//
//                }
//            };
//            SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(10);
//
//            recyclerView.addItemDecoration(spacesItemDecoration);
//            recyclerView.setAdapter(homeAdapter);
//            addView(v);
//
//        }
//    }

//    public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
//        private int space;
//
//        public SpacesItemDecoration(int space){
//            this.space = space;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.top = space;
//        }
//    }

    public class home2 extends PageView{

        public home2(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.home_2,null);
            addView(view);
        }
    }

    private void initView(View view){
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter());
    }

    private void initData(View view){
        pageList = new ArrayList<>();
        pageList.add(new home1(view.getContext()));
        pageList.add(new home2(view.getContext()));
    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return object==view;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }



}
