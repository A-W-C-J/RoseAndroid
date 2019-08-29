package com.rose.android.ui.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.ui.activity.SearchActivity;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.view.NoScrollTimeViewPager;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.UserVM;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

@Deprecated
public class SelfSelectFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private NoScrollTimeViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fragments;
    private String[] tabNames;
    private ImageView service, search;
    private AddSelectFragment addSelectFragment;

    public SelfSelectFragment() {
        // Required empty public constructor
    }

    public static SelfSelectFragment newInstance(String param1, String param2) {
        SelfSelectFragment fragment = new SelfSelectFragment();
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
        create(R.layout.fragment_self_select);
        fragments = new ArrayList<>();
        addSelectFragment = AddSelectFragment.newInstance(null, "PRODUCT_ORDER_TIME_CURRENT");
        fragments.add(addSelectFragment);
//        fragments.add(ContractFragment.newInstance("", ""));
        View view = getRootView();
        setContentView(view);
        initViews(view);
        initListener();
    }

    public void updateSelfSelect() {
        if (addSelectFragment != null && addSelectFragment.isAdded()) {
            addSelectFragment.updateSelfSelect();
        }
    }

    public void clearSelfSelect() {
        if (addSelectFragment != null && addSelectFragment.isAdded()) {
            addSelectFragment.clearSelfSelect();
        }
    }

    @Override
    public void initViews(View rootView) {
        service = rootView.findViewById(R.id.iv_service);
        search = rootView.findViewById(R.id.iv_search);
        viewPager = rootView.findViewById(R.id.vp_viewpager);
        viewPager.setNoScroll(true);
        indicator = rootView.findViewById(R.id.fxidv_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(getContext(), R.color.title_color_3, R.color.title_color_3));
        View view = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
        indicator.setCenterView(view);
//        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
//        colorBar.setWidth(200);
//        indicator.setScrollBar(colorBar);
//        tabNames = new String[]{getString(R.string.self_selct), getString(R.string.contract)};
        tabNames = new String[]{getString(R.string.self_selct)};
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), tabNames, context, fragments));
    }

    @Override
    public void initListener() {
        super.initListener();
        ConsultSource consultSource = new ConsultSource("com.yqz.yqz", UserVM.getInstance().getPhone(), UserVM.getInstance().getRealName());
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            if (currentItem == 1) {
                service.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
            } else {
                search.setVisibility(View.VISIBLE);
                service.setVisibility(View.VISIBLE);
            }
        });
        search.setOnClickListener(v -> {
            if (search.getVisibility() == View.VISIBLE) {
                Intent intent = new Intent(context, SearchActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SelfSelectFragment.this.getActivity(), search, "search_icon");
                context.startActivity(intent, optionsCompat.toBundle());
            }
        });
        service.setOnClickListener(v -> {
            if (BuildConfig.ISTEST) {
                ToastWithIcon.init("此版本暂不支持该功能").show();
            } else
                Unicorn.openServiceActivity(context, SelfSelectFragment.this.getString(R.string.app_name) + "客服", consultSource);
        });
    }
}
