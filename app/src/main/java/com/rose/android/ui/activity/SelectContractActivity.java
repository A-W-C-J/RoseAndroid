package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

@Route(path = "/ui/select_contract_activity")
public class SelectContractActivity extends BaseActivity implements GetOrdersContract.View {
    private RecyclerView list;
    private GetOrdersPresenter getOrdersPresenter;
    private int page = 1;
    private int pageSize = 999;
    private String type = null;
    private String status = "PRODUCT_ORDER_TIME_CURRENT";
    private ContractListAdapter contractListAdapter;
    private MOrderModel.DataBean dataBean;
    @Autowired
    public int selectPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_select_contract);
        setContentView(getRootView());
        setTitle("选择合约");
        getOrdersPresenter = new GetOrdersPresenter(userHttpClient, this);
        initViews();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrders(page, pageSize, status, type, null);
    }

    @Override
    public void initViews() {
        super.initViews();
        list = findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        dataBean = new MOrderModel.DataBean();
        contractListAdapter = new ContractListAdapter(list, dataBean, position -> {
            selectPosition = position;
            ARouter.getInstance().build(RouterConfig.main).withInt("position", 3).withInt("selectPosition", position).navigation();
        }, this, selectPosition, true, position -> ARouter.getInstance().build(RouterConfig.orderDetailsActivity).withInt("orderId", dataBean.getProductOrderList().get(position).getId())
                .withString("orderName", dataBean.getProductOrderList().get(position).getName()).withInt("selectPosition", selectPosition)
                .withBoolean("isActivity", dataBean.getProductOrderList().get(position).getType() == 2).navigation());
        list.setAdapter(contractListAdapter);
    }

    @Override
    public void getOrders(Integer pageNo, Integer pageSize, String status, String type, String symbol) {
        getOrdersPresenter.getOrders(pageNo, pageSize, status, type, symbol);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MOrderModel) {
            dataBean = ((MOrderModel) baseModel).getData();
            contractListAdapter.updateData(dataBean);
        }
        return super.requestCallBack(baseModel);
    }
}
