package com.example.login_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; // 회원가입 로그인, 비밀번호
    private EditText mEtname, mEtage; // 이름, 나이
    private String mGender;
    private Button mbtnMan, mbtnWoman;
    private Button mBtnRegister; // 회원가입 버튼

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance(); // 파이어베이스 회원가입 기능 인스턴스
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Login_Example");
        // 파이어베이스 데이터베이스

        //입력받은 정보들과 연결
        mEtEmail = findViewById(R.id.et_email);
        mEtPwd =  findViewById(R.id.et_pass);
        mEtname = findViewById(R.id.et_name);
        mEtage = findViewById(R.id.et_age);
        mbtnMan = (Button) findViewById(R.id.btn_man);
        mbtnWoman = (Button) findViewById(R.id.btn_woman);
        mBtnRegister = (Button) findViewById(R.id.btn_register);

        mbtnMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGender = "man";
                Toast.makeText(RegisterActivity.this, "남자", Toast.LENGTH_SHORT).show();
            }
        });

        mbtnWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGender = "woman";
                Toast.makeText(RegisterActivity.this, "여자", Toast.LENGTH_SHORT).show();
            }
        });
        // 회원가입 버튼을 눌렀을 때 동작
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 시작
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strName = mEtname.getText().toString();
                String numAge = mEtage.getText().toString();

                //파이어베이스 Auth진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(
                        RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setName(strName);
                            account.setAge(numAge);
                            account.setGender(mGender);

                            // setValue - 데이터베이스에 삽입
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            // 여기까지오면 회원가입 성공. 토스트 출력
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        }else{
                            // 도중에 빠지면 회원가입 실패. 토스트 출력
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}