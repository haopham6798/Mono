package com.example.mono;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChiFrag extends Fragment{
    private View root;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_chi,container,false);




        ArrayList<KhoangChi> khoangchi = new ArrayList<>();


        ListView lvChi = (ListView) root.findViewById(R.id.lvChi);

//        Do Du lieu tai day
        ChiAdapter adapter = new ChiAdapter(getActivity(), khoangchi);
        lvChi.setAdapter(adapter);
//        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
//        dataref.child("chi");


        fab = (FloatingActionButton) root.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        return root;

    }
    private void openDialog() {
        dialogChi dg = new dialogChi();
        dg.show(getFragmentManager(),"Thu");
    }


}
