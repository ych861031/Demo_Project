package com.example.yangchunghsuan.demo_project;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


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
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef = database.getReference("userpage/0/length");
    Button uploadBtn;
    int folder;
    String id;
    boolean check =true;
    boolean check_userPagelength=true;
    EditText editText_storeName;
    EditText editText_address;
    EditText editText_comment;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //取得目前這個view的內容
        view = inflater.inflate(R.layout.fragment_upload, container, false);
        //從這個view找button.img
        editText_storeName = view.findViewById(R.id.editText_storeName);
        editText_address = view.findViewById(R.id.editText_address);
        editText_comment = view.findViewById(R.id.editText＿comment);


        Button button = view.findViewById(R.id.bt1);
        Button CaBtn = view.findViewById(R.id.cBt3);
        uploadBtn = view.findViewById(R.id.bt2);
        img = view.findViewById(R.id.img);
        img.setImageResource(R.drawable.album);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PHOTO);
            }
        });

        CaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
                Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri.getPath());
                startActivityForResult(intent,CAMERA);
            }
        });

        try{
            if (bitmap_get!=null){
                img.setImageBitmap(bitmap_get);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String get = dataSnapshot.getValue().toString();
                if (get!=null){
                    if (check_userPagelength){
                        folder = Integer.parseInt(get)+1;
                        check_userPagelength = false;
                    }
                    storageRef = storage.getReference("Users/"+folder+"/1.jpg");
                    uploadBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (MainActivity.login){
                                Log.e("id",auth.getUid());
                                id = auth.getUid();
                                img.setDrawingCacheEnabled(true);
                                img.buildDrawingCache();
                                Bitmap bitmapUpload = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmapUpload.compress(Bitmap.CompressFormat.JPEG,10,baos);
                                byte[] data = baos.toByteArray();
                                UploadTask uploadTask = storageRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("listner","fail");
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Log.e("listner","success");
                                        database.getReference("userpage/0/length").setValue(folder);
                                        database.getReference("userpage/"+folder+"/info/address").setValue("test");
                                        database.getReference("userpage/"+folder+"/info/name").setValue("test");
                                        database.getReference("userpage/"+folder+"/info/picture").setValue("Users/"+folder+"/1.jpg");
                                        database.getReference("userpage/"+folder+"/info/name").setValue(editText_storeName.getText().toString());
                                        database.getReference("userpage/"+folder+"/info/address").setValue(editText_address.getText().toString());
                                        database.getReference("userpage/"+folder+"/info/comment").setValue(editText_comment.getText().toString());
                                        databaseRef = database.getReference("user/"+id+"/pictureArray/length");
                                        databaseRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                //防止重複寫入
                                                if (check){
                                                    String get = dataSnapshot.getValue().toString();
                                                    int array_length = Integer.parseInt(get)+1;
                                                    database.getReference("user/"+id+"/pictureArray/length").setValue(array_length);
                                                    database.getReference("pictureArray/"+id+"/"+array_length).setValue("Users/"+folder+"/1.jpg");
                                                    check = false;
                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                });
                                Toast.makeText(view.getContext(),"upload",Toast.LENGTH_SHORT).show();
                            }else {
                                new AlertDialog.Builder(view.getContext())
                                        .setTitle("注意")
                                        .setMessage("必須登入才可上傳")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //回傳這個view讓MainActivity更改fragment
        return view;
    }

    public static Bitmap bitmap_get;
    ContentResolver cr;
    Uri uri;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,UploadFragment.newInstance()).commitAllowingStateLoss();
        try{
            //為了早點拿到圖片
            uri = data.getData();
            cr = getContext().getContentResolver();
            BitmapFactory.Options mOption = new BitmapFactory.Options();
            //Size=2為將原始圖片縮小1/2，Size=4為1/4，以此類推
            mOption.inSampleSize = 2;
            bitmap_get = BitmapFactory.decodeStream(cr.openInputStream(uri),null,mOption);

        }catch (Exception e){
            e.printStackTrace();
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
