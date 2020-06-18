package com.example.mono;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    EditText Useremail, Userpassword;
    Button LoginBtn,SignUpBtn, ForgotPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    Handler handler =new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            rellay1.setVisibility(View.VISIBLE);
//            rellay2.setVisibility(View.VISIBLE);
            // txtbanner.setVisibility(View.INVISIBLE);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        InitializeFields();

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUsertoRegisterActivity();
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUsertoLogin();
            }
        });

//        ForgotPassword.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                ResetPassword();
////            }
////        });

    }
    public void InitializeFields(){
        LoginBtn = (Button) findViewById(R.id.login_btn);
        Useremail = (EditText) findViewById(R.id.email_etxt);
        Userpassword = (EditText) findViewById(R.id.password_etxt);
        SignUpBtn = (Button) findViewById(R.id.sign_up_btn);
        //ForgotPassword = (Button) findViewById(R.id.forgot_pw_button);
        loadingBar = new ProgressDialog(this);
    }
    private void SendUsertoRegisterActivity(){
        Intent registerIntent =new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
    }
    private void SendUsertoMainActivity(){
        Intent mainIntent =new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void AllowUsertoLogin(){
        String email = Useremail.getText().toString();
        String password = Userpassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please, Wait for a minute");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendUsertoMainActivity();
                        Toast.makeText(LoginActivity.this, "Your Account are created",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }else{
                        String errorMess = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Error"+errorMess,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

//    private void ResetPassword(){
//        String email = Useremail.getText().toString();
//        loadingBar.setTitle("Processing");
//        loadingBar.setMessage("Please, Wait for a minute");
//        loadingBar.setCanceledOnTouchOutside(true);
//        loadingBar.show();
//        mAuth.sendPasswordResetEmail(email)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            loadingBar.dismiss();
//                            Toast.makeText(LoginActivity.this, "Your Account are Reset",Toast.LENGTH_SHORT).show();
//                        }else{
//                            String errorMess = task.getException().toString();
//                            Toast.makeText(LoginActivity.this, "Error"+errorMess,Toast.LENGTH_SHORT).show();
//                            loadingBar.dismiss();
//                        }
//                    }
//                });
//    }
    @Override
    protected void onStart(){
        super.onStart();
        if(currentUser != null){
            SendUsertoMainActivity();
        }
    }
}
