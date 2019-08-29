/**
 * 引导页
 *
 * @Title: GuideActivity.java
 * @Package com.luckin.magnifier.activity.guide
 * @Description: TODO 不要继承BaseActivity
 * @ClassName: GuideActivity
 * @author 于泽坤
 * @date 2015-7-6 下午5:15:30
 */

package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.CustomNavigationCallBack;
import com.rose.android.ui.adapter.GuideFragmentPagerAdapter;
import com.rose.android.ui.fragment.GuideFragment;

import java.util.ArrayList;

@Route(path = "/ui/guideActivity")
public class GuideActivity extends BaseActivity implements OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPage;
    private GuideFragmentPagerAdapter mViewPageAdapter;

    private ArrayList<Fragment> mFragments;
    private GuideFragment mGuideOneFragment;
    private GuideFragment mGuideTwoFragment;
    private GuideFragment mGuideThreeFragment;
    private GuideFragment mGuideFourFragment;

    // 底部小点图片
    private ImageView[] mBottomPoints;

    // 记录当前选中位置
    private int mCurrentTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        initDots();
        findViewById(R.id.btn_guide).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGuideOneFragment = null;
        mGuideTwoFragment = null;
        mGuideThreeFragment = null;
        mGuideFourFragment = null;
    }

    @Override
    public void initViews() {
        mGuideOneFragment = new GuideFragment();
        mGuideTwoFragment = new GuideFragment();
        mGuideThreeFragment = new GuideFragment();
        mGuideFourFragment = new GuideFragment();
        if (BuildConfig.ISTEST) {
            mGuideOneFragment.updateImage(R.mipmap.welcome_bg1_test, false);
        } else {
            mGuideOneFragment.updateImage(R.mipmap.welcome_bg1, false);
        }
        mGuideTwoFragment.updateImage(R.mipmap.welcome_bg2, false);
        mGuideThreeFragment.updateImage(R.mipmap.welcome_bg3, false);
        mGuideFourFragment.updateImage(R.mipmap.welcome_bg4, false);

        mGuideOneFragment.updateSpeedText(R.string.guide_one_speed, R.string.guide_speed_one);
        mGuideTwoFragment.updateSpeedText(R.string.guide_two_speed, R.string.guide_speed_two);
        mGuideThreeFragment.updateSpeedText(R.string.guide_three_speed, R.string.guide_speed_three);
        mGuideFourFragment.updateSpeedText(R.string.guide_four_speed, R.string.guide_speed_four);
        mFragments = new ArrayList<>();
        mFragments.add(mGuideOneFragment);
        mFragments.add(mGuideTwoFragment);
        mFragments.add(mGuideThreeFragment);
        mFragments.add(mGuideFourFragment);
        // 初始化Adapter
        mViewPage = (ViewPager) findViewById(R.id.viewpager);
        mViewPageAdapter = new GuideFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPage.setAdapter(mViewPageAdapter);
        // 绑定回调
        mViewPage.addOnPageChangeListener(this);
        mViewPage.setOffscreenPageLimit(4);
        mViewPage.setCurrentItem(mCurrentTabIndex);
        mCurrentTabIndex = 0;
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.guide_bottom_ll);
        mBottomPoints = new ImageView[mFragments.size()];

        // 循环取得小点图片
        for (int i = 0; i < mFragments.size(); i++) {
            mBottomPoints[i] = (ImageView) ll.getChildAt(i);
            mBottomPoints[i].setEnabled(true);// 都设为灰色
        }

        mCurrentTabIndex = 0;
        mBottomPoints[mCurrentTabIndex].setEnabled(false);// 设置为红色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > mFragments.size() - 1 || mCurrentTabIndex == position) {
            return;
        }
        mBottomPoints[position].setEnabled(false);
        mBottomPoints[mCurrentTabIndex].setEnabled(true);
        mCurrentTabIndex = position;
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurrentDot(position);
        if (position == 3) {
            findViewById(R.id.btn_guide).setVisibility(View.VISIBLE);
            findViewById(R.id.skip).setVisibility(View.GONE);
        } else {
            findViewById(R.id.btn_guide).setVisibility(View.GONE);
            findViewById(R.id.skip).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //do nothing
    }

    @Override
    public void onClick(View v) {
        storeData();
        gotoMain(0);
    }

    private void storeData() {
        GuideActivity.this.getSharedPreferences("sp", MODE_PRIVATE).edit().putBoolean("LoginStatus", true).apply();
    }

    private void gotoMain(int position) {
        ARouter.getInstance().build(RouterConfig.main).withInt("position", position).navigation(context, new CustomNavigationCallBack(GuideActivity.this, true));
    }
}
