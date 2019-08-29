package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetAllCouponsContract;
import com.rose.android.contract.GetCouponsContract;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.PostCouponsContract;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAllCouponsModel;
import com.rose.android.model.MCoupons;
import com.rose.android.model.MExchangeCouponModel;
import com.rose.android.model.MWalletModel;
import com.rose.android.presenter.GetAllCouponsPresenter;
import com.rose.android.presenter.GetCouponsPresenter;
import com.rose.android.presenter.PostCouponsPresenter;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.ui.adapter.AllCouponListAdapter;
import com.rose.android.ui.adapter.CouponListAdapter;
import com.rose.android.utils.Utils;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.CouponListVM;

@Route(path = "/ui/allCouponListActivity", extras = 1)
public class AllCouponListActivity extends BaseActivity implements GetCouponsContract.View, ClickResponseListener, UserInfoContract.View
        , GetAllCouponsContract.View, PostCouponsContract.View {
    private RecyclerView recyclerView;
    private GetCouponsPresenter getCouponsPresenter;
    private GetAllCouponsPresenter getAllCouponsPresenter;
    private PostCouponsPresenter postCouponsPresenter;
    private CouponListAdapter couponListAdapter;
    private AllCouponListAdapter allCouponListAdapter;
    private RecyclerView allRecyclerView;
    private MAllCouponsModel allCouponsModel;
    private TextView score;
    @Autowired
    long scoreValue;
    private MCoupons coupons;
    private UserInfoPresenter userInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_all_coupon_list);
        getCouponsPresenter = new GetCouponsPresenter(this, userHttpClient);
        userInfoPresenter = new UserInfoPresenter(userHttpClient, this);
        getAllCouponsPresenter = new GetAllCouponsPresenter(this, userHttpClient);
        postCouponsPresenter = new PostCouponsPresenter(this, userHttpClient);
        setContentView(getRootView());
        setTitle("积分卡券");
        initViews();
        initListener();
        getCoupons(null);
        getAllCoupons();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getCouponsPresenter != null) {
            getCouponsPresenter.dispose();
            getCouponsPresenter = null;
        }
        if (userInfoPresenter != null) {
            userInfoPresenter.dispose();
            userInfoPresenter = null;
        }
        if (getAllCouponsPresenter != null) {
            getAllCouponsPresenter.dispose();
            getAllCouponsPresenter = null;
        }
        if (postCouponsPresenter != null) {
            postCouponsPresenter.dispose();
            postCouponsPresenter = null;
        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initViews() {
        coupons = new MCoupons();
        recyclerView = (RecyclerView) findViewById(R.id.rcl_coupons);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        couponListAdapter = new CouponListAdapter(recyclerView, coupons, this, context, null);
        recyclerView.setAdapter(couponListAdapter);
        allRecyclerView = (RecyclerView) findViewById(R.id.rcl_all_coupons);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        allRecyclerView.setLayoutManager(linearLayoutManager2);
        allCouponListAdapter =
                new AllCouponListAdapter(allRecyclerView, null, position -> RoseDialog.newInstance(new DialogClick() {
                                                                                                       @Override
                                                                                                       public void doPositiveClick() {
                                                                                                           AllCouponListActivity.this.postCoupons(allCouponListAdapter.getCouponId(position));
                                                                                                       }

                                                                                                       @Override
                                                                                                       public void doNegativeClick() {

                                                                                                       }
                                                                                                   }, getString(R.string.sure), getString(R.string.cancel), "提示",
                        "确认消耗" + Utils.formatWithScale(Utils.divide1000(allCouponListAdapter.getCouponScore(position)), 2) + "积分进行兑换", null)
                        .show(getSupportFragmentManager(), "dialog"), context, null);
        allRecyclerView.setAdapter(allCouponListAdapter);
        score = (TextView) findViewById(R.id.score);
        score.setText(Utils.formatWithScale(Utils.divide1000(scoreValue), 0));
    }

    @Override
    public void getCoupons(Integer productId) {
        showLoadDialog();
        getCouponsPresenter.getCoupons(productId);
    }

    @Override
    public void getAllCoupons() {
        showLoadDialog();
        getAllCouponsPresenter.getAllCoupons();
    }

    @Override
    public void postCoupons(int couponId) {
        postCouponsPresenter.postCoupons(couponId);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MCoupons) {
            CouponListVM.getInstance().setCoupons((MCoupons) baseModel);
            coupons.setData(((MCoupons) baseModel).getData());
            couponListAdapter.updateData(coupons);
            Log.e(TAG, "requestCallBack: ");
        } else if (baseModel instanceof MAllCouponsModel) {
            allCouponsModel = (MAllCouponsModel) baseModel;
            allCouponListAdapter.updateData(allCouponsModel);
        } else if (baseModel instanceof MExchangeCouponModel) {
            ToastWithIcon.init("兑换成功").show();
            getCoupons(null);
            requestUserAccount();
        } else if (baseModel instanceof MWalletModel) {
            score.setText(Utils.formatWithScale(Utils.divide1000(((MWalletModel) baseModel).getData().getScoreAsset()), 0));
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void onWholeClick(int position) {

    }

    public void requestUserAccount() {
        userInfoPresenter.requestUserAccount();
    }
}
