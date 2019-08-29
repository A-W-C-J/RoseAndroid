package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rose.android.R;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.PostRevokeContract;
import com.rose.android.contract.PostStockOrderListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MRevokeModel;
import com.rose.android.model.MStockOrderListModel;
import com.rose.android.presenter.PostRevokePresenter;
import com.rose.android.presenter.PostStockOrderListPresenter;
import com.rose.android.ui.adapter.StockOrderListAdapter;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;


public class RevokeListFragment extends BaseFragment implements PostRevokeContract.View, PostStockOrderListContract.View, ClickResponseListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PostStockOrderListPresenter postStockOrderListPresenter;
    private PostRevokePresenter postRevokePresenter;
    private int productOrderId;
    private int orderStatus = 10;
    private RecyclerView list;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private StockOrderListAdapter stockOrderListAdapter;
    private MStockOrderListModel stockOrderListModel;
    private Button btnRevoke;
    private int clickPosition;
    private int pageNo = 1, pageSize = 10;
    private ArrayList<MStockOrderListModel.DataBean.StockOrderListBean> stockOrderListBeans;
    private static final String TAG = "RevokeListFragment";
    public RevokeListFragment() {
    }

    public static RevokeListFragment newInstance(int param1, String param2) {
        RevokeListFragment fragment = new RevokeListFragment();
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
        Log.e(TAG, "onCreate: "+productOrderId );
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        postStockOrderListPresenter = new PostStockOrderListPresenter(userHttpClient, this);
        postRevokePresenter = new PostRevokePresenter(this, userHttpClient);
        create(R.layout.fragment_revoke_list);
        View view = getRootView();
        setContentView(view);
        initViews(view);
        initListener();
    }

    public void updateProductId(int id) {
        productOrderId = id;
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (postRevokePresenter != null) {
            postRevokePresenter.dispose();
            postRevokePresenter = null;
        }
        if (postStockOrderListPresenter != null) {
            postStockOrderListPresenter.dispose();
            postStockOrderListPresenter = null;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        btnRevoke.setOnClickListener(v -> postRevoke(stockOrderListModel.getData().getStockOrderList().get(clickPosition).getOrderNo(), productOrderId + ""));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            pageNo = 1;
            pageSize = 10;
            postStockOrderList(productOrderId, orderStatus, null, pageNo, pageSize);
        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            ++pageNo;
            postStockOrderList(productOrderId, orderStatus, null, pageNo, pageSize);
        });
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        list = (RecyclerView) view.findViewById(R.id.list);
        btnRevoke = (Button) view.findViewById(R.id.btn_revoke);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        stockOrderListBeans = new ArrayList<>();
        stockOrderListAdapter = new StockOrderListAdapter(list, stockOrderListBeans, this, orderStatus);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(stockOrderListAdapter);
        list.setAdapter(headerAndFooterRecyclerViewAdapter);
    }

    @Override
    public void postRevoke(String stockOrderNo, String productOrderId) {
        postRevokePresenter.postRevoke(stockOrderNo, productOrderId);
    }

    @Override
    public void postStockOrderList(int productOrderId, @Nullable Integer orderStatus, @Nullable Integer timeStatus, int pageNo, int pageSize) {
        postStockOrderListPresenter.postStockOrderList(productOrderId, orderStatus, timeStatus, pageNo, pageSize);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MStockOrderListModel) {
            if (pageNo > 1) {
                refreshLayout.finishLoadmore(true);
                stockOrderListModel.getData().getStockOrderList().addAll(((MStockOrderListModel) baseModel).getData().getStockOrderList());
            } else {
                refreshLayout.finishRefresh(true);
                stockOrderListModel = (MStockOrderListModel) baseModel;
            }
            stockOrderListBeans = (ArrayList<MStockOrderListModel.DataBean.StockOrderListBean>) stockOrderListModel.getData().getStockOrderList();
            stockOrderListAdapter.updateData(stockOrderListBeans);
        } else if (baseModel instanceof MRevokeModel) {
            ToastWithIcon.init("操作成功，请在当日委托中查询进度").show();
            refreshLayout.autoRefresh();
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
        if (UserInfoManager.getInstance().getLoginStatus()){
            showLoadDialog();
            pageNo = 1;
            pageSize = 10;
            postStockOrderList(productOrderId, orderStatus, null, pageNo, pageSize);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onResumeLazy();
        }
    }

    @Override
    public void onWholeClick(int position) {
        if (stockOrderListBeans != null && !stockOrderListBeans.isEmpty()) {
            for (int i = 0; i < stockOrderListBeans.size(); i++) {
                if (i != position) {
                    stockOrderListBeans.get(i).setHasSelect(false);
                } else {
                    if (stockOrderListBeans.get(i).isHasSelect()) {
                        stockOrderListBeans.get(i).setHasSelect(false);
                        btnRevoke.setVisibility(View.GONE);
                    } else {
                        stockOrderListBeans.get(i).setHasSelect(true);
                        btnRevoke.setVisibility(View.VISIBLE);
                    }
                }
            }
            clickPosition = position;
            stockOrderListAdapter.updateData(stockOrderListBeans);
        }
    }


}
