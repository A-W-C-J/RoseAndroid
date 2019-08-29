package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetWalletScoreFlowListContract;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MWalletCashFlowListModel;
import com.rose.android.model.MWalletModel;
import com.rose.android.presenter.GetWalletScoreFlowListPresenter;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.ui.adapter.WalletCashFlowListAdapter;
import com.rose.android.utils.Utils;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.Locale;

@Route(path = "/ui/scoreActivity", extras = 1)
public class ScoreActivity extends BaseActivity implements ClickResponseListener, UserInfoContract.View, GetWalletScoreFlowListContract.View {
    private UserInfoPresenter getWalletPresenter;
    private TextView score;
    private RecyclerView recyclerView;
    private GetWalletScoreFlowListPresenter getWalletCashFlowListPresenter;
    private RefreshLayout refreshLayout;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private WalletCashFlowListAdapter walletCashFlowListAdapter;
    private MWalletCashFlowListModel walletCashFlowListModel;
    private View blank;
    @Autowired
    int walletFlowType;
    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_score);
        setContentView(getRootView());
        getWalletPresenter = new UserInfoPresenter(userHttpClient, this);
        getWalletCashFlowListPresenter = new GetWalletScoreFlowListPresenter(userHttpClient, this);
        setTitle("积分账户");
        initViews();
        initListener();
        requestUserAccount();
        getWalletScoreFlowList(walletFlowType, String.valueOf(pageNo));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getWalletPresenter != null) {
            getWalletPresenter.dispose();
            getWalletPresenter = null;
        }
        if (getWalletCashFlowListPresenter != null) {
            getWalletCashFlowListPresenter.dispose();
            getWalletCashFlowListPresenter = null;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        score = findViewById(R.id.tv_score);
        recyclerView = findViewById(R.id.rcl_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshLayout = findViewById(R.id.smart_refresh_layout);
        recyclerView.setLayoutManager(linearLayoutManager);
        walletCashFlowListAdapter = new WalletCashFlowListAdapter(this, context, walletCashFlowListModel);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(walletCashFlowListAdapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        blank = view.findViewById(R.id.blank);
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            pageNo = 1;
            getWalletScoreFlowList(walletFlowType, String.valueOf(pageNo));
        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            ++pageNo;
            getWalletScoreFlowList(walletFlowType, String.valueOf(pageNo));

        });
    }


    public void requestUserAccount() {
        getWalletPresenter.requestUserAccount();
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        // TODO: 20/11/2018 积分账户暂时回避屏蔽，不用处理 
        if (baseModel instanceof MWalletModel) {
            MWalletModel walletModel = (MWalletModel) baseModel;
            score.setText(String.format(Locale.getDefault(), "%d", Utils.divide1000(walletModel.getData().getScoreAsset()).longValue()));
        } else if (baseModel instanceof MWalletCashFlowListModel) {
            if (pageNo > 1) {
                walletCashFlowListModel.getData().getUserWalletCashFlowList().addAll(((MWalletCashFlowListModel) baseModel).getData().getUserWalletCashFlowList());
                refreshLayout.finishLoadmore(true);
            } else {
                walletCashFlowListModel = (MWalletCashFlowListModel) baseModel;
                refreshLayout.finishRefresh(true);
            }
            refreshLayout.finishRefresh();
            if (walletCashFlowListModel.getData().getUserWalletCashFlowList().isEmpty()) {
                blank.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                blank.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            walletCashFlowListAdapter.updateData(walletCashFlowListModel);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void getWalletScoreFlowList(int walletFlowType, @Nullable String pageNo) {
        getWalletCashFlowListPresenter.getWalletScoreFlowList(walletFlowType, String.valueOf(pageNo));
    }

    @Override
    public void onWholeClick(int position) {
    }
}
