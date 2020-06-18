package com.example.mono;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyAdapter extends FragmentStatePagerAdapter {
    private String listTab[] = {"Thu", "Chi"};
    private ThuFrag mThuFrag;
    private ChiFrag mChiFrag;

    public MyAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mThuFrag = new ThuFrag();
        mChiFrag = new ChiFrag();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return mThuFrag;
        }else if (position==1){
           return mChiFrag;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }

    @Override
    public int getCount() {
        return listTab.length;
    }
}
