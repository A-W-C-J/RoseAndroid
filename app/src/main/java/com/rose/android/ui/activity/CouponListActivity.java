package com.rose.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.CodeConfig;
import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetCouponsContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MCoupons;
import com.rose.android.presenter.GetCouponsPresenter;
import com.rose.android.ui.adapter.CouponListAdapter;
import com.rose.android.viewmodel.CouponListVM;

/**
 * Created by xiaohuabu on 17/9/13.
 */
@Route(path = "/ui/couponsListActivity")
public class CouponListActivity extends BaseActivity implements GetCouponsContract.View, ClickResponseListener {
  private RecyclerView recyclerView;
  private GetCouponsPresenter getCouponsPresenter;
  private CouponListAdapter couponListAdapter;
  @Autowired
  public String position;
  @Autowired
  public int productId;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ARouter.getInstance().inject(this);
    create(R.layout.activity_coupons_list);
    getCouponsPresenter = new GetCouponsPresenter(this, userHttpClient);
    setContentView(getRootView());
    setTitle("选择抵用券");
    initViews();
    initListener();
    getCoupons(productId);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (getCouponsPresenter != null) {
      getCouponsPresenter.dispose();
      getCouponsPresenter = null;
    }
  }

  @Override
  public void initListener() {
  }

  @Override
  public void initViews() {
    recyclerView = (RecyclerView) findViewById(R.id.rcl_coupons);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(linearLayoutManager);
    if (position == null) {
      couponListAdapter = new CouponListAdapter(recyclerView, null, this, context, null);
    } else {
      couponListAdapter = new CouponListAdapter(recyclerView, null, this, context, Integer.valueOf(position));
    }
    recyclerView.setAdapter(couponListAdapter);
  }

  @Override
  public void getCoupons(Integer productId) {
    showLoadDialog();
    getCouponsPresenter.getCoupons(productId);
  }

  @Override
  public void onWholeClick(int position) {
    View view = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.coupon_selected);
    Intent intent = new Intent();
    if (view.getVisibility() == View.VISIBLE) {
      view.setVisibility(View.GONE);
    } else {
      view.setVisibility(View.VISIBLE);
      intent.putExtra("position", String.valueOf(couponListAdapter.getCouponId(position)));
      intent.putExtra("selectPosition", String.valueOf(position));
    }
    setResult(CodeConfig.COUPONSCODE, intent);
    finish();
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MCoupons) {
      CouponListVM.getInstance().setCoupons((MCoupons) baseModel);
      couponListAdapter.updateData((MCoupons) baseModel);
    }
    return super.requestCallBack(baseModel);
  }

}
