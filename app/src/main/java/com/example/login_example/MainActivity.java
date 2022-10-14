package com.example.login_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabase;
    private TextView mTvEmail; // 이메일
    private TextView mTvname, mTvage; // 이름, 나이
    private TextView mTvGender; // 성별

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mTvEmail = (TextView) findViewById(R.id.tv_email);
        mTvname = (TextView) findViewById(R.id.tv_name);
        mTvage = (TextView) findViewById(R.id.tv_age);
        mTvGender = (TextView) findViewById(R.id.tv_gender);

        mFirebaseDatabase.child("UserAccount").child(mFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void readUser(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount userAccount = snapshot.getValue(UserAccount.class);

                mTvEmail.setText(userAccount.getEmailId());
                mTvname.setText(userAccount.getName());
                mTvage.setText(userAccount.getAge());
                mTvGender.setText(userAccount.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }
}