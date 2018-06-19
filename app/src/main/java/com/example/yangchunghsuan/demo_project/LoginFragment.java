package com.example.yangchunghsuan.demo_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
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

    public LoginFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseAuth auth;
    EditText account;
    EditText password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        auth = FirebaseAuth.getInstance();

    }

    View view;
    Button btn_registered;
    Button btn_login;
    CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view =  inflater.inflate(R.layout.fragment_login, container, false);
        btn_registered = view.findViewById(R.id.btn_registered);
        btn_login = view.findViewById(R.id.btn_login);
        btn_registered.setOnClickListener(listener_registered);
        btn_login.setOnClickListener(listener_login);

        account = view.findViewById(R.id.editText_account);
        password = view.findViewById(R.id.editText_password);
        checkBox = view.findViewById(R.id.checkBox);

        String email_get = view.getContext().getSharedPreferences("data",Context.MODE_PRIVATE)
                .getString("account","");
        String password_get = view.getContext().getSharedPreferences("data",Context.MODE_PRIVATE)
                .getString("password","");
        boolean check = view.getContext().getSharedPreferences("data",Context.MODE_PRIVATE)
                .getBoolean("checkBox",false);
        account.setText(email_get);
        password.setText(password_get);
        checkBox.setChecked(check);
        return view;

    }

    private Button.OnClickListener listener_registered = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(view.getContext(),RegisteredActivity.class);
            startActivityForResult(intent,111);

       }
    };
    final long ONE_MEGABYTE = 1024 * 1024 * 10;
    int bitmap_length = 0;

    SharedPreferences sharedPreferences;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    DatabaseReference databaseRef = database.getReference();
    StorageReference storageRef = firebaseStorage.getReference();

    int get;

    private Button.OnClickListener listener_login = new Button.OnClickListener(){


        @Override
        public void onClick(View v) {

            if(  password.getText().toString().equals("") ||account.getText().toString().equals("")) {
                Toast.makeText(view.getContext(),"帳號與密碼都必須輸入!",Toast.LENGTH_LONG).show();
                return;
            }

            auth.signInWithEmailAndPassword(account.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.e("auth","login");
                                Toast.makeText(view.getContext(),"登入成功",Toast.LENGTH_SHORT).show();
                                MainActivity.login = true;

                                try{
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frame,PersonalFragment.newInstance()).commitAllowingStateLoss();

                                    Calendar c = Calendar.getInstance();
                                    c.setTimeInMillis(System.currentTimeMillis());
                                    c.add(Calendar.SECOND,5);
                                    AlarmManager am = (AlarmManager)view.getContext().getSystemService(Context.ALARM_SERVICE);
                                    Intent intent = new Intent();
                                    intent.setClass(view.getContext(), update.class);

                                    PendingIntent pendingintent = PendingIntent.getBroadcast(
                                            view.getContext(), 1, intent, 0);
                                    am.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingintent);


                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                if (checkBox.isChecked()){
                                    sharedPreferences = view.getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
                                    sharedPreferences.edit()
                                            .putString("account",account.getText().toString())
                                            .putString("password",password.getText().toString())
                                            .putBoolean("checkBox",true)
                                            .apply();

                                }else{
                                    sharedPreferences = view.getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
                                    sharedPreferences.edit()
                                            .putString("account","")
                                            .putString("password","")
                                            .putBoolean("checkBox",false)
                                            .apply();
                                }

                                databaseRef = database.getReference("user/"+auth.getUid()+"/pictureArray/length");
                                databaseRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        try{
                                            get = Integer.parseInt(dataSnapshot.getValue().toString());
                                            for (int i=1;i<=get;i++){
                                                databaseRef = database.getReference("pictureArray/"+auth.getUid()+"/"+i);
                                                databaseRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        String path = dataSnapshot.getValue().toString();
                                                        if (path!=null){
                                                            storageRef = firebaseStorage.getReference(path);
                                                            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                                @Override
                                                                public void onSuccess(byte[] bytes) {
                                                                    PersonalFragment.image[bitmap_length++] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                                    Log.e("bitmap",PersonalFragment.image[bitmap_length-1].toString());
                                                                    if (bitmap_length==get){
                                                                        bitmap_length=0;
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                            }else{
                                Log.e("auth","login fail");
                                Toast.makeText(view.getContext(),"帳號或密碼錯誤!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    };


    public static class update extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity  mainActivity = new MainActivity();
            mainActivity.update();
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
