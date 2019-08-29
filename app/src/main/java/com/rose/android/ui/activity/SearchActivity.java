package com.rose.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.BroadcastAction;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.DeleteSelfSelectStockContract;
import com.rose.android.contract.GetStockListContract;
import com.rose.android.contract.PostSelfSelectStockContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MSelectStockDeleteModel;
import com.rose.android.model.MSelfSelectStockModel;
import com.rose.android.model.MStockListModel;
import com.rose.android.presenter.DeleteSelfSelectStockPresenter;
import com.rose.android.presenter.GetStockListPresenter;
import com.rose.android.presenter.PostSelfSelectStockPresenter;
import com.rose.android.ui.adapter.StockListAdapter;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements GetStockListContract.View, ClickResponseListener, PostSelfSelectStockContract.View, DeleteSelfSelectStockContract.View {
    private android.support.v7.widget.SearchView searchView;
    private TextView cancel;
    private GetStockListPresenter getStockListPresenter;
    private int pageNo = 1, pageSize = 10;
    private StockListAdapter stockListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MStockListModel.DataBean.StockListBean> stockListBeans;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private String key;
    private PostSelfSelectStockPresenter postSelfSelectStockPresenter;
    private DeleteSelfSelectStockPresenter deleteSelfSelectStockPresenter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_search);
        View view = getRootView();
        setContentView(view);
        getStockListPresenter = new GetStockListPresenter(this, userHttpClient);
        postSelfSelectStockPresenter = new PostSelfSelectStockPresenter(userHttpClient, this);
        deleteSelfSelectStockPresenter = new DeleteSelfSelectStockPresenter(userHttpClient, this);
        initViews();
        initListener();
    }

    @Override
    public void initViews() {
        super.initViews();
        searchView = findViewById(R.id.sv_search);
        if (searchView == null) {
            return;
        }
        cancel = findViewById(R.id.tv_cancel);
        searchView.setIconifiedByDefault(false);
        ImageView icon = searchView.findViewById(R.id.search_mag_icon);
        ViewGroup linearLayoutSearchView = (ViewGroup) icon.getParent();
        linearLayoutSearchView.removeView(icon);
        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(context, R.color.title_color_3)); // set the text color
        searchEditText.setHintTextColor(ContextCompat.getColor(context, R.color.title_color_2)); // set the hint color
        searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
                .getDimension(R.dimen.detail_size));
        searchEditText.setGravity(Gravity.CENTER_VERTICAL);
        searchEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key = s.toString();
                pageNo = 1;
                getStockList(key, pageNo, pageSize);
            }
        });
        recyclerView = findViewById(R.id.list);
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        stockListBeans = new ArrayList<>();
        stockListAdapter = new StockListAdapter(recyclerView, stockListBeans, this, new ClickResponseListener() {
            @Override
            public void onWholeClick(int position) {
                SearchActivity.this.position = position;
                if (stockListBeans.get(position).isSelfSelect()) {
                    deleteSelfSelect(stockListBeans.get(position).getStockExchange(), stockListBeans.get(position).getStockName(),
                            stockListBeans.get(position).getStockSymbol());
                } else
                    postSelfSelect(stockListBeans.get(position).getStockExchange(), stockListBeans.get(position).getStockName(),
                            stockListBeans.get(position).getStockSymbol());
            }
        });
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(stockListAdapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        cancel.setOnClickListener(v -> onBackPressed());
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            pageNo = 1;
            getStockList(key, pageNo, pageSize);
        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            pageNo++;
            getStockList(key, pageNo, pageSize);
        });
    }

    @Override
    public void getStockList(String key, @NonNull Integer pageNo, @Nullable Integer pageSize) {
        getStockListPresenter.getStockList(key, pageNo, pageSize);
    }

    @Override
    public void onWholeClick(int position) {
        ARouter.getInstance().build(RouterConfig.stockDetailsActivity).withString("symbol", stockListBeans.get(position).getStockSymbol())
                .withString("StockExchange", stockListBeans.get(position).getStockExchange()).withBoolean("isSelf", stockListBeans.get(position).isSelfSelect()).navigation();
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        Intent intent = new Intent(BroadcastAction.UPDATE_SELF_SELCET);
        if (baseModel instanceof MStockListModel) {
            if (stockListAdapter != null) {
                if (pageNo > 1) {
                    refreshLayout.finishLoadmore(true);
                    stockListBeans.addAll(((MStockListModel) baseModel).getData().getStockList());
                } else {
                    refreshLayout.finishRefresh(true);
                    stockListBeans = (ArrayList<MStockListModel.DataBean.StockListBean>) ((MStockListModel) baseModel).getData().getStockList();
                }
                stockListAdapter.updateData(stockListBeans);
            }
        } else if (baseModel instanceof MSelfSelectStockModel) {
            ToastWithIcon.init("添加自选成功").show();
            stockListBeans.get(position).setSelfSelect(true);
            if (stockListAdapter != null) {
                stockListAdapter.updateData(stockListBeans);
            }
            sendBroadcast(intent);
        } else if (baseModel instanceof MSelectStockDeleteModel) {
            ToastWithIcon.init("删除自选成功").show();
            stockListBeans.get(position).setSelfSelect(false);
            if (stockListAdapter != null) {
                stockListAdapter.updateData(stockListBeans);
            }
            sendBroadcast(intent);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void postSelfSelect(String stockExchange, String stockName, String stockSymbol) {
        showLoadDialog();
        postSelfSelectStockPresenter.postSelfSelect(stockExchange, stockName, stockSymbol);
    }

    @Override
    public void deleteSelfSelect(String stockExchange, String stockName, String stockSymbol) {
        showLoadDialog();
        deleteSelfSelectStockPresenter.deleteSelfSelect(stockExchange, stockName, stockSymbol);
    }
}
