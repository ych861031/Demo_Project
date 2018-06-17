package com.example.yangchunghsuan.demo_project;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.Transaction;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    public static UploadFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UploadFragment fragment = new UploadFragment();
        fragment.setArguments(args);
        return fragment;
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //用來判斷回傳值其代表動作是什麼
    public final static int CAMERA = 66;
    public final static int PHOTO = 99;
    ImageView img;
    DisplayMetrics mPhone;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadFragment newInstance(String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //取得目前這個view的內容
        view = inflater.inflate(R.layout.fragment_upload, container, false);
        //從這個view找button.img
        Button button = view.findViewById(R.id.bt1);
        Button uploadBtn = view.findViewById(R.id.bt2);
        img = view.findViewById(R.id.img);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(view.getContext(),"Upload Page test",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PHOTO);
            }
        });


        img.setImageBitmap(bitmap_get);



        //回傳這個view讓MainActivity更改fragment
        return view;
    }

    public static Bitmap bitmap_get;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,UploadFragment.newInstance()).commitAllowingStateLoss();

        if((requestCode ==CAMERA || requestCode ==PHOTO)&&data!=null){
            Uri uri = data.getData();
            ContentResolver cr = getContext().getContentResolver();
            Toast.makeText(getView().getContext(),"1",Toast.LENGTH_SHORT).show();

            try{
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                bitmap_get = bitmap;
                Log.e("bitmap",bitmap.toString());
                Toast.makeText(getView().getContext(),"1.5",Toast.LENGTH_SHORT).show();
                ScalePic(bitmap,mPhone.heightPixels);

                if(bitmap.getWidth()>bitmap.getHeight()){
                    ScalePic(bitmap,mPhone.heightPixels);
                    Toast.makeText(getView().getContext(),"2",Toast.LENGTH_SHORT).show();

                }else{
                    ScalePic(bitmap,mPhone.widthPixels);
                    Toast.makeText(getView().getContext(),"3",Toast.LENGTH_SHORT).show();

                }

            }catch (Exception e){

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void ScalePic(Bitmap bitmap, int phone){
        float mScale = 1;

        if(bitmap.getWidth()>phone){
            mScale = (float)phone/(float)bitmap.getWidth();

            Matrix mMat = new Matrix();
            mMat.setScale(mScale,mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,0,0,
                    bitmap.getWidth(),bitmap.getHeight(),mMat,false);
            img.setImageBitmap(mScaleBitmap);
            bitmap_get = mScaleBitmap;
        }else{
            img.setImageBitmap(bitmap);
            bitmap_get = bitmap;
        }

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
}
