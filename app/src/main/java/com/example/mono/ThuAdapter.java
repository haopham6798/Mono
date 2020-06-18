package com.example.mono;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThuAdapter extends BaseAdapter {
    Activity context;
    ArrayList<KhoangThu> khoangthu;
    private static LayoutInflater inflater = null;
    public ThuAdapter(Activity context, ArrayList<KhoangThu> kt) {
        this.context = context;
        this.khoangthu = kt;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return khoangthu.size();
    }

    @Override
    public KhoangThu getItem(int position) {
        return khoangthu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView==null) ? inflater.inflate(R.layout.list_item,null): itemView;
        TextView tvNhan = (TextView) itemView.findViewById(R.id.nhan);
        TextView tvNgay = (TextView) itemView.findViewById(R.id.ngay);
        TextView tvGiatri = (TextView) itemView.findViewById(R.id.gia);
        KhoangThu select = khoangthu.get(position);
        System.out.println(select.nhan);
        tvNhan.setText(select.nhan);
        tvNgay.setText(select.ngay);
        tvGiatri.setText(select.tien);
        return itemView;
    }
}
