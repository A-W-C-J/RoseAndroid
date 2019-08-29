package com.rose.android.ui.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetOrderDetailsContract;
import com.rose.android.contract.GetOrdersContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.model.MOrderModel;
import com.rose.android.presenter.GetOrderDetailsPresenter;
import com.rose.android.presenter.GetOrdersPresenter;
import com.rose.android.ui.activity.MainActivity;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.utils.Utils;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

/**
 * Created by wenen on 2018/1/16.
 */

public class ExchangeCenterFragment extends BaseFragment implements GetOrderDetailsContract.View, GetOrdersContract.View, ClickResponseListener {
    private ViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fragments;
    private int productOrderId;
    private int orderId;
    private String symbol;
    private boolean isSale;
    private TextView assets;
    private GetOrderDetailsPresenter getOrderDetailsPresenter;
    private MOrderDetailsModel orderDetailsModel;
    private String[] tabNames = {"持仓", "买入", "卖出", "撤单", "查询"};
    private GetOrdersPresenter getOrdersPresenter;
    private View blank;
    private View contract;
    private BuyInFragment buyInFragment, saleFragment;
    private RevokeListFragment revokeListFragment;
    private InquireStockFragment inquireStockFragment;
    private int page = 1;
    private int pageSize = 999;
    private String type = null;
    private String status = "PRODUCT_ORDER_TIME_CURRENT";
    private TextView viewContract;
    private MOrderModel mOrderModel;
    private TextView title, date;
    private int selectPosition;
    private View toRight;
    private TextView zc, ristStatus, balance;
    private Button application;
    private TextView orderNo;

    public ExchangeCenterFragment() {
    }

    public static ExchangeCenterFragment newInstance(boolean isSale) {
        ExchangeCenterFragment fragment = new ExchangeCenterFragment();
        Bundle args = new Bundle();
        args.putBoolean("isSale", isSale);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        create(R.layout.fragment_exchange_center);
        super.onCreateViewLazy(savedInstanceState);
        View view = getRootView();
        setContentView(view);
        getOrderDetailsPresenter = new GetOrderDetailsPresenter(this, userHttpClient);
        getOrdersPresenter = new GetOrdersPresenter(userHttpClient, this);
        fragments = new ArrayList<>();
        initViews();
        initListener();
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        if (UserInfoManager.getInstance().getLoginStatus()) {
            getOrders(page, pageSize, status, type, null);
            getOrderDetails(orderId);
        } else {
            blank.setVisibility(View.VISIBLE);
            contract.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        viewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        viewPager.setOffscreenPageLimit(5);
        indicator = (FixedIndicatorView) findViewById(R.id.fxidv_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
        colorBar.setWidth(200);
        indicator.setScrollBar(colorBar);
        blank = findViewById(R.id.blank);
        contract = findViewById(R.id.contract);
        viewContract = (TextView) findViewById(R.id.view_contract);
        viewContract.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        toRight = findViewById(R.id.to_right);
        zc = (TextView) findViewById(R.id.zc);
        ristStatus = (TextView) findViewById(R.id.status);
        balance = (TextView) findViewById(R.id.balance);
        application = (Button) findViewById(R.id.tv_application);
        orderNo = (TextView) findViewById(R.id.order_no);
    }

    public void updateSelect(int selectPosition) {
        this.selectPosition = selectPosition;
        if (mOrderModel != null && mOrderModel.getData() != null && mOrderModel.getData().getProductOrderList() != null) {
            getOrderDetails(mOrderModel.getData().getProductOrderList().get(selectPosition).getId());
            if (buyInFragment != null && buyInFragment.isAdded()) {
                buyInFragment.updateProductId(mOrderModel.getData().getProductOrderList().get(selectPosition).getId());
            }
            if (revokeListFragment != null && revokeListFragment.isAdded()) {
                revokeListFragment.updateProductId(mOrderModel.getData().getProductOrderList().get(selectPosition).getId());
            }
            if (inquireStockFragment != null && inquireStockFragment.isAdded()) {
                inquireStockFragment.updateProductId(mOrderModel.getData().getProductOrderList().get(selectPosition).getId());
            }
        }
    }

    public void updateSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (getOrderDetailsPresenter != null) {
            getOrderDetailsPresenter.dispose();
            getOrderDetailsPresenter = null;
        }
        fragments.clear();
        fragments = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isSale = getArguments().getBoolean("isSale");
        }
    }

    public void updateAvailabelCash() {
        getOrderDetails(orderId);
    }

    public long getAvailabelCash() {
        if (orderDetailsModel != null && orderDetailsModel.getData() != null)
            return orderDetailsModel.getData().getAvailabelCash();
        else
            return 0;
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
                if (mOrderModel != null && mOrderModel.getData() != null && mOrderModel.getData().getProductOrderList() != null) {
                    if (fragments.get(position) instanceof BuyInFragment) {
                        ((BuyInFragment) fragments.get(position)).updateProductId(mOrderModel.getData().getProductOrderList().get(selectPosition).getId());
                    } else if (fragments.get(position) instanceof RevokeListFragment) {
                        ((RevokeListFragment) fragments.get(position)).updateProductId(mOrderModel.getData().getProductOrderList().get(selectPosition).getId());
                    } else if (fragments.get(position) instanceof InquireStockFragment) {
                        ((InquireStockFragment) fragments.get(position)).updateProductId(mOrderModel.getData().getProductOrderList().get(selectPosition).getId());
                    }
                }
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
        viewContract.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.orderDetailsActivity).withInt("orderId", mOrderModel.getData().getProductOrderList().get(selectPosition).getId())
                .withString("orderName", mOrderModel.getData().getProductOrderList().get(selectPosition).getName()).withInt("selectPosition", selectPosition)
                .withBoolean("isActivity", mOrderModel.getData().getProductOrderList().get(selectPosition).getType() == 2).navigation());
        toRight.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.selectContractActivity).withInt("selectPosition", selectPosition).navigation());
        application.setOnClickListener(v -> ((MainActivity) ExchangeCenterFragment.this.getActivity()).changItem(1));
    }

    @Override
    public void getOrderDetails(int orderId) {
        if (orderId != 0)
            getOrderDetailsPresenter.getOrderDetails(orderId);
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MOrderDetailsModel) {
            orderDetailsModel = (MOrderDetailsModel) baseModel;
            title.setText(orderDetailsModel.getData().getName());
            date.setText(orderDetailsModel.getData().getEndTradingDate());
            zc.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getAssetAmount()), 2));
            balance.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getAvailabelCash()), 2));
            switch (orderDetailsModel.getData().getRiskType()) {
                case 0:
                    ristStatus.setText("正常");
                    break;
                case 1:
                    ristStatus.setText("警戒中");
                    break;
                case 2:
                    ristStatus.setText("止损中");
                    break;
                default:
                    ristStatus.setText("正常");
                    break;
            }
            orderNo.setText("(" + orderDetailsModel.getData().getOrderNo() + ")");
        } else if (baseModel instanceof MOrderModel) {
            if (((MOrderModel) baseModel).getData() != null && ((MOrderModel) baseModel).getData().getProductOrderList() != null) {
                if (((MOrderModel) baseModel).getData().getProductOrderList().isEmpty()) {
                    blank.setVisibility(View.VISIBLE);
                    contract.setVisibility(View.GONE);
                } else {
                    if (symbol == null) {
                        if (buyInFragment != null && buyInFragment.isAdded()) {
                            buyInFragment.updateProductId(((MOrderModel) baseModel).getData().getProductOrderList().get(0).getId());
                        }
                    }
                    getOrderDetails(((MOrderModel) baseModel).getData().getProductOrderList().get(0).getId());
                    mOrderModel = (MOrderModel) baseModel;
                    productOrderId = ((MOrderModel) baseModel).getData().getProductOrderList().get(0).getId();
                    buyInFragment = BuyInFragment.newInstance(productOrderId, false, symbol);
                    saleFragment = BuyInFragment.newInstance(productOrderId, true, symbol);
                    fragments.add(HoldingFragment.newInstance(productOrderId, ""));
                    fragments.add(buyInFragment);
                    fragments.add(saleFragment);
                    revokeListFragment = RevokeListFragment.newInstance(productOrderId, "");
                    inquireStockFragment = InquireStockFragment.newInstance(productOrderId, "");
                    fragments.add(revokeListFragment);
                    fragments.add(inquireStockFragment);
                    indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
                    indicatorViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), tabNames, context, fragments));
                    if (isSale)
                        viewPager.setCurrentItem(2, true);
                    else {
                        viewPager.setCurrentItem(1, true);
                    }
                    blank.setVisibility(View.GONE);
                    contract.setVisibility(View.VISIBLE);
                    if (buyInFragment != null) {
                        Log.e("BuyInFragment", "requestCallBack: " + symbol);
                        buyInFragment.updateSymbol(symbol);
                    }
                }
            }
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void onWholeClick(int position) {

    }

    @Override
    public void getOrders(Integer pageNo, Integer pageSize, String status, String type, @Nullable String symbol) {
        if (UserInfoManager.getInstance().getToken() != null) {
            Log.e("BuyInFragment", "getOrders: " + symbol);
            getOrdersPresenter.getOrders(pageNo, pageSize, status, type, symbol);
        }
    }
}
