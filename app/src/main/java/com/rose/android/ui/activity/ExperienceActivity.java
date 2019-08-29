package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.rose.android.contract.GetProductListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MProductListModel;
import com.rose.android.presenter.GetProductListPresenter;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.ui.fragment.CurContractFragment;
import com.rose.android.ui.fragment.JoinExperienceFragment;
import com.rose.android.viewmodel.ActivityProductListVM;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

/**
 * Created by xiaohuabu on 17/9/14.
 */
@Route(path = "/ui/experienceActivity")
public class ExperienceActivity extends BaseActivity implements GetProductListContract.View {
    private ViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fragments;
    private GetProductListPresenter getProductListPresenter;
    private TextView peopleCount;
    private JoinExperienceFragment joinExperienceFragment;
    private String[] tabNames = {"参与体验", "我的体验"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_experience);
        setContentView(getRootView());
        setTitle("新手体验");
        fragments = new ArrayList<>();
        joinExperienceFragment = JoinExperienceFragment.newInstance("", "");
        fragments.add(joinExperienceFragment);
        fragments.add(CurContractFragment.newInstance("PRODUCT_CATEGORY_TYPE_ACTIVITY", null));
        initViews();
        initListener();
        getProductListPresenter = new GetProductListPresenter(userHttpClient, this);
        getProductList(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getProductListPresenter != null) {
            getProductListPresenter.dispose();
            getProductListPresenter = null;
        }
        joinExperienceFragment = null;
        fragments = null;
    }

    @Override
    public void initViews() {
        viewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        indicator = (FixedIndicatorView) findViewById(R.id.fxidv_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
        View view = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
        indicator.setCenterView(view);
        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
        colorBar.setWidth(200);
        indicator.setScrollBar(colorBar);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabNames, context, fragments, true));
        peopleCount = (TextView) findViewById(R.id.tv_people_count);
    }

    public void setPeopleCount(String count) {
        peopleCount.setText(count + " 人");
    }

    public void changState(int i) {
        if (joinExperienceFragment != null) {
            joinExperienceFragment.changState(i);
        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void getProductList(int type) {
        showLoadDialog();
        getProductListPresenter.getProductList(type);
    }

    public void setSelectItem(int position) {
        indicatorViewPager.setCurrentItem(position, true);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MProductListModel) {
            ActivityProductListVM.getInstance().setProductListModel((MProductListModel) baseModel);
            indicatorViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabNames, context, fragments));
        }
        return super.requestCallBack(baseModel);
    }
}
