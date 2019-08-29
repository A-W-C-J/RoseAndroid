package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.contract.GetOrderDetailsContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.presenter.GetOrderDetailsPresenter;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.ui.fragment.BuyInFragment;
import com.rose.android.ui.fragment.HoldingFragment;
import com.rose.android.ui.fragment.InquireStockFragment;
import com.rose.android.ui.fragment.RevokeListFragment;
import com.rose.android.utils.Utils;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.Locale;
@Deprecated
@Route(path = "/ui/commissionedTransactionActivity")
public class CommissionedTransactionActivity extends BaseActivity implements GetOrderDetailsContract.View {
    private ViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fragments;
    @Autowired
    public int productOrderId;
    @Autowired
    public int orderId;
    @Autowired
    public String symbol;
    @Autowired
    public boolean isSale;
    private TextView assets;
    private GetOrderDetailsPresenter getOrderDetailsPresenter;
    private MOrderDetailsModel orderDetailsModel;
    private String[] tabNames = {"持仓", "买入", "卖出", "撤单", "查询"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        create(R.layout.activity_commissioned_transaction);
        getOrderDetailsPresenter = new GetOrderDetailsPresenter(this, userHttpClient);
        setContentView(getRootView());
        setTitle("委托交易");
        fragments = new ArrayList<>();
        fragments.add(HoldingFragment.newInstance(productOrderId, ""));
        fragments.add(BuyInFragment.newInstance(productOrderId, false,symbol));
        fragments.add(BuyInFragment.newInstance(productOrderId, true,symbol));
        fragments.add(RevokeListFragment.newInstance(productOrderId, ""));
        fragments.add(InquireStockFragment.newInstance(productOrderId, ""));
        initViews();
        initListener();
        getOrderDetails(orderId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getOrderDetailsPresenter != null) {
            getOrderDetailsPresenter.dispose();
            getOrderDetailsPresenter = null;
        }
        fragments.clear();
        fragments = null;
    }

    @Override
    public void initViews() {
        super.initViews();
        viewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        indicator = (FixedIndicatorView) findViewById(R.id.fxidv_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
        colorBar.setWidth(200);
        indicator.setScrollBar(colorBar);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabNames, context, fragments));
        assets = (TextView) findViewById(R.id.assets);
        if (isSale)
            viewPager.setCurrentItem(2, true);
        else {
            viewPager.setCurrentItem(1, true);
        }
    }

    public void updateAvailabelCash() {
        getOrderDetails(orderId);

    }

    public long getAvailabelCash() {
        return orderDetailsModel.getData().getAvailabelCash();
    }

    public void changPage() {
        viewPager.setCurrentItem(3, true);
    }

    @Override
    public void initListener() {
        super.initListener();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateAvailabelCash();
            }

            @Override
            public void onPageSelected(int position) {
                //do nothing
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    @Override
    public void getOrderDetails(int orderId) {
        getOrderDetailsPresenter.getOrderDetails(orderId);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MOrderDetailsModel) {
            orderDetailsModel = (MOrderDetailsModel) baseModel;
            assets.setText(String.format(Locale.CHINA, "%s元", Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getAvailabelCash()), 2)));
        }
        return super.requestCallBack(baseModel);
    }
}
