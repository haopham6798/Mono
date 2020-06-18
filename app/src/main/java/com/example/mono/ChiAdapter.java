package com.example.mono;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChiAdapter extends BaseAdapter {
    Activity context;
    ArrayList<KhoangChi> khoangthu;
    private static LayoutInflater inflater = null;
    public ChiAdapter(Activity context, ArrayList<KhoangChi> kt) {
        this.context = context;
        this.khoangthu = kt;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return khoangthu.size();
    }

    @Override
    public KhoangChi getItem(int position) {
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
        KhoangChi select = khoangthu.get(position);
        tvNhan.setText(select.nhan);
        tvNgay.setText(select.thoigian);
        tvGiatri.setText(select.gia);
        return itemView;
    }
}
