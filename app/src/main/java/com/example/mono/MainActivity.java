package com.example.mono;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.tabs.TabLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ViewPager mViewPager;
    private Button AddBtn, CancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
        initView();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(currentUser != null){
            VetifyUser();
        }else {
            SendUsertoLoginActivity();
        }
    }
    private void SendUsertoLoginActivity(){
        Intent loginIntent =new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
    private void SendUsertoProfileActivity(){
        Intent profileIntent =new Intent(MainActivity.this, ProfileActivity.class);
        profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(profileIntent);
        finish();
    }

    public void initView(){
        mViewPager = (ViewPager) findViewById(R.id.vpger);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.sign_out_opt){
            mAuth.signOut();
            SendUsertoLoginActivity();
        }
        if(item.getItemId() == R.id.profile_opt){
            SendUsertoProfileActivity();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void VetifyUser(){
        final String currentUserID = mAuth.getCurrentUser().getUid().toString();
        if(currentUserID != null){
            RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("name").exists()){
                        Toast.makeText(MainActivity.this, "Welcome ", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println("Khong co Du lieu");
                        SendUsertoProfileActivity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            SendUsertoLoginActivity();
        }

    }

//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.)
//    }
}
