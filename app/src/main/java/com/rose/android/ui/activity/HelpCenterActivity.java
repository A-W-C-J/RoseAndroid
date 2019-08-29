package com.rose.android.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.DialogClick;
import com.rose.android.view.ContractDetailItemView;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.UserVM;
import com.tbruyelle.rxpermissions.RxPermissions;


@Route(path = "/ui/helpCenterActivity")
public class HelpCenterActivity extends BaseActivity {
    private View base, trade, recharge;
    private ContractDetailItemView ciServiceOnline, ciServicePhone;
    private boolean isPhonePermissionGrant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_help_center);
        setContentView(getRootView());
        setTitle("帮助中心");
        initViews();
        initListener();
    }

    @Override
    public void initViews() {
        super.initViews();
        base = findViewById(R.id.base);
        trade = findViewById(R.id.trade);
        recharge = findViewById(R.id.out_in);
        ciServiceOnline = findViewById(R.id.ci_online);
        ciServicePhone = findViewById(R.id.ci_phone);
        ciServicePhone.setRightIconVisiable(true);
        ciServiceOnline.setRightIconVisiable(true);
        ciServicePhone.setRightText("交易日 9:00-18:00");
    }

    @Override
    public void initListener() {
        super.initListener();
        base.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.basicKnowledge)
                .withBoolean("hasHost", false).navigation());
        trade.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.stockTrade)
                .withBoolean("hasHost", false).navigation());
        recharge.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.drawout)
                .withBoolean("hasHost", false).navigation());
        ciServiceOnline.setOnClickListener(v -> {
            ConsultSource consultSource = new ConsultSource("com.yqz.yqz", UserVM.getInstance().getPhone(), UserVM.getInstance().getRealName());
            Unicorn.openServiceActivity(context, getString(R.string.app_name) + "客服", consultSource);
        });
        ciServicePhone.setOnClickListener(v -> {
            if (BuildConfig.ISTEST) {
                ToastWithIcon.init("此版本暂不支持该功能").show();
            } else
                RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        requestPhonePermission();
                        if (isPhonePermissionGrant) {
                            callPhone(getString(R.string.phone_num));
                        }
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, HelpCenterActivity.this.getString(R.string.sure), HelpCenterActivity.this.getString(R.string.cancel), HelpCenterActivity.this.getString(R.string.hint), HelpCenterActivity.this.getString(R.string.phone_num), null)
                        .show(HelpCenterActivity.this.getSupportFragmentManager(), "dialog");
        });
    }

    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    private void requestPhonePermission() {
        new RxPermissions(this)
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(aBoolean -> isPhonePermissionGrant = aBoolean);
    }
}
