package com.example.mono;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Button UpdateBtn;
    private EditText Fullname, Fund;
    private CircleImageView ImgProfile;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        InitializeFields();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });
    }
    private void InitializeFields(){
        UpdateBtn = (Button) findViewById(R.id.btn_update);
        Fullname = (EditText) findViewById(R.id.fullname_txt);
        Fund = (EditText) findViewById(R.id.fund_txt);
        ImgProfile =(CircleImageView) findViewById(R.id.profile_image);
    }
    private void SendUsertoMainActivity(){
        Intent mainIntent =new Intent(ProfileActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    private void SendUsertoLoginActivity(){
        Intent loginIntent =new Intent(ProfileActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.sign_out_opt){
            mAuth.signOut();
            SendUsertoLoginActivity();
        }

        return true;
    }

    private void UpdateProfile(){
        String setName = Fullname.getText().toString();
        String setFund = Fund.getText().toString();
        if(TextUtils.isEmpty(setName)){
            Toast.makeText(this,"Please Enter Name", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setFund)){
            Toast.makeText(this,"Please Enter Fund", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserID);
            profileMap.put("name", setName);
            profileMap.put("found",setFund);
            RootRef.child("Users").child(currentUserID).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        SendUsertoMainActivity();
                        Toast.makeText(ProfileActivity.this, "Update Profile success",Toast.LENGTH_SHORT).show();
                    }else{
                        String mess = task.getException().toString();
                        Toast.makeText(ProfileActivity.this, "Error" + mess,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
