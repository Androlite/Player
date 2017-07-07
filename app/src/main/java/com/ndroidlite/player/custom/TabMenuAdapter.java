package com.ndroidlite.player.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.ndroidlite.player.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vatsaldesai on 02-05-2017.
 */

public class TabMenuAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    Context con;

    public TabMenuAdapter(FragmentManager fm, Context con) {
        super(fm);
        this.con = con;
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    public View getTabView(int position) {
        TfTextView tfTextView = new TfTextView(con);
        tfTextView.setText(fragmentTitleList.get(position));
        tfTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tfTextView.setTypeface(Functions.getFontType(con, FontType.SemiBold.getId()));
        tfTextView.setTextColor(ContextCompat.getColor(con, R.color.white));
        tfTextView.setGravity(Gravity.CENTER);
        return tfTextView;
    }

    public View getSelectedTabView(int position) {
        TfTextView tfTextView = new TfTextView(con);
        tfTextView.setText(fragmentTitleList.get(position));
        tfTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tfTextView.setTypeface(Functions.getFontType(con, FontType.Bold.getId()));
        tfTextView.setTextColor(ContextCompat.getColor(con, R.color.white));
        tfTextView.setGravity(Gravity.CENTER);
        return tfTextView;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return fragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
