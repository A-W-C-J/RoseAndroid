package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.CustomNavigationCallBack;
import com.rose.android.model.MProductListModel;
import com.rose.android.ui.fragment.ApplicationContractFragment;
import com.rose.android.viewmodel.ProductListVM;

import java.util.ArrayList;

@Route(path = "/ui/applicationContractActivity")
public class ApplicationContractActivity extends BaseActivity{
    private MProductListModel productListModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyAdapter myAdapter;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_application_contract);
        setContentView(getRootView());
        setTitle(getString(R.string.application_contract));
        setRightTxt(getString(R.string.my_contract));
        setRightTxtClick(v -> {
            ARouter.getInstance().build(RouterConfig.main).withInt("position", 2).navigation(this, new CustomNavigationCallBack(ApplicationContractActivity.this,true));
        });
        initViews();
        initListener();
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initViews() {
        productListModel = ProductListVM.getInstance().getProductListModel();
        viewPager = findViewById(R.id.viewpager);
        if (productListModel.getData().getProductList().size() >= 2) {
            viewPager.setOffscreenPageLimit(productListModel.getData().getProductList().size() - 1);
        }
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, false);
        if (productListModel != null && productListModel.getData() != null && productListModel.getData().getProductList() != null) {
            for (int i = 0; i < productListModel.getData().getProductList().size(); i++) {
                titleList.add(productListModel.getData().getProductList().get(i).getName());
                fragments.add(ApplicationContractFragment.newInstance(i, ""));
            }
        }
        myAdapter = new MyAdapter(getSupportFragmentManager(), titleList, fragments);
        viewPager.setAdapter(myAdapter);

    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private ArrayList<String> titleList;
        private ArrayList<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
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
