package com.rose.android.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.CustomNavigationCallBack;
import com.rose.android.contract.HostContract;
import com.rose.android.contract.LoginContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MHostModel;
import com.rose.android.model.MLoginModel;
import com.rose.android.network.HttpClient;
import com.rose.android.presenter.HostPresenter;
import com.rose.android.presenter.LoginPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.ToastWithIcon;

import io.reactivex.annotations.NonNull;

public class SplashActivity extends BaseActivity implements LoginContract.View, HostContract.View {
    private LoginPresenter loginPresenter;
    private HostPresenter hostPresenter;
    private String token;
    private static final String SP_PWD_KEY = "password";
    private static final String SP_PHONE_KEY = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.ISTEST) {
            setTheme(R.style.SplashTheme_test);
        } else
            setTheme(R.style.SplashTheme);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            loginPresenter = new LoginPresenter(userHttpClient, this);
            hostPresenter = new HostPresenter(hostHttpClient, this);
            getHost();
            token = sp.getString("token", null);
            if (token != null) {
                login(sp.getString(SP_PHONE_KEY, ""), Utils.parseStrToMd5L32(sp.getString(SP_PWD_KEY, "")));
            } else {
                if (!sp.getBoolean("LoginStatus", false)) {
                    ARouter.getInstance().build(RouterConfig.guideActivity).navigation(this, this);
                } else {
                    ARouter.getInstance().build(RouterConfig.main).withInt("position", 0).navigation(this, this);
                }
            }
        } else {
            ToastWithIcon.init("请授予相关权限，否则APP将无法正常启动");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresenter != null) {
            loginPresenter.dispose();
            loginPresenter = null;
        }
        if (hostPresenter != null) {
            hostPresenter.dispose();
            hostPresenter = null;
        }
    }

    @Override
    public void login(String username, String password) {
        loginPresenter.login(username, password);
    }

    @Override
    public void showError() {
        gotoMain(0);
    }

    @Override
    public void showError(String s) {
        gotoMain(0);
    }

    @Override
    public void showError(Throwable throwable) {
        gotoMain(0);
    }

    @Override
    public void showError(String s, View.OnClickListener listener) {
        gotoMain(0);
    }

    private void storeInfo(String phoneNum, String pwd, String token) {
        spEditor.putString(SP_PHONE_KEY, phoneNum);
        spEditor.putString(SP_PWD_KEY, pwd);
        spEditor.putString("token", token);
        spEditor.commit();
    }

    private void gotoMain(int position) {
        if (token != null) {
            ARouter.getInstance().build(RouterConfig.main).withInt("position", position).navigation(this, new CustomNavigationCallBack(this, true));
        }
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MLoginModel) {
            storeInfo(sp.getString(SP_PHONE_KEY, ""), sp.getString(SP_PWD_KEY, ""), ((MLoginModel) baseModel).getData().getToken());
            gotoMain(4);
        } else if (baseModel instanceof MHostModel) {
            MHostModel hostModel = (MHostModel) baseModel;
            spEditor.putString("protocal", hostModel.getProtocal());
            spEditor.putString("domain", hostModel.getDomain());
            spEditor.commit();
            super.userHttpClient = HttpClient.Builder.getUserService(context);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void getHost() {
        hostPresenter.getHost();
    }

}
