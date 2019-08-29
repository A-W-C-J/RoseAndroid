package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.view.ContractDetailItemView;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class InquireStockFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam2;
    private int productOrderId;
    private ContractDetailItemView todayExchange, todayCommission, historyExchange, historyCommission, todayTrading, historyTrading;
    private static final String PRODUCT_ID_KEY = "productOrderId";

    public InquireStockFragment() {
        // Required empty public constructor
    }

    public static InquireStockFragment newInstance(int param1, String param2) {
        InquireStockFragment fragment = new InquireStockFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productOrderId = getArguments().getInt(ARG_PARAM1);
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
        create(R.layout.fragment_inquire_stock);
        View view = getRootView();
        setContentView(view);
        initViews(view);
        initListener();
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        todayExchange = view.findViewById(R.id.ci_today_exchange);
        todayCommission = view.findViewById(R.id.ci_commission);
        historyExchange = view.findViewById(R.id.ci_history_exchange);
        historyCommission = view.findViewById(R.id.ci_commission_history);
        todayTrading = view.findViewById(R.id.ci_trading_day);
        historyTrading = view.findViewById(R.id.ci_trading_history);
        todayExchange.setRightIconVisiable(true);
        todayCommission.setRightIconVisiable(true);
        historyCommission.setRightIconVisiable(true);
        todayTrading.setRightIconVisiable(true);
        historyTrading.setRightIconVisiable(true);
        historyExchange.setRightIconVisiable(true);
    }
    public void updateProductId(int id) {
        productOrderId = id;
    }
    @Override
    public void initListener() {
        super.initListener();
        todayExchange.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.dealOrExchange).withInt(PRODUCT_ID_KEY, productOrderId).withInt("orderStatus", 30).withInt("timeStatus", 1)
                .withString("title", "当日成交").navigation());
        todayCommission.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.dealOrExchange).withInt(PRODUCT_ID_KEY, productOrderId).withInt("timeStatus", 1)
                .withString("title", "当日委托").navigation());
        historyExchange.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.dealOrExchange).withInt(PRODUCT_ID_KEY, productOrderId).withInt("orderStatus", 30).withInt("timeStatus", 2)
                .withString("title", "历史成交").navigation());
        historyCommission.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.dealOrExchange).withInt(PRODUCT_ID_KEY, productOrderId).withInt("timeStatus", 2)
                .withString("title", "历史委托").navigation());
        todayTrading.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.flowsActivity).withString(PRODUCT_ID_KEY, String.valueOf(productOrderId)).withInt("timeStatus", 1)
                .withString("title", "当日资金流水").navigation());
        historyTrading.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.flowsActivity).withString(PRODUCT_ID_KEY, String.valueOf(productOrderId)).withInt("timeStatus", 2)
                .withString("title", "历史资金流水").navigation());
    }
}
