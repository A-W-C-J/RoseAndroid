package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetOrdersContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderModel;
import com.rose.android.presenter.GetOrdersPresenter;
import com.rose.android.ui.activity.ExperienceActivity;
import com.rose.android.ui.activity.MainActivity;
import com.rose.android.ui.adapter.ContractListAdapter;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.rose.android.viewmodel.ActivityProductListVM;

import java.util.ArrayList;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.annotations.Nullable;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class CurContractFragment extends BaseFragment implements GetOrdersContract.View, ClickResponseListener {

    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "status";
    private String type;
    private String status;
    private RecyclerView recyclerView;
    private GetOrdersPresenter getOrdersPresenter;
    private ContractListAdapter contractListAdapter;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private int page = 1;
    private int pageSize = 10;
    private MOrderModel.DataBean listBean;
    private View blank;
    private View applicationDouble;
    private static final String TAG = "CurContractFragment";
    private MOrderModel orderModel;
    private View parent;

    public CurContractFragment() {
    }

    public static CurContractFragment newInstance(String param1, String param2) {
        CurContractFragment fragment = new CurContractFragment();
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
            type = getArguments().getString(ARG_PARAM1);
            status = getArguments().getString(ARG_PARAM2);
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
        page = 1;
        pageSize = 10;
        getOrders(page, pageSize, status, type,null);
    }

    @Override
    public void initListener() {
        applicationDouble.setOnClickListener(v -> {
            if (CurContractFragment.this.getActivity() instanceof MainActivity) {
                ((MainActivity) CurContractFragment.this.getActivity()).changItem(1);
            } else if (CurContractFragment.this.getActivity() instanceof ExperienceActivity) {
                ((ExperienceActivity) CurContractFragment.this.getActivity()).setSelectItem(0);
            }
        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            ++page;
            getOrders(page, pageSize, status, type,null);
        });
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            pageSize = 10;
            getOrders(page, pageSize, status, type,null);
        });
    }

    @Override
    public void initViews(View view) {
        parent = view.findViewById(R.id.parent);
        blank = view.findViewById(R.id.blank);
        applicationDouble = view.findViewById(R.id.tv_application);
        recyclerView = view.findViewById(R.id.rcl_cur_contract_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        recyclerView.setLayoutManager(linearLayoutManager);
        listBean = new MOrderModel.DataBean();
        listBean.setProductOrderList(new ArrayList<>());
        if (type != null) {
            parent.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else {
            parent.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_color_1));
        }
        contractListAdapter = new ContractListAdapter(recyclerView, listBean, this, context, type);
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
            if (ActivityProductListVM.getInstance().getProductListModel() != null) {
                for (int i = 0; i < ActivityProductListVM.getInstance().getProductListModel().getData().getProductList().size(); i++) {
                    if (ActivityProductListVM.getInstance().getProductListModel().getData().getProductList().get(i).getItems().size() > 0) {
                        int activityId = ActivityProductListVM.getInstance().getProductListModel().getData().getProductList().get(i).getItems().get(0).getId();
                        for (int j = 0; j < ((MOrderModel) baseModel).getData().getProductOrderList().size(); j++) {
                            if (activityId == ((MOrderModel) baseModel).getData().getProductOrderList().get(j).getProductItemId()) {
                                if (CurContractFragment.this.getActivity() instanceof ExperienceActivity) {
                                    ((ExperienceActivity) CurContractFragment.this.getActivity()).changState(i);
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.
                requestCallBack(baseModel);

    }

    @Override
    public void onWholeClick(int position) {
        ARouter.getInstance().build(RouterConfig.orderDetailsActivity)
                .withString("orderName", contractListAdapter.getOrderName(position)).withInt("orderId", contractListAdapter.getOrderId(position)).navigation();
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
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (getOrdersPresenter != null) {
            getOrdersPresenter.dispose();
            getOrdersPresenter = null;
        }
    }

}
