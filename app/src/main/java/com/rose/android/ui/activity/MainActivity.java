package com.rose.android.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.rose.android.BroadcastAction;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.GetOrdersContract;
import com.rose.android.contract.GetProductListContract;
import com.rose.android.contract.MainContract;
import com.rose.android.contract.VersionContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MProductListModel;
import com.rose.android.model.MVersionModel;
import com.rose.android.presenter.GetOrdersPresenter;
import com.rose.android.presenter.GetProductListPresenter;
import com.rose.android.presenter.VersionPresenter;
import com.rose.android.ui.fragment.AddSelectFragment;
import com.rose.android.ui.fragment.ExchangeCenterFragment;
import com.rose.android.ui.fragment.IndexFragment;
import com.rose.android.ui.fragment.MineFragment;
import com.rose.android.ui.fragment.TraderFragment;
import com.rose.android.utils.Utils;
import com.rose.android.view.NoScrollTimeViewPager;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.ProductListVM;
import com.rose.android.viewmodel.UserVM;
import com.rose.android.viewmodel.VersionVm;

import org.lzh.framework.updatepluginlib.UpdateBuilder;



@Route(path = "/ui/mainActivity")
public class MainActivity extends BaseActivity implements MainContract.View, GetProductListContract.View, VersionContract.View, GetOrdersContract.View {
    private TabLayout navigationView;
    private IndexFragment indexFragment = IndexFragment.newInstance("", "");

    private TraderFragment traderFragment = TraderFragment.newInstance("", "");

    //    private SelfSelectFragment selfSelectFragment = SelfSelectFragment.newInstance("", "");
    private MineFragment mineFragment = MineFragment.newInstance("", "");
    private ExchangeCenterFragment exchangeCenterFragment = ExchangeCenterFragment.newInstance(false);
    private AddSelectFragment addSelectFragment = AddSelectFragment.newInstance(null, "PRODUCT_ORDER_TIME_CURRENT");
    private Fragment[] fragments;
    private NoScrollTimeViewPager viewpager;
    @Autowired
    public int position;
    @Autowired
    public int selectPosition;
    @Autowired
    public String symbol;
    private int type = 1;
    private GetProductListPresenter getProductListPresenter;
    private VersionPresenter versionPresenter;
    private static int[] unSelectImages = new int[]{R.mipmap.ic_gray_home, R.mipmap.ic_gray_operate, R.mipmap.ic_select_contract_gray, R.mipmap.ic_gray_contract, R.mipmap.ic_gray_my};
    private static int[] selectImages = new int[]{R.mipmap.ic_home, R.mipmap.ic_operate, R.mipmap.ic_select_contract, R.mipmap.ic_contract, R.mipmap.ic_my};
    private static String[] strings = new String[]{"首页", "操盘", "自选", "交易", "我的"};
    private MyAdapter adapter;
    private LinearLayout layout;
    private static final String TAG = "MainActivity";
    private GetOrdersPresenter getOrdersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_main);
        setContentView(getRootView());
        setArrowBackIsVisiable(false);
        setTitle(getString(R.string.index));
        getProductListPresenter = new GetProductListPresenter(userHttpClient, this);
        versionPresenter = new VersionPresenter(userHttpClient, this);
        getOrdersPresenter = new GetOrdersPresenter(userHttpClient, this);
        registerReceiver(updateAvatar, new IntentFilter(BroadcastAction.UPDATE_AVATAR));
        registerReceiver(updateSelfSelect, new IntentFilter(BroadcastAction.UPDATE_SELF_SELCET));
        registerReceiver(clearSelfSelect, new IntentFilter(BroadcastAction.CLEAR_SELF_SELECT));
        fragments = new Fragment[]{indexFragment, traderFragment, addSelectFragment, exchangeCenterFragment, mineFragment};
        initViews();
        initListener();
        changItem(position);
        UpdateBuilder builder = UpdateBuilder.create();
        builder.check();
//        GodEyeMonitor.work(context);
    }

    @Override
    public void initViews() {
        super.initViews();
        navigationView = findViewById(R.id.navigation);
        viewpager = findViewById(R.id.viewpager);
        viewpager.setNoScroll(true);
        layout = findViewById(R.id.layout);
        adapter = new MyAdapter(getSupportFragmentManager(), strings, fragments);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(3);
        navigationView.setupWithViewPager(viewpager, false);
        if (position == 0 || position == 1) {
            findViewById(R.id.appbar).setVisibility(View.GONE);
            CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, Utils.dip2px(context, 0), 0, 0);
            layout.setLayoutParams(lp);
        }
        YSFUserInfo userInfo = new YSFUserInfo();
        userInfo.data = "[{\"key\":\"avatar\",\"value\":" + UserVM.getInstance().getHeadUrl() + "}]";
        Unicorn.setUserInfo(userInfo);
    }

    @Override
    public void initListener() {
        super.initListener();
        navigationView.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                for (int i = 0; i < navigationView.getTabCount(); i++) {
                    if (navigationView.getTabAt(i) == null) {
                        return;
                    }
                    TabLayout.Tab customView = navigationView.getTabAt(i);
                    View view;
                    if (customView != null)
                        view = customView.getCustomView();
                    else {
                        return;
                    }
                    ImageView icon = null;
                    TextView text = null;
                    if (view != null) {
                        icon = view.findViewById(R.id.image);
                        text = view.findViewById(R.id.text);
                    }
                    if (icon != null && text != null) {
                        if (i == tab.getPosition()) { // 选中状态
                            icon.setImageResource(selectImages[i]);
                            text.setTextColor(ContextCompat.getColor(context, R.color.color_gold));
                            if (i == 1) {
                                if (traderFragment != null && traderFragment.isAdded()) {
                                    changeTradeItem(1, false);
                                }
                            }
                        } else {// 未选中状态
                            icon.setImageResource(unSelectImages[i]);
                            text.setTextColor(ContextCompat.getColor(context, R.color.title_color_2));
                        }
                    }
                }
                setTitle(strings[tab.getPosition()]);
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2) {
                    findViewById(R.id.appbar).setVisibility(View.GONE);
                    CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(0, Utils.dip2px(context, 0), 0, 0);
                    layout.setLayoutParams(lp);
                } else {
                    findViewById(R.id.appbar).setVisibility(View.VISIBLE);
                    CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(0, Utils.dip2px(context, 44), 0, 0);
                    layout.setLayoutParams(lp);
                }
                if (tab.getPosition() != 4) {
                    setRightIcon(R.mipmap.ic_online_servic, false);
                    setRightTxt(null);
                } else {
                    setRightIcon(R.mipmap.ic_online_servic, true);
                    setRightTxt("在线客服");
                    ConsultSource consultSource = new ConsultSource("com.yqz.yqz", UserVM.getInstance().getPhone(), UserVM.getInstance().getRealName());
                    setRightIconClick(v -> {
                        if (BuildConfig.ISTEST) {
                            ToastWithIcon.init("此版本暂不支持该功能").show();
                        } else
                            Unicorn.openServiceActivity(context, getString(R.string.app_name) + "客服", consultSource);
                    });

                }
                if (tab.getPosition() == 3) {
                    setRightTxt("今日限购");
                    setTitle("交易中心");
                    setRightIconClick(v -> ARouter.getInstance().build(RouterConfig.limitedBuyActivity).navigation());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing.
            }
        });
        for (int i = 0; i < unSelectImages.length; i++) {
            TabLayout.Tab customView = navigationView.getTabAt(i);
            if (customView == null)
                return;
            customView.setCustomView(R.layout.navigation_item);
            View view = customView.getCustomView();
            if (view == null)
                return;
            if (i == 0) {
                ((ImageView) view.findViewById(R.id.image)).setImageResource(selectImages[i]);
                ((TextView) view.findViewById(R.id.text)).setText(strings[i]);
                ((TextView) view.findViewById(R.id.text)).setTextColor(ContextCompat.getColor(context, R.color.color_gold));
            } else {
                ((ImageView) view.findViewById(R.id.image)).setImageResource(unSelectImages[i]);
                ((TextView) view.findViewById(R.id.text)).setText(strings[i]);
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductList(type);
        postVersion(1, Utils.getVersionName(context), getPackageName());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: " + position);
        setIntent(intent);
        ARouter.getInstance().inject(this);
        changItem(position);
    }

    public void changItem(int position) {
        if (viewpager != null) {
            viewpager.setCurrentItem(position, true);
            if (position == 3) {
                if (exchangeCenterFragment != null && exchangeCenterFragment.isAdded()) {
                    exchangeCenterFragment.updateSymbol(symbol);
                    exchangeCenterFragment.updateSelect(selectPosition);
                }
            }

        }
    }

    public void changeTradeItem(int position, boolean isFromList) {
        if (traderFragment != null && traderFragment.isAdded()) {
            if (isFromList) {
                traderFragment.changeItem(position);
            } else {
                traderFragment.changeItem(1);
            }
        }
    }

    @Override
    public void getProductList(int type) {
        getProductListPresenter.getProductList(type);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MProductListModel) {
            ProductListVM.getInstance().setProductListModel((MProductListModel) baseModel);
        } else if (baseModel instanceof MVersionModel) {
            VersionVm.getInstance().setVersionModel((MVersionModel) baseModel);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void postVersion(int terminalType, String versionName, String packageName) {
        versionPresenter.postVersion(terminalType, versionName, packageName);
    }

    @Override
    public void getOrders(Integer pageNo, Integer pageSize, String status, String type, String symbol) {
        if (UserInfoManager.getInstance().getToken() != null) {
            getOrdersPresenter.getOrders(pageNo, pageSize, status, type, symbol);
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private String[] titleList;
        private Fragment[] fragmentList;

        public MyAdapter(FragmentManager fm, String[] titleList, Fragment[] fragmentList) {
            super(fm);
            this.titleList = titleList.clone();
            this.fragmentList = fragmentList.clone();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList[position];
        }

        @Override
        public int getCount() {
            return fragmentList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList[position];
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getProductListPresenter != null) {
            getProductListPresenter.dispose();
            getProductListPresenter = null;
        }
        if (versionPresenter != null) {
            versionPresenter.dispose();
            versionPresenter = null;
        }
        unregisterReceiver(updateAvatar);
        unregisterReceiver(updateSelfSelect);
        unregisterReceiver(clearSelfSelect);
    }

    private BroadcastReceiver updateAvatar = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BroadcastAction.UPDATE_AVATAR.equals(intent.getAction())) {
                if (mineFragment != null && mineFragment.isAdded()) {
                    mineFragment.updateAvatar();
                }
            }
        }
    };
    private BroadcastReceiver updateSelfSelect = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BroadcastAction.UPDATE_SELF_SELCET.equals(intent.getAction())) {
                Log.e(TAG, "onReceive: " + intent.getAction());
                if (addSelectFragment != null && addSelectFragment.isAdded()) {
                    addSelectFragment.updateSelfSelect();
                }
            }
        }
    };
    private BroadcastReceiver clearSelfSelect = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BroadcastAction.CLEAR_SELF_SELECT.equals(intent.getAction())) {
                if (addSelectFragment != null && addSelectFragment.isAdded()) {
                    addSelectFragment.clearSelfSelect();
                }
            }
        }
    };
}
