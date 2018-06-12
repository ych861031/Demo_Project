package com.example.yangchunghsuan.demo_project;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class RegisteredActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText username;
    EditText cellphome;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference storageRef;
    Intent intent;
    int users = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        setTitle("Registered");


        email = findViewById(R.id.edt_mail);
        password = findViewById(R.id.edt_password);
        username = findViewById(R.id.edt_name);
        cellphome = findViewById(R.id.edt_smartphone);

        Button button = findViewById(R.id.button_send_registered);

        storageRef = database.getReference("user/0");
        storageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String number = dataSnapshot.getValue().toString();
                users = Integer.parseInt(number);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (users==-1){
                    return;
                }
                Log.e("email",email.getText().toString());
                Log.e("password",password.getText().toString());

                intent = new Intent();
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            String folder = String.valueOf(users+1);
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisteredActivity.this,"註冊成功",Toast.LENGTH_SHORT).show();
                                    database.getReference("user/"+folder+"/email").setValue(email.getText().toString());
                                    database.getReference("user/"+folder+"/cellphone").setValue(cellphome.getText().toString());
                                    database.getReference("user/"+folder+"/userName").setValue(username.getText().toString());

                                    database.getReference("user/0/length").setValue(folder);


                                    setResult(RESULT_OK,intent);
                                    finish();
                                }else {
                                    Toast.makeText(RegisteredActivity.this,"註冊失敗",Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                            }
                        });




            }
        });
    }


}
