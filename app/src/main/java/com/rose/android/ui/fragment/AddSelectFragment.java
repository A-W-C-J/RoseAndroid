package com.rose.android.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.DeleteSelfSelectStockContract;
import com.rose.android.contract.GetSelfSelectStockContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MSelectStockDeleteModel;
import com.rose.android.model.MSelfSelectListModel;
import com.rose.android.presenter.DeleteSelfSelectStockPresenter;
import com.rose.android.presenter.GetSelfSelectStockPresenter;
import com.rose.android.ui.activity.SearchActivity;
import com.rose.android.ui.adapter.SelfSelectAdapter;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.rose.android.viewmodel.UserVM;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;


public class AddSelectFragment extends BaseFragment implements GetSelfSelectStockContract.View, ClickResponseListener, DeleteSelfSelectStockContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SwipeMenuRecyclerView recyclerView;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private SelfSelectAdapter selfSelectAdapter;
    private GetSelfSelectStockPresenter getSelfSelectStockPresenter;
    private ArrayList<MSelfSelectListModel.DataBean.StockListBean> dataBeans;
    private DeleteSelfSelectStockPresenter deleteSelfSelectStockPresenter;
    private ImageView service, search;
    private View blank;
    private Button add;

    public AddSelectFragment() {
        // Required empty public constructor
    }

    public static AddSelectFragment newInstance(String param1, String param2) {
        AddSelectFragment fragment = new AddSelectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
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
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        create(R.layout.fragment_add_select);
        super.onCreateViewLazy(savedInstanceState);
        View view = getRootView();
        setContentView(view);
        getSelfSelectStockPresenter = new GetSelfSelectStockPresenter(this, userHttpClient);
        deleteSelfSelectStockPresenter = new DeleteSelfSelectStockPresenter(userHttpClient, this);
        initViews(view);
        initListener();
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        getSelfSelectStock();
    }

    @Override
    public void initListener() {
        super.initListener();
        ConsultSource consultSource = new ConsultSource("com.yqz.yqz", UserVM.getInstance().getPhone(), UserVM.getInstance().getRealName());
        search.setOnClickListener(v -> {
            if (search.getVisibility() == View.VISIBLE) {
                Intent intent = new Intent(context, SearchActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(AddSelectFragment.this.getActivity(), search, "search_icon");
                context.startActivity(intent, optionsCompat.toBundle());
            }
        });
        service.setOnClickListener(v -> {
            if (BuildConfig.ISTEST) {
                ToastWithIcon.init("此版本暂不支持该功能").show();
            } else
                Unicorn.openServiceActivity(context, AddSelectFragment.this.getString(R.string.app_name) + "客服", consultSource);
        });
        add.setOnClickListener(v -> {
            if (UserInfoManager.getInstance().getLoginStatus()) {
                Intent intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
            } else
                showReLogin(true);
        });
    }

    public void updateSelfSelect() {
        getSelfSelectStock();
    }

    public void clearSelfSelect() {
        if (dataBeans != null) {
            dataBeans.clear();
        }
        if (selfSelectAdapter != null) {
            selfSelectAdapter.updateData(dataBeans);
        }
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        blank = findViewById(R.id.blank);
        service = view.findViewById(R.id.iv_service);
        search = view.findViewById(R.id.iv_search);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setSwipeMenuCreator((swipeLeftMenu, swipeRightMenu, viewType) -> {
            SwipeMenuItem deleteItem = new SwipeMenuItem(context);
            deleteItem.setText("\t\t\t删除自选\t\t\t");
            deleteItem.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            deleteItem.setTextColor(ContextCompat.getColor(context, R.color.black));
            deleteItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            deleteItem.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            swipeRightMenu.addMenuItem(deleteItem);
        });
        recyclerView.setSwipeMenuItemClickListener(menuBridge -> {
            menuBridge.closeMenu();
            deleteSelfSelect(dataBeans.get(menuBridge.getAdapterPosition()).getStockExchange(),
                    dataBeans.get(menuBridge.getAdapterPosition()).getStockName(),
                    dataBeans.get(menuBridge.getAdapterPosition()).getStockSymbol());
            dataBeans.remove(menuBridge.getAdapterPosition());
            if (selfSelectAdapter != null) {
                selfSelectAdapter.updateData(dataBeans);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataBeans = new ArrayList<>();
        selfSelectAdapter = new SelfSelectAdapter(recyclerView, dataBeans, this);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(selfSelectAdapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        add = (Button) findViewById(R.id.btn_join_now);
    }

    @Override
    public void getSelfSelectStock() {
        if (UserInfoManager.getInstance().getLoginStatus()) {
            if (context != null && getSelfSelectStockPresenter != null) {
                showLoadDialog();
                getSelfSelectStockPresenter.getSelfSelectStock();
            }
        }
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MSelfSelectListModel) {
            if (((MSelfSelectListModel) baseModel).getData() == null
                    ||((MSelfSelectListModel) baseModel).getData().getStockList() == null
                    || ((MSelfSelectListModel) baseModel).getData().getStockList().isEmpty()) {
                blank.setVisibility(View.VISIBLE);
            } else {
                blank.setVisibility(View.GONE);
            }
            if (dataBeans != null && !dataBeans.isEmpty())
                dataBeans.clear();
            if (dataBeans != null && ((MSelfSelectListModel) baseModel).getData().getStockList() != null)
                dataBeans.addAll(((MSelfSelectListModel) baseModel).getData().getStockList());
            if (selfSelectAdapter != null) {
                selfSelectAdapter.updateData(dataBeans);
            }
        } else if (baseModel instanceof MSelectStockDeleteModel) {
            ToastWithIcon.init("删除自选成功").show();
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void onWholeClick(int position) {
        ARouter.getInstance().build(RouterConfig.stockDetailsActivity).withString("symbol", dataBeans.get(position).getStockSymbol())
                .withString("StockExchange", dataBeans.get(position).getStockExchange()).withBoolean("isSelf", dataBeans.get(position).isSelfSelect()).navigation();
    }

    @Override
    public void deleteSelfSelect(String stockExchange, String stockName, String stockSymbol) {
        deleteSelfSelectStockPresenter.deleteSelfSelect(stockExchange, stockName, stockSymbol);
    }
}
