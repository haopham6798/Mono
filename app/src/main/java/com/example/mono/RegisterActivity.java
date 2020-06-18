package com.example.mono;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText Username, Passwd,Repasswd;
    private TextView AlreadyHaveAccountLink;
    private ProgressDialog loadingBar;
    private DatabaseReference RootRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();
        CreateAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CreateNewAccount();
            }
        });
        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SendUsertoLoginActivity();
            }
        });

    }


    private void InitializeFields(){
        CreateAccountButton = (Button) findViewById(R.id.register_button);
        Username = (EditText) findViewById(R.id.username);
        Passwd = (EditText) findViewById(R.id.passwd);
        Repasswd = (EditText) findViewById(R.id.repasswd);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_account);
        loadingBar = new ProgressDialog(this);
    }
    private void SendUsertoLoginActivity(){
        Intent loginIntent =new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
    private void SendUsertoMainActivity(){
        Intent mainIntent =new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void CreateNewAccount(){
        String username = Username.getText().toString();
        String password = Passwd.getText().toString();
        String repassword = Repasswd.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{
            if(password.equals(repassword)){
                loadingBar.setTitle("Create a new account");
                loadingBar.setMessage("Please, Wait for a minute");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();
                mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String currentUserID = mAuth.getCurrentUser().getUid();
                            RootRef.child("Users").child("currentUserID").setValue("");

                            SendUsertoMainActivity();
                            Toast.makeText(RegisterActivity.this, "Your Account are created",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }else{
                            String errorMess = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error"+errorMess,Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }
    }
}
