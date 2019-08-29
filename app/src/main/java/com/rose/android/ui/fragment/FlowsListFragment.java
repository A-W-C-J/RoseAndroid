package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetWalletCashFlowListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MWalletCashFlowListModel;
import com.rose.android.presenter.GetWalletCashFlowListPresenter;
import com.rose.android.ui.adapter.WalletCashFlowListAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class FlowsListFragment extends BaseFragment implements ClickResponseListener, GetWalletCashFlowListContract.View {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  private int walletFlowType;
  private String productOrderId;
  private RecyclerView recyclerView;
  private GetWalletCashFlowListPresenter getWalletCashFlowListPresenter;
  private RefreshLayout refreshLayout;
  private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
  private WalletCashFlowListAdapter walletCashFlowListAdapter;
  private MWalletCashFlowListModel walletCashFlowListModel;
  private View blank;
  private int pageNo = 1;

  public FlowsListFragment() {
  }

  public static FlowsListFragment newInstance(String param1, int param2) {
    FlowsListFragment fragment = new FlowsListFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putInt(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      walletFlowType = getArguments().getInt(ARG_PARAM2);
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
    create(R.layout.fragment_flows_list);
    getWalletCashFlowListPresenter = new GetWalletCashFlowListPresenter(userHttpClient, this);
    View view = getRootView();
    setContentView(view);
    initViews(view);
    initListener();
  }

  @Override
  public void onDestroyViewLazy() {
    super.onDestroyViewLazy();
    if (getWalletCashFlowListPresenter != null) {
      getWalletCashFlowListPresenter.dispose();
      getWalletCashFlowListPresenter = null;
    }
  }

  @Override
  public void onResumeLazy() {
    super.onResumeLazy();
    if (productOrderId == null) {
      getWalletCashFlowList(walletFlowType, null, String.valueOf(pageNo));
    } else {
      getWalletCashFlowList(walletFlowType, Integer.valueOf(productOrderId), String.valueOf(pageNo));
    }

  }

  @Override
  public void initListener() {
    super.initListener();
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        if (productOrderId == null) {
          getWalletCashFlowList(walletFlowType, null, String.valueOf(pageNo));
        } else {
          getWalletCashFlowList(walletFlowType, Integer.valueOf(productOrderId), String.valueOf(pageNo));
        }
      }
    });
    refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        ++pageNo;
        if (productOrderId == null) {
          getWalletCashFlowList(walletFlowType, null, String.valueOf(pageNo));
        } else {
          getWalletCashFlowList(walletFlowType, Integer.valueOf(productOrderId), String.valueOf(pageNo));
        }
      }
    });
  }

  @Override
  public void initViews(View view) {
    super.initViews(view);
    recyclerView = view.findViewById(R.id.rcl_list);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    refreshLayout = view.findViewById(R.id.smart_refresh_layout);
    recyclerView.setLayoutManager(linearLayoutManager);
    walletCashFlowListAdapter = new WalletCashFlowListAdapter(this, context, walletCashFlowListModel);
    headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(walletCashFlowListAdapter);
    recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    blank = view.findViewById(R.id.blank);
  }

  @Override
  public void onWholeClick(int position) {

  }

  @Override
  public void showError(Throwable throwable) {
    super.showError(throwable);
    refreshLayout.finishRefresh(false);
    refreshLayout.finishLoadmore(false);
  }

  @Override
  public void getWalletCashFlowList(int walletFlowType, Integer productOrderId, String pageNo) {
    getWalletCashFlowListPresenter.getWalletCashFlowList(walletFlowType, productOrderId, pageNo);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MWalletCashFlowListModel) {
      if (pageNo > 1) {
        walletCashFlowListModel.getData().getUserWalletCashFlowList().addAll(((MWalletCashFlowListModel) baseModel).getData().getUserWalletCashFlowList());
        refreshLayout.finishLoadmore(true);
      } else {
        walletCashFlowListModel = (MWalletCashFlowListModel) baseModel;
        refreshLayout.finishRefresh(true);
      }
      refreshLayout.finishRefresh();
      if (walletCashFlowListModel.getData().getUserWalletCashFlowList().size() <= 0) {
        blank.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
      } else {
        blank.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        walletCashFlowListAdapter.updateData(walletCashFlowListModel);
      }
    }
    return super.requestCallBack(baseModel);
  }
}
