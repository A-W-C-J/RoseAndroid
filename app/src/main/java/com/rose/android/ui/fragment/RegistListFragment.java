package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetRegistListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MRegistListModel;
import com.rose.android.presenter.GetRegistPresenter;
import com.rose.android.ui.adapter.RegistListAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;


public class RegistListFragment extends BaseFragment implements GetRegistListContract.View, ClickResponseListener {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private RecyclerView recyclerView;
  private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
  private int page = 1;
  private int pageSize = 10;
  private RegistListAdapter registListAdapter;
  private MRegistListModel.DataBean listBean;
  private GetRegistPresenter getRegistPresenter;
  private View blank;
  private MRegistListModel registListModel;

  public RegistListFragment() {
  }

  public static RegistListFragment newInstance(String param1, String param2) {
    RegistListFragment fragment = new RegistListFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void initViews(View view) {
    super.initViews(view);
    recyclerView = view.findViewById(R.id.rcl_cur_contract_list);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    refreshLayout = view.findViewById(R.id.smart_refresh_layout);
    recyclerView.setLayoutManager(linearLayoutManager);
    listBean = new MRegistListModel.DataBean();
    listBean.setPartnerList(new ArrayList<>());
    registListAdapter = new RegistListAdapter(recyclerView, listBean, this, context);
    headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(registListAdapter);
    recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    blank = view.findViewById(R.id.blank);
  }

  @Override
  public void initListener() {
    super.initListener();
    refreshLayout.setOnLoadmoreListener(refreshlayout -> {
      ++page;
      getRegistList(page, pageSize);
    });
    refreshLayout.setOnRefreshListener(refreshlayout -> {
      page = 1;
      getRegistList(page, pageSize);
    });
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  @Override
  protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(R.layout.layout_preview, container, false);
  }
  @Override
  protected void onCreateViewLazy(Bundle savedInstanceState) {
    super.onCreateViewLazy(savedInstanceState);
    create(R.layout.fragment_regist_list);
    View view = getRootView();
    getRegistPresenter = new GetRegistPresenter(userHttpClient, this);
    setContentView(view);
    initViews(view);
    initListener();
    getRegistList(page, pageSize);
  }

  @Override
  public void onDestroyViewLazy() {
    super.onDestroyViewLazy();
    if (getRegistPresenter != null) {
      getRegistPresenter.dispose();
      getRegistPresenter = null;
    }
  }

  @Override
  public void getRegistList(int pageNo, int pageSize) {
    getRegistPresenter.getRegistList(pageNo, pageSize);
  }


  @Override
  public void onWholeClick(int position) {

  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MRegistListModel) {
      if (page > 1) {
        refreshLayout.finishLoadmore(true);
        registListModel.getData().getPartnerList().addAll(((MRegistListModel) baseModel).getData().getPartnerList());
      } else {
        refreshLayout.finishRefresh(true);
        registListModel = (MRegistListModel) baseModel;
      }
      listBean.setPartnerList(registListModel.getData().getPartnerList());
      registListAdapter.updateData(listBean);
      if (page == 1) {
        if (registListModel.getData().getPartnerList().size() == 0) {
          blank.setVisibility(View.VISIBLE);
        } else {
          blank.setVisibility(View.GONE);
        }
      }
    }
    return super.requestCallBack(baseModel);
  }

}
