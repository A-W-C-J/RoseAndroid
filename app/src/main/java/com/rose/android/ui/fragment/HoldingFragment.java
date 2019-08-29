package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rose.android.R;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.PostOrderPositionsContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderPositionsModel;
import com.rose.android.presenter.PostOrderPositionsPresenter;
import com.rose.android.ui.adapter.OrderPositionAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class HoldingFragment extends BaseFragment implements PostOrderPositionsContract.View, ClickResponseListener {
  private static final String ARG_PARAM1 = "productOrderId";
  private static final String ARG_PARAM2 = "param2";

  private int productOrderId;
  private PostOrderPositionsPresenter postOrderPositionsPresenter;
  private RecyclerView list;
  private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
  private OrderPositionAdapter stockOrderListAdapter;
  private MOrderPositionsModel orderPositionsModel;
  private Timer timer2;
  private RefreshLayout refreshLayout;
  private int pageNo = 1, pageSize = 10;
  private ArrayList<MOrderPositionsModel.DataBean.ProductOrderPositionListBean> productOrderPositionListBeans;

  public HoldingFragment() {
    // Required empty public constructor
  }

  public static HoldingFragment newInstance(int param1, String param2) {
    HoldingFragment fragment = new HoldingFragment();
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
    }
  }
  @Override
  protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(R.layout.layout_preview, container, false);
  }
  @Override
  protected void onCreateViewLazy(Bundle savedInstanceState) {
    super.onCreateViewLazy(savedInstanceState);
    create(R.layout.fragment_holding);
    postOrderPositionsPresenter = new PostOrderPositionsPresenter(userHttpClient, this);
    View view = getRootView();
    setContentView(view);
    initViews(view);
    initListener();
  }

  @Override
  public void onDestroyViewLazy() {
    super.onDestroyViewLazy();
    if (postOrderPositionsPresenter != null) {
      postOrderPositionsPresenter.dispose();
      postOrderPositionsPresenter = null;
    }
  }

  @Override
  public void initViews(View view) {
    super.initViews(view);
    refreshLayout = view.findViewById(R.id.smart_refresh_layout);
    list = (RecyclerView) view.findViewById(R.id.list);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    list.setLayoutManager(linearLayoutManager);
    productOrderPositionListBeans = new ArrayList<>();
    stockOrderListAdapter = new OrderPositionAdapter(list, productOrderPositionListBeans, this);
    headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(stockOrderListAdapter);
    list.setAdapter(headerAndFooterRecyclerViewAdapter);
  }

  @Override
  public void initListener() {
    super.initListener();
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        pageSize = 10;
        postOrderPositions(pageNo, pageSize, String.valueOf(productOrderId));
      }
    });
    refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        ++pageNo;
        postOrderPositions(pageNo, pageSize, String.valueOf(productOrderId));
      }
    });
  }


  @Override
  public void postOrderPositions(int pageNo, int pageSize, String productOrderId) {
    postOrderPositionsPresenter.postOrderPositions(pageNo, pageSize, productOrderId);
  }

  @Override
  public void onWholeClick(int position) {

  }

  @Override
  public void onResumeLazy() {
    super.onResumeLazy();
    if (UserInfoManager.getInstance().getLoginStatus()){
      showLoadDialog();
      pageNo = 1;
      pageSize = 10;
      postOrderPositions(pageNo, pageSize, String.valueOf(productOrderId));
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (hidden) {
      onPauseLazy();
    }
  }

  @Override
  public void onPauseLazy() {
    super.onPauseLazy();
    postOrderPositionsPresenter.dispose();
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MOrderPositionsModel) {
      if (pageNo > 1) {
        orderPositionsModel.getData().getProductOrderPositionList().addAll(((MOrderPositionsModel) baseModel).getData().getProductOrderPositionList());
        refreshLayout.finishLoadmore(true);
      } else {
        refreshLayout.finishRefresh(true);
        orderPositionsModel = (MOrderPositionsModel) baseModel;
      }
      productOrderPositionListBeans = (ArrayList<MOrderPositionsModel.DataBean.ProductOrderPositionListBean>) orderPositionsModel.getData().getProductOrderPositionList();
      stockOrderListAdapter.updateData(productOrderPositionListBeans);
    }
    return super.requestCallBack(baseModel);
  }


}
