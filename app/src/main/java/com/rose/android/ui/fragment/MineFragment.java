package com.rose.android.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.newstruct.UserInfoModel;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.ui.activity.UserInfoSettingActivity;
import com.rose.android.ui.activity.newstruct.UserSigninActivity;
import com.rose.android.utils.GlideApp;
import com.rose.android.view.CircleImageView;
import com.rose.android.view.MineItemView;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.LoginVM;
import com.rose.android.viewmodel.UserVM;

import org.apache.commons.lang3.StringUtils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class MineFragment extends BaseFragment implements UserInfoContract.View {
    private Button loginBtn;
    private MineItemView taskCenter;
    private View hasLoginView;
    private View noLoginView;
    private CircleImageView imageView;
    private TextView userName;
    private TextView balance;
    private LinearLayout setting;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private UserInfoPresenter userInfoPresenter;
    private MineItemView ciCash, ciScore, ciCoupon, ciHelp, ciAbout, ciZC, ciShare, ciHistory;
    private Button withdrawalBtn, rechargeBtn;

    public MineFragment() {
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        create(R.layout.fragment_mine);
        View view = getRootView();
        setContentView(view);
        userInfoPresenter = new UserInfoPresenter(userHttpClient, this);
        initViews(view);
        initListener();
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (userInfoPresenter != null) {
            userInfoPresenter.dispose();
            userInfoPresenter = null;
        }
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
        if (LoginVM.getInstance().getLoginModel() != null) {
            hasLoginView.setVisibility(View.VISIBLE);
            noLoginView.setVisibility(View.GONE);
            GlideApp.with(context).load(LoginVM.getInstance().getHeadUrl()).into(imageView);
            userName.setText(LoginVM.getInstance().getPhone());
            balance.setText(LoginVM.getInstance().getCashAsset());
        } else {
            hasLoginView.setVisibility(View.GONE);
            noLoginView.setVisibility(View.VISIBLE);
        }
        if (UserInfoManager.getInstance().getToken() != null) {
            requestUserInfo();
        }
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
    }

    @Override
    public void initViews(View view) {
        loginBtn = (Button) view.findViewById(R.id.btn_login);
        taskCenter = (MineItemView) view.findViewById(R.id.task_center);
        taskCenter.setTagIconShow(View.VISIBLE);
        imageView = (CircleImageView) view.findViewById(R.id.iv_image);
        userName = (TextView) view.findViewById(R.id.tv_username);
        balance = (TextView) view.findViewById(R.id.tv_balance);
        hasLoginView = view.findViewById(R.id.ll_has_login);
        noLoginView = view.findViewById(R.id.rl_no_login);
        setting = (LinearLayout) view.findViewById(R.id.ll_setting);
        ciCash = (MineItemView) view.findViewById(R.id.ci_cash);
        ciScore = (MineItemView) view.findViewById(R.id.ci_score);
        ciCoupon = (MineItemView) view.findViewById(R.id.ci_coupon);
        withdrawalBtn = (Button) view.findViewById(R.id.withdrawal);
        rechargeBtn = (Button) view.findViewById(R.id.recharge);
        ciHelp = view.findViewById(R.id.help_center);
        ciAbout = view.findViewById(R.id.about);
        ciZC = view.findViewById(R.id.ci_zc);
        ciShare = view.findViewById(R.id.share_profile);
        ciShare.setTagIconShow(View.VISIBLE);
        ciHistory = view.findViewById(R.id.ci_history);
    }

    @Override
    public void initListener() {
        ciShare.setOnClickListener(v -> {
            if (BuildConfig.ISTEST) {
                ToastWithIcon.init("此版本暂不支持该功能").show();
            } else {
                ARouter.getInstance().build(RouterConfig.sharePartnerActivity).navigation();
            }
        });
        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserSigninActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MineFragment.this.getActivity(), loginBtn, "login_btn");
            context.startActivity(intent, optionsCompat.toBundle());
        });
        setting.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserInfoSettingActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MineFragment.this.getActivity(), imageView, "profile");
            context.startActivity(intent, optionsCompat.toBundle());
        });
        ciCash.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.cashActivity).navigation());
        ciScore.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.scoreActivity).navigation());
        ciCoupon.setOnClickListener(v -> ARouter.getInstance()
                .build(RouterConfig.allCouponListActivity)
                .withLong("scoreValue", UserVM.getInstance().getScoreAsset())
                .navigation());
        withdrawalBtn.setOnClickListener(v -> {
            if ("未绑定".equals(UserVM.getInstance().getBankCard())) {
                RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        ARouter.getInstance().build(RouterConfig.authBankCardActivity).withBoolean("hasBankCard", false).navigation();
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, "去绑定", getString(R.string.cancel), getString(R.string.hint), "请先绑定银行卡", null).show(getFragmentManager(), "dialog");
            } else {
                ARouter.getInstance().build(RouterConfig.withdrawalActivity).navigation();
            }
        });
        ciHelp.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.helpCenterActivity).navigation());
        ciAbout.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.aboutActivity).navigation());
        ciZC.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.assetTotalActivity).navigation());
        rechargeBtn.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.recharge)
                .withBoolean("hasHost", false).navigation());
        taskCenter.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.taskCenterActivity).navigation());
        ciHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterConfig.historyContractActivity).navigation();
            }
        });
    }

    public void requestUserInfo() {
        showLoadDialog();
        userInfoPresenter.requestUserInfo();
        userInfoPresenter.requestUserAccount();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPauseLazy();
        }
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof UserInfoModel) {
            UserInfoModel userInfoModel = (UserInfoModel) baseModel;
            hasLoginView.setVisibility(View.VISIBLE);
            noLoginView.setVisibility(View.GONE);

            String avatarUri = null;
            if (null != userInfoModel.getData() && null != userInfoModel.getData().getUser()) {
                avatarUri = userInfoModel.getData().getUser().getAvatar_uri();
            }

            if (StringUtils.isNotEmpty(avatarUri) && !avatarUri.equals(UserVM.getInstance().getHeadUrl())) {
                GlideApp.with(context).load(avatarUri).into(imageView);
            }
            userName.setText(UserVM.getInstance().getPhone());
            balance.setText(UserVM.getInstance().getCashAsset());
        }
        return super.requestCallBack(baseModel);
    }

    public void updateAvatar() {
        GlideApp.with(context).load(UserVM.getInstance().getHeadUrl()).into(imageView);
    }
}
