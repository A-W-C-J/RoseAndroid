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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.GetPartnerInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MPartnerInfoModel;
import com.rose.android.presenter.GetPartnerInfoPresenter;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.ui.fragment.FlowsListFragment;
import com.rose.android.ui.fragment.RegistListFragment;
import com.rose.android.utils.Utils;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

@Route(path = "/ui/shareDetailsActivity")
public class ShareDetailsActivity extends BaseActivity implements GetPartnerInfoContract.View {
  private GetPartnerInfoPresenter getPartnerInfoPresenter;
  private TextView people, cash;
  private ViewPager viewPager;
  private FixedIndicatorView indicator;
  private IndicatorViewPager indicatorViewPager;
  private ArrayList<Fragment> fragments;
  private String[] tabNames = {"用户明细", "佣金明细"};
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_share_details);
    setContentView(getRootView());
    setTitle("推广明细");
    getPartnerInfoPresenter = new GetPartnerInfoPresenter(userHttpClient, this);
    fragments = new ArrayList<>();
    fragments.add(RegistListFragment.newInstance("", ""));
    fragments.add(FlowsListFragment.newInstance(null, 4));
    initViews();
    indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
    indicatorViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),tabNames,context,fragments));
    initListener();
    getPartnerInfo();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (getPartnerInfoPresenter != null) {
      getPartnerInfoPresenter.dispose();
      getPartnerInfoPresenter = null;
    }
  }

  @Override
  public void initViews() {
    super.initViews();
    people = findViewById(R.id.people);
    cash = findViewById(R.id.cash);
    viewPager = findViewById(R.id.vp_viewpager);
    indicator = findViewById(R.id.fxidv_indicator);
    indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
    View view = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
    indicator.setCenterView(view);
    ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
    colorBar.setWidth(200);
    indicator.setScrollBar(colorBar);
  }

  @Override
  public void initListener() {
    super.initListener();
  }

  @Override
  public void getPartnerInfo() {
    getPartnerInfoPresenter.getPartnerInfo();
  }
  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MPartnerInfoModel) {
      MPartnerInfoModel partnerInfoModel = (MPartnerInfoModel) baseModel;
      people.setText(String.valueOf(partnerInfoModel.getData().getInviteUserCount()));
      cash.setText(Utils.formatWithScale(Utils.divide1000(partnerInfoModel.getData().getBrokerageAmount()), 2));
    }
    return super.requestCallBack(baseModel);
  }
}
