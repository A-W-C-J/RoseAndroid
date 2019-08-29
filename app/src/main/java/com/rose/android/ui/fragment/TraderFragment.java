package com.rose.android.ui.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.GetProductListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MProductListModel;
import com.rose.android.presenter.GetProductListPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.viewmodel.ProductListVM;

import java.util.ArrayList;


public class TraderFragment extends BaseFragment implements GetProductListContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    private int position;
//    private String mParam2;
    private MProductListModel productListModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyAdapter myAdapter;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private static final String TAG = "TraderFragment";
    private View blank;
    private TextView textView;
    private GetProductListPresenter getProductListPresenter;
    private int type = 1;

    public TraderFragment() {
        //do nothing
    }

    public static TraderFragment newInstance(String param1, String param2) {
        TraderFragment fragment = new TraderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        getProductListPresenter = new GetProductListPresenter(userHttpClient, this);
        create(R.layout.fragment_trader);
        View view = getRootView();
        setContentView(view);
        Log.e(TAG, "onCreateViewLazy: ");
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        getProductList(type);
        initViews(getRootView());
        viewPager.setCurrentItem(1);
        Log.e(TAG, "onFragmentStartLazy: ");
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        Log.e(TAG, "onFragmentStopLazy: ");
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        Log.e(TAG, "onDestroyViewLazy: ");
    }

    @Override
    public void initViews(View view) {
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        refreshLayout.setEnableRefresh(false);
        viewPager = view.findViewById(R.id.viewpager);
        blank = view.findViewById(R.id.blank);
        tabLayout = view.findViewById(R.id.tablayout);
        textView = view.findViewById(R.id.blank_text);
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        isBlank();
        tabLayout.setupWithViewPager(viewPager, false);
        myAdapter = new MyAdapter(getChildFragmentManager(), titleList, fragments);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(myAdapter);
        initListener();
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshLayout.setOnRefreshListener(refreshlayout -> getProductList(type));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab customView = tabLayout.getTabAt(i);
                    if (customView != null) {
                        View view = customView.getCustomView();
                        if (view != null) {
                            TextView text = view.findViewById(R.id.text);
                            if (i == tab.getPosition()) { // 选中状态
                                text.setTextColor(ContextCompat.getColor(context, R.color.white));
                                text.setTextSize(Utils.dip2px(context, 7));
                            } else {// 未选中状态
                                text.setTextColor(ContextCompat.getColor(context, R.color.title_color_3));
                                text.setTextSize(Utils.dip2px(context, 6));
                            }
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //do nothing
            }
        });
        for (int i = 0; i < titleList.size(); i++) {
            TabLayout.Tab customView = tabLayout.getTabAt(i);
            if (customView != null) {
                customView.setCustomView(R.layout.product_tab_item);
                View view = customView.getCustomView();
                if (view != null) {
                    ((TextView) view.findViewById(R.id.text)).setText(titleList.get(i));
                    if (i == 0) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.getWidth() / 3 - Utils.dip2px(context, 30), ViewGroup.LayoutParams.WRAP_CONTENT);
                        view.findViewById(R.id.text).setLayoutParams(layoutParams);
                        ((TextView) view.findViewById(R.id.text)).setTextSize(Utils.dip2px(context, 7));
                        ((TextView) view.findViewById(R.id.text)).setTextColor(ContextCompat.getColor(context, R.color.white));
                    } else {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.getWidth() / 3 - Utils.dip2px(context, 30), ViewGroup.LayoutParams.WRAP_CONTENT);
                        view.findViewById(R.id.text).setLayoutParams(layoutParams);
                        ((TextView) view.findViewById(R.id.text)).setTextColor(ContextCompat.getColor(context, R.color.title_color_3));
                        ((TextView) view.findViewById(R.id.text)).setTextSize(Utils.dip2px(context, 6));
                    }
                }
            }
        }
        blank.setOnClickListener(v -> getProductList(type));
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
        Log.e(TAG, "onResumeLazy: ");
    }

    public void changeItem(int position) {
        Log.e(TAG, "changeItem: " + position);
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void getProductList(int type) {
        getProductListPresenter.getProductList(type);
    }

    private void isBlank() {
        productListModel = ProductListVM.getInstance().getProductListModel();
        if (productListModel != null && productListModel.getData() != null && productListModel.getData().getProductList() != null) {
            blank.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            if (productListModel.getData().getProductList().size() >= 2) {
//                viewPager.setOffscreenPageLimit(productListModel.getData().getProductList().size() - 1);
            }
            if (!titleList.isEmpty()) {
                titleList.clear();
                fragments.clear();
            }
            for (int i = 0; i < productListModel.getData().getProductList().size(); i++) {
                titleList.add(productListModel.getData().getProductList().get(i).getName());
                fragments.add(ApplicationContractFragment.newInstance(i, ""));
            }
        } else {
            blank.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public BaseModel requestCallBack(BaseModel sv) {
        if (sv instanceof MProductListModel) {
            refreshLayout.finishRefresh(true);
            ProductListVM.getInstance().setProductListModel((MProductListModel) sv);
            isBlank();
            if (myAdapter != null) {
                myAdapter.notifyDataSetChanged();
            }
        }
        return super.requestCallBack(sv);
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        private ArrayList<String> titleList;
        private ArrayList<Fragment> fragmentList;

        MyAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
