package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetFlowsContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MFlowsModel;
import com.rose.android.presenter.GetFlowsPresenter;
import com.rose.android.ui.adapter.FlowsAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class ContractFlowsFragment extends BaseFragment implements GetFlowsContract.View, ClickResponseListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private static final String TAG = "ContractFlowsFragment";
  private RecyclerView recyclerView;
  private GetFlowsPresenter getFlowsPresenter;
  private int pageNo = 1, pageSize = 10, totalSize;
  private FlowsAdapter flowsAdapter;
  private ArrayList<MFlowsModel.DataBean.ProductOrderFlowListBean> productOrderFlowListBeans;
  private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
  private String productOrderId;
  private MFlowsModel mFlowsModel;

  public ContractFlowsFragment() {
  }

  public static ContractFlowsFragment newInstance(String param1, String param2) {
    ContractFlowsFragment fragment = new ContractFlowsFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      productOrderId = getArguments().getString(ARG_PARAM1);
    }
  }
  @Override
  protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(R.layout.layout_preview, container, false);
  }
  @Override
  protected void onCreateViewLazy(Bundle savedInstanceState) {
    super.onCreateViewLazy(savedInstanceState);
    create(R.layout.fragment_contract_flows);
    View view = getRootView();
    getFlowsPresenter = new GetFlowsPresenter(userHttpClient, this);
    setContentView(view);
    initViews(view);
    initListener();
  }

  @Override
  public void onDestroyViewLazy() {
    super.onDestroy();
    if (getFlowsPresenter != null) {
      getFlowsPresenter.dispose();
      getFlowsPresenter = null;
    }
  }

  @Override
  public void onResumeLazy() {
    super.onResumeLazy();
    pageNo = 1;
    pageSize = 10;
    getFlows(pageNo, pageSize, productOrderId, null);
  }

  @Override
  public void initViews(View view) {
    super.initViews(view);
    recyclerView = view.findViewById(R.id.list);
    refreshLayout = view.findViewById(R.id.smart_refresh_layout);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(linearLayoutManager);
    productOrderFlowListBeans = new ArrayList<>();
    flowsAdapter = new FlowsAdapter(recyclerView, productOrderFlowListBeans, this, true);
    headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(flowsAdapter);
    recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
  }

  @Override
  public void initListener() {
    super.initListener();
    refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        ++pageNo;
        getFlows(pageNo, pageSize, productOrderId, null);
      }
    });
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getFlows(pageNo, pageSize, productOrderId, null);
      }
    });
  }

//  @Override
//  public void onRefresh() {
//    pageNo = 1;
//    pageSize = 10;
//    getFlows(pageNo, pageSize, productOrderId, null);
//  }

  @Override
  public void onWholeClick(int position) {

  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MFlowsModel) {
      if (pageNo > 1) {
        refreshLayout.finishLoadmore(true);
        mFlowsModel.getData().getProductOrderFlowList().addAll(((MFlowsModel) baseModel).getData().getProductOrderFlowList());
      } else {
        refreshLayout.finishRefresh(true);
        mFlowsModel = (MFlowsModel) baseModel;
      }
      productOrderFlowListBeans = (ArrayList<MFlowsModel.DataBean.ProductOrderFlowListBean>) mFlowsModel.getData().getProductOrderFlowList();
      flowsAdapter.updateData(productOrderFlowListBeans);
    }
    return super.requestCallBack(baseModel);
  }

  @Override
  public void getFlows(int pageNo, int pageSize, String productOrderId, Integer timeStatus) {
    getFlowsPresenter.getFlows(pageNo, pageSize, productOrderId, timeStatus);
  }

}
