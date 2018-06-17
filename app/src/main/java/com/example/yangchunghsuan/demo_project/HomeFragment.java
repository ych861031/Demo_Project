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



    private void initView(View view){
        viewPager = view.findViewById(R.id.pager);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                viewPager.setAdapter(new ViewPagerAdapter());
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.e("position",String.valueOf(position));
                        if (position==0){
                            MainActivity.toolbar.setTitle("官方推薦");
                        }else{
                            MainActivity.toolbar.setTitle("使用者推薦");
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
        thread.start();
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
