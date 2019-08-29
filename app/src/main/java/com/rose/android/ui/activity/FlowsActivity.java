package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetFlowsContract;
import com.rose.android.contract.GetOrdersContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MFlowsModel;
import com.rose.android.presenter.GetFlowsPresenter;
import com.rose.android.ui.adapter.FlowsAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;

/**
 * Created by xiaohuabu on 17/9/27.
 */
@Route(path = "/ui/flowsActivity")
public class FlowsActivity extends BaseActivity implements GetFlowsContract.View, ClickResponseListener {
  private RecyclerView recyclerView;
  private RefreshLayout refreshLayout;
  private GetFlowsPresenter getFlowsPresenter;
  private int pageNo = 1, pageSize = 10, totalSize;
  @Autowired
  public String productOrderId;
  @Autowired
  public int timeStatus;
  @Autowired
  public String title;
  private FlowsAdapter flowsAdapter;
  private ArrayList<MFlowsModel.DataBean.ProductOrderFlowListBean> productOrderFlowListBeans;
  private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
  private MFlowsModel mFlowsModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_flows);
    setContentView(getRootView());
    getFlowsPresenter = new GetFlowsPresenter(userHttpClient, this);
    setTitle(title);
    initViews();
    initListener();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (getFlowsPresenter != null) {
      getFlowsPresenter.dispose();
      getFlowsPresenter = null;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    pageNo = 1;
    pageSize = 10;
    getFlows(pageNo, pageSize, productOrderId, timeStatus);
  }

  @Override
  public void initViews() {
    super.initViews();
    recyclerView = findViewById(R.id.list);
    refreshLayout = findViewById(R.id.smart_refresh_layout);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(linearLayoutManager);
    productOrderFlowListBeans = new ArrayList<>();
    flowsAdapter = new FlowsAdapter(recyclerView, productOrderFlowListBeans, this, false);
    headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(flowsAdapter);
    recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
  }

  @Override
  public void initListener() {
    super.initListener();
    refreshLayout.setOnLoadmoreListener(refreshlayout -> {
      ++pageNo;
      getFlows(pageNo, pageSize, productOrderId, timeStatus);
    });
    refreshLayout.setOnRefreshListener(refreshlayout -> {
      pageNo = 1;
      pageSize = 10;
      getFlows(pageNo, pageSize, productOrderId, timeStatus);
    });
  }

  @Override
  public void onWholeClick(int position) {

  }

  @Override
  public void getFlows(int pageNo, int pageSize, String productOrderId, Integer timeStatus) {
    getFlowsPresenter.getFlows(pageNo, pageSize, productOrderId, timeStatus);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MFlowsModel) {
      if (pageNo > 1) {
        mFlowsModel.getData().getProductOrderFlowList().addAll(((MFlowsModel) baseModel).getData().getProductOrderFlowList());
        refreshLayout.finishLoadmore(true);
      } else {
        refreshLayout.finishRefresh(true);
        mFlowsModel = (MFlowsModel) baseModel;
      }
      productOrderFlowListBeans = (ArrayList<MFlowsModel.DataBean.ProductOrderFlowListBean>) mFlowsModel.getData().getProductOrderFlowList();
      flowsAdapter.updateData(productOrderFlowListBeans);
    }
    return super.requestCallBack(baseModel);
  }
}
