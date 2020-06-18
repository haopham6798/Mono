package com.example.mono;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThuFrag extends Fragment {

    DatabaseReference RootRef;
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private String currentUserID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_thu, container, false);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        ListView lvThu = (ListView) root.findViewById(R.id.lvThu);
        final ArrayList<KhoangThu> data = new ArrayList<>();


        final ThuAdapter adapter = new ThuAdapter(getActivity(), data);
        lvThu.setAdapter(adapter);

//        RootRef.child("Users").child(currentUserID).child("thu").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println(snapshot.getValue());
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
//                    KhoangThu kt = dataSnapshot.getValue(KhoangThu.class);
//                    data.add(kt);
//                    adapter.notifyDataSetChanged();
//                }
//            }
        RootRef.child("Users").child(currentUserID).child("thu").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                KhoangThu kt = dataSnapshot.getValue(KhoangThu.class);
//                KhoangThu kt1 = new KhoangThu(kt.nhan,kt.gia,kt.thoigian);
                data.add(kt);
                adapter.notifyDataSetChanged();
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


        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        return root;
    }

    private void openDialog() {
        dialog dg = new dialog();
        dg.show(getActivity().getSupportFragmentManager(), "Thu");
    }
}
