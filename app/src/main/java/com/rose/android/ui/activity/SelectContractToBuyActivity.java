package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetOrdersContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderModel;
import com.rose.android.presenter.GetOrdersPresenter;
import com.rose.android.ui.adapter.ContractListAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
@Deprecated
@Route(path = "/ui/SelectContractToBuyActivity")
public class SelectContractToBuyActivity extends BaseActivity implements GetOrdersContract.View, ClickResponseListener {
    @Autowired
    public String symbol;
    @Autowired
    public boolean isSale;
    private String status = "PRODUCT_ORDER_TIME_CURRENT";
    private GetOrdersPresenter getOrdersPresenter;
    private RecyclerView recyclerView;
    private int page = 1;
    private int pageSize = 10;
    private ContractListAdapter contractListAdapter;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private MOrderModel.DataBean listBean;
    private View blank;
    private View applicationDouble;
    private MOrderModel orderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_select_contract_to_buy);
        View view = getRootView();
        setContentView(view);
        setTitle("选择合约");
        getOrdersPresenter = new GetOrdersPresenter(userHttpClient, this);
        initViews();
        initListener();
        if (isSale)
            getOrders(page, pageSize, status, null, symbol);
        else
            getOrders(page, pageSize, status, null, null);
    }

    @Override
    public void initViews() {
        super.initViews();
        blank = view.findViewById(R.id.blank);
        applicationDouble = view.findViewById(R.id.tv_application);
        recyclerView = view.findViewById(R.id.rcl_cur_contract_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        recyclerView.setLayoutManager(linearLayoutManager);
        listBean = new MOrderModel.DataBean();
        listBean.setProductOrderList(new ArrayList<>());
        contractListAdapter = new ContractListAdapter(recyclerView, listBean, this, context, true);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(contractListAdapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    }

    @Override
    public void initListener() {
        applicationDouble.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterConfig.main).withInt("position", 1).navigation();

        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            ++page;
            getOrders(page, pageSize, status, null, symbol);
        });
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            pageSize = 10;
            getOrders(page, pageSize, status, null, symbol);
        });
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MOrderModel) {
            if (page > 1) {
                refreshLayout.finishLoadmore(true);
                orderModel.getData().getProductOrderList().addAll(((MOrderModel) baseModel).getData().getProductOrderList());
                listBean = orderModel.getData();
            } else {
                refreshLayout.finishRefresh(true);
                orderModel = (MOrderModel) baseModel;
            }
            listBean = orderModel.getData();
            if (listBean != null && listBean.getProductOrderList() != null) {
                if (listBean.getProductOrderList().size() == 0) {
                    blank.setVisibility(View.VISIBLE);
                } else {
                    blank.setVisibility(View.GONE);
                }
            } else {
                blank.setVisibility(View.GONE);
            }
            contractListAdapter.updateData(listBean);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void getOrders(Integer pageNo, Integer pageSize, String status, String type, String symbol) {
        getOrdersPresenter.getOrders(pageNo, pageSize, status, type, symbol);
    }

    @Override
    public void onWholeClick(int position) {
        ARouter.getInstance().build(RouterConfig.main)
                .withInt("position", 3).navigation();
//                .withInt("orderId", listBean.getProductOrderList().get(position).getId()).withBoolean("isSale", isSale).withString("symbol", symbol).navigation();
    }
}
