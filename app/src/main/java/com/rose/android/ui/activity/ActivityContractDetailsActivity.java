package com.rose.android.ui.activity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.GetOrderDetailsContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.presenter.GetOrderDetailsPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.ContractDetailItemView;
import com.rose.android.view.SuperCircleView;

/**
 * Created by xiaohuabu on 17/9/16.
 */
@Route(path = "/ui/ActivityContractDetailsActivity")
public class ActivityContractDetailsActivity extends BaseActivity implements GetOrderDetailsContract.View {
  @Autowired
  public String orderName;
  @Autowired
  public int orderId;
  @Autowired
  public int isActivity;
  @Autowired
  int productOrderId;
  private GetOrderDetailsPresenter getOrderDetailsPresenter;
  private SuperCircleView mSuperCircleView;
  private TextView textView;
  private ContractDetailItemView title;
  private ContractDetailItemView lj;
  private ContractDetailItemView gg;
  private ContractDetailItemView hy;
  private ContractDetailItemView ly;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ARouter.getInstance().inject(this);
    create(R.layout.activity_activity_order_details);
    setContentView(getRootView());
    if (orderName != null) {
      setTitle(orderName);
    }
    getOrderDetailsPresenter = new GetOrderDetailsPresenter(this, userHttpClient);
    initViews();
    initListener();
    getOrderDetails(orderId);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (getOrderDetailsPresenter != null) {
      getOrderDetailsPresenter.dispose();
      getOrderDetailsPresenter = null;
    }
  }

  @Override
  public void initViews() {
    super.initViews();
    textView = (TextView) findViewById(R.id.tv_percent);
    mSuperCircleView = (SuperCircleView) findViewById(R.id.supercircleview);
    mSuperCircleView.setShowSelect(false);
    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
    valueAnimator.setTarget(textView);
    valueAnimator.setDuration(2000);
    valueAnimator.addUpdateListener(animation -> {
      int i = Integer.parseInt(String.valueOf(animation.getAnimatedValue()));
      mSuperCircleView.setSelect((int) (360 * (i / 100f)));
    });
    valueAnimator.start();
    title = (ContractDetailItemView) findViewById(R.id.ci_title);
    lj = (ContractDetailItemView) findViewById(R.id.ci_lj);
    gg = (ContractDetailItemView) findViewById(R.id.ci_gg);
    hy = (ContractDetailItemView) findViewById(R.id.ci_hy);
    ly = (ContractDetailItemView) findViewById(R.id.ci_ls);
  }

  @Override
  public void initListener() {
    super.initListener();
    ly.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.dealOrExchange).withInt("productOrderId", orderId).withInt("orderStatus", 30)
        .withString("title", "交易流水").navigation());
  }

  @Override
  public void getOrderDetails(int orderId) {
    getOrderDetailsPresenter.getOrderDetails(orderId);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    int color[] = new int[3];
    if (baseModel instanceof MOrderDetailsModel) {
      MOrderDetailsModel.DataBean orderDetailsModel = ((MOrderDetailsModel) baseModel).getData();
      if (orderDetailsModel.getSettleBenefit() >= 0) {
        textView.setTextColor(ContextCompat.getColor(context, R.color.red));
        textView.setText(String.format("+%s%%", Utils.formatWithScale(orderDetailsModel.getSettleBenefitPercent() / 100.00, 2)));
        color[0] = Color.parseColor("#D03838");
        color[1] = Color.parseColor("#D03838");
        color[2] = Color.parseColor("#D03838");
        mSuperCircleView.setColor(color);
      } else {
        textView.setTextColor(ContextCompat.getColor(context, R.color.green));
        textView.setText(String.format("%s%%", Utils.formatWithScale(orderDetailsModel.getSettleBenefitPercent() / 100.00, 2)));
        color[0] = Color.parseColor("#03BE9E");
        color[1] = Color.parseColor("#03BE9E");
        color[2] = Color.parseColor("#03BE9E");
        mSuperCircleView.setColor(color);
      }
      title.setRightText(orderDetailsModel.getStartTradingDate() + "至" + orderDetailsModel.getEndTradingDate());
      title.setTitle(orderDetailsModel.getName());
      lj.setRightText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getSettleBenefit()), 2) + "元");
      lj.setIconVisiable(true);
      gg.setRightText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getMargin()), 2) + "元");
      gg.setIconVisiable(true);
      hy.setRightText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getAmount()), 2) + "元");
      ly.setRightIconVisiable(true);
    }
    return super.requestCallBack(baseModel);
  }
}
