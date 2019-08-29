package com.rose.android.ui.activity;

import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.GetOrderDetailsContract;
import com.rose.android.contract.PostSettleContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.model.MSettleModel;
import com.rose.android.presenter.GetOrderDetailsPresenter;
import com.rose.android.presenter.PostSettlePresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;

import java.math.BigDecimal;

/**
 * Created by xiaohuabu on 17/9/14.
 */
@Route(path = "/ui/orderDetailsActivity")
public class ContractDetailsDetailsActivity extends BaseActivity implements GetOrderDetailsContract.View, PostSettleContract.View {
  @Autowired
  public String orderName;
  @Autowired
  public int orderId;
  @Autowired
  public int selectPosition;
  @Autowired
  public boolean isActivity;
  private GetOrderDetailsPresenter getOrderDetailsPresenter;
  private PostSettlePresenter postSettlePresenter;
  private TextView upOrDown;
  private TextView percentage;
  private TextView margin;
  private TextView contractAmount;
  private TextView canUseMoney, loanAmount, cpAmount, useDays, hold, contractInfo, contractFlows, moreAction;
  private ImageView littleMan;
  private TextView limited, warning_line, stop_line;
  private TextView applicationSettlement;
  private Button commissionTransaction;
  private long availabelCash;
  private PopupWindow moreActionWindow;
  private View bootmBtn;
  private View mainContent;
  private View windowView;
  private TextView zoomOut, addMargin;
  private MOrderDetailsModel.DataBean orderDetailsModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ARouter.getInstance().inject(this);
    create(R.layout.activity_order_details);
    setContentView(getRootView());
    getRootView().setOnKeyListener((v, keyCode, event) -> {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
        if (moreActionWindow != null) {
          moreActionWindow.dismiss();
        }
      }
      return false;
    });
    getOrderDetailsPresenter = new GetOrderDetailsPresenter(this, userHttpClient);
    postSettlePresenter = new PostSettlePresenter(this, userHttpClient);
    if (orderName != null) {
      setTitle(orderName);
    }
    setRightTxt("刷新");
    setRightTxtClick(v -> getOrderDetails(orderId));
    initViews();
    initListener();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (getOrderDetailsPresenter != null) {
      getOrderDetailsPresenter.dispose();
      getOrderDetailsPresenter = null;
    }
    if (postSettlePresenter != null) {
      postSettlePresenter.dispose();
      postSettlePresenter = null;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    getOrderDetails(orderId);
  }

  @Override
  public void initListener() {
    limited.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.limitedBuyActivity).navigation());
    applicationSettlement.setOnClickListener(v -> RoseDialog.newInstance(new DialogClick() {
      @Override
      public void doPositiveClick() {
        postSettle(String.valueOf(orderId));
      }

      @Override
      public void doNegativeClick() {

      }
    }, getString(R.string.sure), getString(R.string.cancel), getString(R.string.hint), "确认结算当前合约？", null).show(getSupportFragmentManager(), "dialog"));
    commissionTransaction.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.main).withInt("position", 3).withInt("selectPosition", selectPosition).navigation());
    contractFlows.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.contractFlowsActivity)
        .withInt("orderId", orderId).withBoolean("hasHeader", false)
        .navigation());
    moreAction.setOnClickListener(v -> {
      int windowsPos[] = calculatePopWindowPos(moreAction, windowView);
      moreActionWindow.showAtLocation(mainContent, Gravity.TOP | Gravity.START, windowsPos[0], windowsPos[1]);
    });
    zoomOut.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.zoomoutActivity).withInt("orderId", orderId).navigation());
    addMargin.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.addmarginActivity).withInt("orderId", orderId).withLong("min", orderDetailsModel.getBalance()).navigation());
  }

  @SuppressWarnings("deprecation")
  @Override
  public void initViews() {
    bootmBtn = findViewById(R.id.bottom_btn);
    mainContent = findViewById(R.id.main_content);
    windowView = LayoutInflater.from(context).inflate(R.layout.more_action_window, null, false);
    zoomOut = windowView.findViewById(R.id.zoom_out);
    addMargin = windowView.findViewById(R.id.add_margin);
    moreActionWindow = new PopupWindow(windowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
    moreActionWindow.setOutsideTouchable(true);
    moreActionWindow.setBackgroundDrawable(new BitmapDrawable());
    limited = findViewById(R.id.tv_limited);
    applicationSettlement = findViewById(R.id.tv_application_settlement);
    commissionTransaction = findViewById(R.id.btn_commission_transaction);
    upOrDown = findViewById(R.id.tv_up_down);
    percentage = findViewById(R.id.percentage);
    margin = findViewById(R.id.margin);
    contractAmount = findViewById(R.id.tv_contract_amount);
    canUseMoney = findViewById(R.id.tv_can_use_money);
    littleMan = findViewById(R.id.iv_image);
    warning_line = findViewById(R.id.warning_line);
    stop_line = findViewById(R.id.stop_line);
    loanAmount = findViewById(R.id.tv_amount_loan);
    cpAmount = findViewById(R.id.tv_amount_cp);
    useDays = findViewById(R.id.tv_use_days);
    hold = findViewById(R.id.tv_hold);
    contractInfo = findViewById(R.id.contract_info);
    contractFlows = findViewById(R.id.contract_flows);
    contractFlows.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    moreAction = findViewById(R.id.more_action);
  }

  @Override
  public void getOrderDetails(int orderId) {
    getOrderDetailsPresenter.getOrderDetails(orderId);
  }

  @Override
  public void postSettle(String productOrderId) {
    showLoadDialog();
    postSettlePresenter.postSettle(productOrderId);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MOrderDetailsModel) {
      orderDetailsModel = ((MOrderDetailsModel) baseModel).getData();
      if (orderDetailsModel.getSettleBenefit() >= 0) {
        upOrDown.setText(String.format("+%s", Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getSettleBenefit()), 2)));
        upOrDown.setTextColor(ContextCompat.getColor(context, R.color.red));
        percentage.setText(String.format("+%s%%", Utils.formatWithScale(new BigDecimal(orderDetailsModel.getSettleBenefitPercent()).divide(new BigDecimal(100)), 2)));
        percentage.setTextColor(ContextCompat.getColor(context, R.color.red));
      } else {
        upOrDown.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getSettleBenefit()), 2));
        upOrDown.setTextColor(ContextCompat.getColor(context, R.color.green));
        percentage.setText(String.format("%s%%", Utils.formatWithScale(new BigDecimal(orderDetailsModel.getSettleBenefitPercent()).divide(new BigDecimal(100)), 2)));
        percentage.setTextColor(ContextCompat.getColor(context, R.color.green));
      }
      margin.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getMargin()), 2));
      contractAmount.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getAmount()), 2));
      canUseMoney.setText(orderDetailsModel.getTotalFeeText());
      loanAmount.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getLoan()), 2));
      cpAmount.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getBalance()), 2));
      useDays.setText(orderDetailsModel.getCreateTime().split(" ")[0]);
      hold.setText(orderDetailsModel.getProductPositionLimit().getDescription());
      contractInfo.setText("合约信息（" + orderDetailsModel.getOrderNo() + "）");
      if (orderDetailsModel.getAssetAmount() > orderDetailsModel.getWarningLine()) {
        ViewAnimator.animate(littleMan).translationX(0, -(Utils.getWidth() / 6 - littleMan.getWidth() / 2)).duration(2000).start();
      } else if (orderDetailsModel.getAssetAmount() <= orderDetailsModel.getWarningLine() && orderDetailsModel.getAssetAmount() > orderDetailsModel.getStopLine()) {
        ViewAnimator.animate(littleMan).translationX(0, -(Utils.getWidth() / 2 - littleMan.getWidth() / 2)).duration(3000).start();
      } else {
        ViewAnimator.animate(littleMan).translationX(0, -(Utils.getWidth() / 6 * 5 - littleMan.getWidth() / 2)).duration(3000).start();
      }
      contractFlows.setVisibility(View.VISIBLE);
      availabelCash = orderDetailsModel.getAvailabelCash();
      warning_line.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getWarningLine()), 2));
      stop_line.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getStopLine()), 2));
    } else if (baseModel instanceof MSettleModel) {
      ToastWithIcon.init("结算成功！").show();
      finish();
    }
    return super.requestCallBack(baseModel);
  }

  private int[] calculatePopWindowPos(View anchorView, View contentView) {
    final int windowPos[] = new int[2];
    final int anchorLoc[] = new int[2];
    anchorView.getLocationOnScreen(anchorLoc);
    final int anchorHeight = anchorView.getHeight();
    final int screenHeight = Utils.getScreenHeight(context);
    contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    final int windowHeight = contentView.getMeasuredHeight();
    final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
    if (isNeedShowUp) {
      windowPos[0] = -anchorView.getWidth();
      windowPos[1] = anchorLoc[1] - windowHeight;
    } else {
      windowPos[0] = -anchorView.getWidth();
      windowPos[1] = anchorLoc[1] + anchorHeight;
    }
    return windowPos;
  }
}
