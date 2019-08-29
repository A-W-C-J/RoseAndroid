package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MWalletModel;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.ui.fragment.FlowsListFragment;
import com.rose.android.utils.Utils;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

@Route(path = "/ui/cashActivity", extras = 1)
public class CashActivity extends BaseActivity implements UserInfoContract.View {
    private UserInfoPresenter userInfoPresenter;
    private TextView cash;
    private ViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fragments;
    private String[] tabNames = {"全部明细", "充值提款", "合约明细"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_cash);
        setContentView(getRootView());
        setTitle("现金账户");
        userInfoPresenter = new UserInfoPresenter(userHttpClient, this);
        fragments = new ArrayList<>();
        fragments.add(FlowsListFragment.newInstance(null, 0));
        fragments.add(FlowsListFragment.newInstance(null, 1));
        fragments.add(FlowsListFragment.newInstance(null, 2));
        initViews();
        initListener();
        requestUserAccount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userInfoPresenter != null) {
            userInfoPresenter.dispose();
            userInfoPresenter = null;
        }
        fragments.clear();
        fragments = null;
    }

    @Override
    public void initViews() {
        super.initViews();
        cash = findViewById(R.id.tv_cash);
        viewPager = findViewById(R.id.vp_viewpager);
        indicator = findViewById(R.id.fxidv_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
        View view = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
        view.findViewById(R.id.divider).setVisibility(View.GONE);
        indicator.setCenterView(view);
        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
        colorBar.setWidth(200);
        indicator.setScrollBar(colorBar);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabNames, context, fragments));
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    public void requestUserAccount() {
        userInfoPresenter.requestUserAccount();
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MWalletModel) {
            MWalletModel walletModel = (MWalletModel) baseModel;
            cash.setText(Utils.formatWithThousandsSeparator(Utils.divide1000(walletModel.getData().getCashAsset())));
        }
        return super.requestCallBack(baseModel);
    }
}
