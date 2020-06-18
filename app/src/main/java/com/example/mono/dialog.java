package com.example.mono;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class dialog extends AppCompatDialogFragment {
    private EditText nhan;
    private EditText giatri;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add,null);
        builder.setView(view)
                .setTitle("Thêm")
                .setNegativeButton(Html.fromHtml("<font color='#FF7F27'>Cancel</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Ok</font>"), new DialogInterface.OnClickListener() {
                    //@RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        nhan = (EditText) view.findViewById(R.id.txtnhan);
        giatri =(EditText) view.findViewById(R.id.txtgia);

        return builder.create();
    }
}
