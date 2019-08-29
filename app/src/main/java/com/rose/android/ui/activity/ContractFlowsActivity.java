package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.GetOrderDetailsContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.presenter.GetOrderDetailsPresenter;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.ui.fragment.ContractFlowsFragment;
import com.rose.android.ui.fragment.FlowsListFragment;
import com.rose.android.utils.Utils;
import com.rose.android.view.ContractDetailItemView;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

@Route(path = "/ui/contractFlowsActivity")
public class ContractFlowsActivity extends BaseActivity implements GetOrderDetailsContract.View {
  @Autowired
  public String orderName;
  @Autowired
  public int orderId;
  private GetOrderDetailsPresenter getOrderDetailsPresenter;
  private ContractDetailItemView title;
  private TextView info, tvGg, tvJk, tvTotal, tvCp;
  private ViewPager viewPager;
  private FixedIndicatorView indicator;
  private IndicatorViewPager indicatorViewPager;
  private ArrayList<Fragment> fragments;
  private View header;
  private String[] tabNames = {"流水明细", "管理费明细"};
  @Autowired
  public boolean hasHeader;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_contract_flows);
    setContentView(getRootView());
    setContentView(getRootView());
    setTitle("合约流水");
    getOrderDetailsPresenter = new GetOrderDetailsPresenter(this, userHttpClient);
    fragments = new ArrayList<>();
    initViews();
    initListener();
    getOrderDetails(orderId);
    fragments.add(ContractFlowsFragment.newInstance(String.valueOf(orderId), ""));
    fragments.add(FlowsListFragment.newInstance(String.valueOf(orderId), 3));
  }

  @Override
  public void initViews() {
    super.initViews();
    header = findViewById(R.id.header);
    if (hasHeader) {
      header.setVisibility(View.VISIBLE);
    } else {
      header.setVisibility(View.GONE);
    }
    title = findViewById(R.id.ci_title);
    info = findViewById(R.id.contract_info);
    tvCp = findViewById(R.id.tv_cp);
    tvGg = findViewById(R.id.tv_gg);
    tvJk = findViewById(R.id.tv_jk);
    tvTotal = findViewById(R.id.tv_total);
    viewPager = findViewById(R.id.vp_viewpager);
    indicator = findViewById(R.id.fxidv_indicator);
    indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
    View view = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
    view.findViewById(R.id.divider).setVisibility(View.GONE);
    indicator.setCenterView(view);
    ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
    colorBar.setWidth(200);
    indicator.setScrollBar(colorBar);
    indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
    indicatorViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),tabNames,context,fragments));
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
      info.setText(String.format("合约信息（%s）", orderDetailsModel.getData().getOrderNo()));
      tvGg.setText(String.format("杠杆本金  %s", Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getMargin()), 2)));
      tvCp.setText(String.format("操盘金额  %s", Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getAmount()), 2)));
      tvJk.setText(String.format("借款金额  %s", Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getLoan()), 2)));
      tvTotal.setText(String.format("合约金额  %s", Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getMargin() + orderDetailsModel.getData().getLoan()), 2)));
    }
    return super.requestCallBack(baseModel);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    viewPager = null;
    fragments = null;
    indicator = null;
    indicatorViewPager = null;
    if (getOrderDetailsPresenter != null) {
      getOrderDetailsPresenter.dispose();
      getOrderDetailsPresenter = null;
    }
  }
}
