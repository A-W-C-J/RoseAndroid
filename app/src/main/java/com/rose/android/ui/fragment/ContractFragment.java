package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.ui.adapter.PagerAdapter;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class ContractFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ContractFragment";
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fragments;
    private String[] tabNames;

    public ContractFragment() {
    }

    public static ContractFragment newInstance(String param1, String param2) {
        ContractFragment fragment = new ContractFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        create(R.layout.fragment_contract);
        fragments = new ArrayList<>();
        fragments.add(CurContractFragment.newInstance(null, "PRODUCT_ORDER_TIME_CURRENT"));
        fragments.add(HistoryContractFragment.newInstance("", ""));
        View view = getRootView();
        setContentView(view);
        initViews(view);
        tabNames = new String[]{getString(R.string.contract_cur), getString(R.string.contract_history)};
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), tabNames, context, fragments));
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        fragments.clear();
        indicatorViewPager = null;
        viewPager = null;
        fragments = null;
        indicator = null;
    }

    @Override
    public void initViews(View rootView) {
        viewPager = (ViewPager) rootView.findViewById(R.id.vp_viewpager);
        indicator = (FixedIndicatorView) rootView.findViewById(R.id.fxidv_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(getContext(), R.color.color_gold, R.color.title_color_3));
        View view = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
        indicator.setCenterView(view);
        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
        colorBar.setWidth(200);
        indicator.setScrollBar(colorBar);
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            onDestroy();
        }
        super.onHiddenChanged(hidden);

    }
}
