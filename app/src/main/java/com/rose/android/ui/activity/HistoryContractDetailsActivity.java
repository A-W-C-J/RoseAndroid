package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by xiaohuabu on 17/9/22.
 */
@Route(path = "/ui/historyContractDetatisActivity")
public class HistoryContractDetailsActivity extends BaseActivity implements GetOrderDetailsContract.View {
  @Autowired
  public String orderName;
  @Autowired
  public int orderId;
  @Autowired
  int productOrderId;
  private GetOrderDetailsPresenter getOrderDetailsPresenter;
  private ContractDetailItemView title, zc, lj, js, gl, hy, ls;
  private TextView info, tvGg, tvJk, tvTotal, tvCp;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ARouter.getInstance().inject(this);
    create(R.layout.activity_history_contract_details);
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
    title = (ContractDetailItemView) findViewById(R.id.ci_title);
    zc = (ContractDetailItemView) findViewById(R.id.ci_zc);
    lj = (ContractDetailItemView) findViewById(R.id.ci_lj);
    js = (ContractDetailItemView) findViewById(R.id.ci_js);
    gl = (ContractDetailItemView) findViewById(R.id.ci_gl);
    hy = (ContractDetailItemView) findViewById(R.id.ci_hy);
    ls = (ContractDetailItemView) findViewById(R.id.ci_ls);
    info = (TextView) findViewById(R.id.contract_info);
    tvCp = (TextView) findViewById(R.id.tv_cp);
    tvGg = (TextView) findViewById(R.id.tv_gg);
    tvJk = (TextView) findViewById(R.id.tv_jk);
    tvTotal = (TextView) findViewById(R.id.tv_total);
  }

  @Override
  public void initListener() {
    super.initListener();
    ls.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.dealOrExchange).withInt("productOrderId", orderId).withInt("orderStatus", 30)
        .withString("title", "交易流水").navigation());
    hy.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.contractFlowsActivity).withBoolean("hasHeader", true)
        .withInt("orderId", orderId)
        .navigation());
  }

  @Override
  public void getOrderDetails(int orderId) {
    getOrderDetailsPresenter.getOrderDetails(orderId);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MOrderDetailsModel) {
      MOrderDetailsModel orderDetailsModel = (MOrderDetailsModel) baseModel;
      title.setTitle(orderDetailsModel.getData().getName());
      title.setRightText(orderDetailsModel.getData().getStartTradingDate() + "至" + orderDetailsModel.getData().getEndTradingDate());
      zc.setRightText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getAssetAmount()), 2) + "元");
      lj.setRightText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getSettleBenefit()), 2) + "元");
      js.setRightText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getSettleMargin()), 2) + "元");
      gl.setRightText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getSettleFee()), 2) + "元");
      hy.setRightIconVisiable(true);
      ls.setRightIconVisiable(true);
      info.setText("合约信息（" + orderDetailsModel.getData().getOrderNo() + "）");
      tvGg.setText("杠杆本金  " + Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getMargin()), 2));
      tvCp.setText("操盘金额  " + Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getAmount()), 2));
      tvJk.setText("借款金额  " + Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getLoan()), 2));
      tvTotal.setText("合约金额  " + Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getMargin() + orderDetailsModel.getData().getLoan()), 2));
    }
    return super.requestCallBack(baseModel);
  }
}
