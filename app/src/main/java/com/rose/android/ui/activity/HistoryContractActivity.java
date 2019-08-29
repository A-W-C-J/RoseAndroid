package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetOrdersContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderModel;
import com.rose.android.presenter.GetOrdersPresenter;
import com.rose.android.ui.adapter.ContractHistoryListAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

@Route(path = "/ui/historyContractActivity")
public class HistoryContractActivity extends BaseActivity implements GetOrdersContract.View, ClickResponseListener {
    private RecyclerView recyclerView;
    private GetOrdersPresenter getOrdersPresenter;
    private ContractHistoryListAdapter contractListAdapter;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private int page = 1;
    private int pageSize = 10;
    private MOrderModel.DataBean listBean;
    private View blank;
    private View applicationDouble;
    private String status = "PRODUCT_ORDER_TIME_PAST";
    private TextView textView;
    private MOrderModel orderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_history_contract);
        setContentView(getRootView());
        getOrdersPresenter = new GetOrdersPresenter(userHttpClient, this);
        setTitle("历史合约");
        initViews();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrders(page, pageSize, status, null, null);
    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                pageSize = 10;
                getOrders(page, pageSize, status, null, null);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                getOrders(page, pageSize, status, null, null);
            }
        });
    }

    @Override
    public void initViews() {
        blank = findViewById(R.id.blank);
        applicationDouble = findViewById(R.id.tv_application);
        applicationDouble.setVisibility(View.GONE);
        textView = findViewById(R.id.text);
        textView.setText("暂无历史合约");
        recyclerView = findViewById(R.id.rcl_cur_contract_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshLayout = findViewById(R.id.smart_refresh_layout);
        recyclerView.setLayoutManager(linearLayoutManager);
        listBean = new MOrderModel.DataBean();
        listBean.setProductOrderList(new ArrayList<>());
        contractListAdapter = new ContractHistoryListAdapter(recyclerView, listBean, this, context);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(contractListAdapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    }

    @Override
    public void getOrders(Integer pageNo, Integer pageSize, String status, String type, @Nullable String symbol) {
        if (UserInfoManager.getInstance().getToken() != null) {
            getOrdersPresenter.getOrders(pageNo, pageSize, status, type, symbol);
        } else {
            listBean = new MOrderModel.DataBean();
            listBean.setProductOrderList(new ArrayList<>());
            blank.setVisibility(View.VISIBLE);
            contractListAdapter.updateData(listBean);
        }
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MOrderModel) {
            if (orderModel != null && orderModel.getData().getProductOrderList() != null && page > 1 && ((MOrderModel) baseModel).getData().getProductOrderList() != null) {
                orderModel.getData().getProductOrderList().addAll(((MOrderModel) baseModel).getData().getProductOrderList());
                refreshLayout.finishLoadmore(true);
            } else {
                orderModel = (MOrderModel) baseModel;
                refreshLayout.finishRefresh(true);
            }
            listBean = orderModel.getData();
            if (listBean.getProductOrderList() != null && listBean.getProductOrderList().isEmpty()) {
                blank.setVisibility(View.VISIBLE);
            } else {
                blank.setVisibility(View.GONE);
            }
        }
        contractListAdapter.updateData(listBean);
        return super.requestCallBack(baseModel);
    }

    @Override
    public void onWholeClick(int position) {
        if (position >= 0) {
            if (contractListAdapter.isActivity(position)) {
                ARouter.getInstance().build(RouterConfig.activityOrderDetailsActivity)
                        .withString("orderName", contractListAdapter.getOrderName(position))
                        .withInt("orderId", contractListAdapter.getOrderId(position))
                        .withBoolean("isActivity", contractListAdapter.isActivity(position))
                        .withInt("productOrderId", contractListAdapter.getProductItemId(position))
                        .navigation();
            } else {
                ARouter.getInstance().build(RouterConfig.historyContractDetatisActivity)
                        .withString("orderName", contractListAdapter.getOrderName(position))
                        .withString("orderName", contractListAdapter.getOrderName(position))
                        .withInt("orderId", contractListAdapter.getOrderId(position))
                        .withInt("productOrderId", contractListAdapter.getProductItemId(position))
                        .navigation();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getOrdersPresenter != null) {
            getOrdersPresenter.dispose();
            getOrdersPresenter = null;
        }
    }
}
