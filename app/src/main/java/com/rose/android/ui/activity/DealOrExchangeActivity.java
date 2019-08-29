package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.PostStockOrderListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MStockOrderListModel;
import com.rose.android.presenter.PostStockOrderListPresenter;
import com.rose.android.ui.adapter.StockOrderListAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by xiaohuabu on 17/9/26.
 */
@Route(path = "/ui/dealOrExchange")
public class DealOrExchangeActivity extends BaseActivity implements PostStockOrderListContract.View, ClickResponseListener {
    @Autowired
    public int productOrderId;
    @Autowired
    public Integer orderStatus;
    @Autowired
    public Integer timeStatus;
    @Autowired
    public String title;
    private PostStockOrderListPresenter postStockOrderListPresenter;
    private RecyclerView list;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private StockOrderListAdapter stockOrderListAdapter;
    private MStockOrderListModel stockOrderListModel;
    private RefreshLayout refreshLayout;
    private TextView date;
    private int pageNo = 1, pageSize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_today_deal);
        setTitle(title);
        setContentView(getRootView());
        postStockOrderListPresenter = new PostStockOrderListPresenter(userHttpClient, this);
        initViews();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (postStockOrderListPresenter != null) {
            postStockOrderListPresenter.dispose();
            postStockOrderListPresenter = null;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            pageNo = 1;
            postStockOrderList(productOrderId, orderStatus, timeStatus, pageNo, pageSize);
        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            ++pageNo;
            postStockOrderList(productOrderId, orderStatus, timeStatus, pageNo, pageSize);
        });
    }

    @Override
    public void initViews() {
        super.initViews();
        refreshLayout = findViewById(R.id.smart_refresh_layout);
        list = findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        stockOrderListAdapter = new StockOrderListAdapter(list, null, this, orderStatus);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(stockOrderListAdapter);
        list.setAdapter(headerAndFooterRecyclerViewAdapter);
        date = findViewById(R.id.date);
        switch (orderStatus) {
            case 10:
                date.setText("委托时间");
                break;
            case 30:
                date.setText("成交时间");
                break;
            default:
                date.setText("委托时间");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (orderStatus == 0) {
            orderStatus = null;
        }
        postStockOrderList(productOrderId, orderStatus, timeStatus, pageNo, pageSize);
    }

    @Override
    public void postStockOrderList(int productOrderId, @Nullable Integer orderStatus, @Nullable Integer timeStatus, int pageNo, int pageSize) {
        if (timeStatus != null && timeStatus == 0) {
            timeStatus = null;
        }
        postStockOrderListPresenter.postStockOrderList(productOrderId, orderStatus, timeStatus, pageNo, pageSize);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MStockOrderListModel) {
            if (pageNo > 1) {
                stockOrderListModel.getData().getStockOrderList().addAll(((MStockOrderListModel) baseModel).getData().getStockOrderList());
                refreshLayout.finishLoadmore(true);
            } else {
                refreshLayout.finishRefresh(true);
                stockOrderListModel = (MStockOrderListModel) baseModel;
            }
            stockOrderListAdapter.updateData((ArrayList<MStockOrderListModel.DataBean.StockOrderListBean>) stockOrderListModel.getData().getStockOrderList());
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void onWholeClick(int position) {

    }
}
