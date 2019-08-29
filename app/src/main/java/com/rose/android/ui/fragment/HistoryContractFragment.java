package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.annotations.Nullable;
@Deprecated
@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class HistoryContractFragment extends BaseFragment implements GetOrdersContract.View, ClickResponseListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
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

    public HistoryContractFragment() {
        // Required empty public constructor
    }

    public static HistoryContractFragment newInstance(String param1, String param2) {
        HistoryContractFragment fragment = new HistoryContractFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
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
        create(R.layout.fragment_cur_contract);
        View view = getRootView();
        getOrdersPresenter = new GetOrdersPresenter(userHttpClient, this);
        setContentView(view);
        initViews(view);
        initListener();
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
        getOrders(page, pageSize, status, null,null);
    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                pageSize = 10;
                getOrders(page, pageSize, status, null,null);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ++page;
                getOrders(page, pageSize, status, null,null);
            }
        });
    }

    @Override
    public void initViews(View view) {
        blank = view.findViewById(R.id.blank);
        applicationDouble = view.findViewById(R.id.tv_application);
        applicationDouble.setVisibility(View.GONE);
        textView = (TextView) view.findViewById(R.id.text);
        textView.setText("暂无历史合约");
        recyclerView = (RecyclerView) view.findViewById(R.id.rcl_cur_contract_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        recyclerView.setLayoutManager(linearLayoutManager);
        listBean = new MOrderModel.DataBean();
        listBean.setProductOrderList(new ArrayList<>());
        contractListAdapter = new ContractHistoryListAdapter(recyclerView, listBean, this, context);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(contractListAdapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    }

    @Override
    public void getOrders(Integer pageNo, Integer pageSize, String status, String type,@Nullable String symbol) {
        if (UserInfoManager.getInstance().getToken() != null) {
            getOrdersPresenter.getOrders(pageNo, pageSize, status, type,symbol);
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
            if ( orderModel.getData().getProductOrderList()!=null&&page > 1&&((MOrderModel) baseModel).getData().getProductOrderList()!=null) {
                orderModel.getData().getProductOrderList().addAll(((MOrderModel) baseModel).getData().getProductOrderList());
                refreshLayout.finishLoadmore(true);
            } else {
                orderModel = (MOrderModel) baseModel;
                refreshLayout.finishRefresh(true);
            }
            listBean = orderModel.getData();
            if (listBean.getProductOrderList()!=null&&listBean.getProductOrderList().isEmpty()) {
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
    public void onPauseLazy() {
        super.onPauseLazy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPauseLazy();
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
