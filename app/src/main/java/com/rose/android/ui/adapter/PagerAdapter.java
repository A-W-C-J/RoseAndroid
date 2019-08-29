package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rose.android.R;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;

/**
 * Created by wenen on 2017/12/18.
 */

public class PagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private String[] tabNames;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fragment> fragments;
    private boolean isExperience = false;

    public PagerAdapter(FragmentManager fragmentManager,
                        String[] tabNames, Context context, ArrayList<Fragment> fragments) {
        super(fragmentManager);
        this.tabNames = tabNames.clone();
        this.context = context;
        this.fragments = fragments;
        inflater = LayoutInflater.from(this.context);
    }

    public PagerAdapter(FragmentManager fragmentManager,
                        String[] tabNames, Context context, ArrayList<Fragment> fragments, boolean isExperience) {
        super(fragmentManager);
        this.tabNames = tabNames.clone();
        this.context = context;
        this.fragments = fragments;
        this.isExperience = isExperience;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            if (isExperience) {
                convertView = inflater.inflate(R.layout.tab_exprience, container, false);
            } else
                convertView = inflater.inflate(R.layout.tab_main, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(tabNames[position]);
        return textView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return fragments.get(position);
    }
}
