package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.GetLimitedListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MLimitedBuyListModel;
import com.rose.android.presenter.GetLimitedListPresenter;
import com.rose.android.ui.adapter.LimitedBuyAdapter;
import com.rose.android.view.RoseDialog;

/**
 * Created by xiaohuabu on 17/9/18.
 */
@Route(path = "/ui/limitedBuyActivity")
public class LimitedBuyActivity extends BaseActivity implements GetLimitedListContract.View, ClickResponseListener {
  private RecyclerView recyclerView;
  private LimitedBuyAdapter limitedBuyAdapter;
  private GetLimitedListPresenter getLimitedListPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_limited_buy);
    setContentView(getRootView());
    setTitle("今日限买");
    getLimitedListPresenter = new GetLimitedListPresenter(this, userHttpClient);
    initViews();
    initListener();
    getLimitedList();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (getLimitedListPresenter != null) {
      getLimitedListPresenter.dispose();
      getLimitedListPresenter = null;
    }
  }

  @Override
  public void initViews() {
    super.initViews();
    recyclerView = (RecyclerView) findViewById(R.id.limited_list);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, OrientationHelper.VERTICAL, false);
    recyclerView.setLayoutManager(gridLayoutManager);
    limitedBuyAdapter = new LimitedBuyAdapter(null, context, this);
    recyclerView.setAdapter(limitedBuyAdapter);
  }

  @Override
  public void initListener() {
    super.initListener();
  }

  @Override
  public void getLimitedList() {
    showLoadDialog();
    getLimitedListPresenter.getLimitedList();
  }

  @Override
  public void onWholeClick(int position) {

  }

  @Override
  public void showError(String s) {
    super.showError(s);
    RoseDialog.newInstance(new DialogClick() {
      @Override
      public void doPositiveClick() {

      }

      @Override
      public void doNegativeClick() {

      }
    }, null, getString(R.string.cancel), "提示", s, null).show(getSupportFragmentManager(), "dialog");
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MLimitedBuyListModel) {
      limitedBuyAdapter.updateData(((MLimitedBuyListModel) baseModel).getData());
    }
    return super.requestCallBack(baseModel);
  }
}
