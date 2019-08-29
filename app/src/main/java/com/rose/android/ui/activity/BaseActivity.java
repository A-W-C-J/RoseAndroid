package com.rose.android.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.BaseContract;
import com.rose.android.contract.DialogClick;
import com.rose.android.elegate.IDelegate;
import com.rose.android.model.BaseModel;
import com.rose.android.network.HttpClient;
import com.rose.android.view.LoadProgressDialog;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.LoginVM;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tendcloud.tenddata.TCAgent;

import java.util.Timer;
import java.util.TimerTask;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.disposables.Disposable;
import rx.functions.Action1;

/**
 * Created by xiaohuabu on 17/8/29.
 */

@SuppressFBWarnings({"URF_UNREAD_FIELD", "URF_UNREAD_FIELD"})
public class BaseActivity extends BaseShareableActivity implements IDelegate, BaseContract.View, DialogClick, NavigationCallback {
    public View view;
    public Context context;
    public Toolbar toolbar;
    public static final String TAG = "BaseActivity";
    private LoadProgressDialog loadProgressDialog;
    private Timer mTimer;
    protected HttpClient userHttpClient;
    protected HttpClient hostHttpClient;
    protected SharedPreferences.Editor spEditor;
    protected SharedPreferences sp;
    protected RefreshLayout refreshLayout;
    private int code;
    private int layoutId;
    public boolean isPermissionGrant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ARouter.getInstance().inject(this);
        userHttpClient = HttpClient.Builder.getUserService(getApplicationContext());
        hostHttpClient = HttpClient.Builder.getHostService(getApplicationContext());
        sp = context.getSharedPreferences("sp", MODE_PRIVATE);
        spEditor = sp.edit();
        if (isPermissionGrant)
            requestStoragePermission();
    }

    @Override
    public void create(int layoutId, ViewGroup v, Bundle b) {
        this.layoutId = layoutId;
        view = getLayoutInflater().inflate(layoutId, v, false);
        if (view != null && view.findViewById(R.id.toolbar) != null) {
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        if (view != null && view.findViewById(R.id.smart_refresh_layout) != null) {
            refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        }
    }

    @Override
    public void create(int layoutId) {
        this.layoutId = layoutId;
        view = getLayoutInflater().inflate(layoutId, null, false);
        if (view != null && view.findViewById(R.id.toolbar) != null) {
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void create(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.layoutId = layoutId;
    }

    @Override
    public android.view.View getRootView() {
        setArrowBackClick(v -> onBackPressed());
        return view;
    }

    private void requestStoragePermission() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        isPermissionGrant = aBoolean;
                    }
                });
    }

    @Override
    public void showLoadDialog() {
        if (loadProgressDialog != null && loadProgressDialog.isShowing()) {
            loadProgressDialog.dismiss();
            loadProgressDialog.show();
        } else {
            loadProgressDialog = new LoadProgressDialog(context);
            loadProgressDialog.show();
        }
    }

    @Override
    public void dismissLoadDialog() {
        if (loadProgressDialog != null && loadProgressDialog.isShowing()) {
            loadProgressDialog.dismiss();
        }
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        dismissLoadDialog();
        return null;
    }

    @Override
    public void showError(String s, View.OnClickListener listener) {
        dismissLoadDialog();
        ToastWithIcon.init(s).show();
        releaseRefreshView();
    }

    @Override
    public void showError() {
        dismissLoadDialog();
        ToastWithIcon.init("请稍后重试！").show();
        releaseRefreshView();
    }

    @Override
    public void showError(String s) {
        dismissLoadDialog();
        ToastWithIcon.init(s).show();
        releaseRefreshView();
    }

    @Override
    public void onCancel() {
        dismissLoadDialog();
        releaseRefreshView();
    }

    @Override
    public void showError(Throwable throwable) {
        dismissLoadDialog();
        ToastWithIcon.init("请稍后重试！").show();
        Log.e(TAG, "showError: " + throwable.toString());
        releaseRefreshView();
    }

    @Override
    public void showReLogin(boolean url) {
        dismissLoadDialog();
        releaseRefreshView();
        UserInfoManager.getInstance().setToken(null);
        UserInfoManager.getInstance().setLoginStatus(false);
        LoginVM.getInstance().setLoginModel(null);
        RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {
                ARouter.getInstance().build(RouterConfig.userSignin).withBoolean("routerUrl", url).navigation();
            }

            @Override
            public void doNegativeClick() {

            }
        }, "去登录", getString(R.string.cancel), getString(R.string.hint), "请先登录", null);
        getSupportFragmentManager().executePendingTransactions();
        if (!roseDialog.isAdded()) {
            roseDialog.show(getSupportFragmentManager(), "reLogin");
        }

    }

    @Override
    public void showRecharge() {
        dismissLoadDialog();
        releaseRefreshView();
        RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {
                ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.recharge)
                        .withBoolean("hasHost", false).navigation(context, BaseActivity.this);
            }

            @Override
            public void doNegativeClick() {

            }
        }, "去充值", getString(R.string.cancel), getString(R.string.hint), "余额不足", null);
        roseDialog.show(getSupportFragmentManager(), "reLogin");
    }

    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {

    }

    @Override
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {

    }

    @Override
    public void showAuthName(String msg) {
        dismissLoadDialog();
        releaseRefreshView();
        RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {
                ARouter.getInstance().build(RouterConfig.authRealNameActivity).navigation();
            }

            @Override
            public void doNegativeClick() {

            }
        }, "去实名", getString(R.string.cancel), getString(R.string.hint), msg, null);
        roseDialog.show(getSupportFragmentManager(), "reLogin");
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void showAuthError(String msg) {
        dismissLoadDialog();
        releaseRefreshView();
        RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {
                finish();
//        ARouter.getInstance().build(RouterConfig.authRealNameActivity).navigation();
            }

            @Override
            public void doNegativeClick() {

            }
        }, getString(R.string.sure), null, getString(R.string.hint), msg, null);
        roseDialog.show(getSupportFragmentManager(), "reLogin");
    }

    @Override
    public void showNext(BaseModel baseModel, boolean next, Disposable disposable) {
        if (baseModel != null && (code = baseModel.getCode()) == 200) {
        } else {
            disposable.dispose();
            if (code == 100) {
                showReLogin(next);
            } else if (code == 424) {
                showRecharge();
            } else if (code == 313) {
                showAuthName(baseModel.getMsg());
            } else if (baseModel != null) {
                showError(baseModel.getMsg());
            }
        }
    }

    private void releaseRefreshView() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadmore(false);
            refreshLayout.finishRefresh(false);
        }
    }

    public void showNotCompleted() {
        RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {

            }

            @Override
            public void doNegativeClick() {

            }
        }, getString(R.string.sure), null, getString(R.string.hint), "亲，此功能正在开发中~", null);
        roseDialog.show(getSupportFragmentManager(), "dialog");
    }

    public void setArrowBackClick(View.OnClickListener listener) {
        if (toolbar != null) {
            toolbar.findViewById(R.id.title_bar_left).setOnClickListener(listener);
        }
    }

    public void setRightIconClick(View.OnClickListener listener) {
        if (toolbar != null) {
            toolbar.findViewById(R.id.parent_right).setOnClickListener(listener);
        }
    }

    public void setRightTxtClick(View.OnClickListener listener) {
        if (toolbar != null) {
            toolbar.findViewById(R.id.title_bar_right_txt).setOnClickListener(listener);
        }
    }

    public void setRightTxt(String txt) {
        if (toolbar != null) {
            if (txt != null) {
                ((TextView) toolbar.findViewById(R.id.title_bar_right_txt)).setText(txt);
                ((TextView) toolbar.findViewById(R.id.title_bar_right_txt)).setVisibility(View.VISIBLE);
            } else {
                ((TextView) toolbar.findViewById(R.id.title_bar_right_txt)).setVisibility(View.GONE);
            }
        }
    }

    public void setTitle(String title) {
        if (toolbar != null) {
            ((TextView) toolbar.findViewById(R.id.title_bar_title)).setText(title);
        }
    }

    public void setArrowBackIsVisiable(boolean b) {
        if (toolbar != null) {
            if (b) {
                toolbar.findViewById(R.id.title_bar_left).setVisibility(View.VISIBLE);
            } else {
                toolbar.findViewById(R.id.title_bar_left).setVisibility(View.GONE);
            }
        }
    }

    public void setRightIcon(@Nullable int id, boolean b) {
        if (toolbar != null) {
            if (b) {
                ((ImageView) toolbar.findViewById(R.id.title_bar_right_icon)).setVisibility(View.VISIBLE);
                ((ImageView) toolbar.findViewById(R.id.title_bar_right_icon)).setImageResource(id);
            } else {
                ((ImageView) toolbar.findViewById(R.id.title_bar_right_icon)).setVisibility(View.GONE);
            }

        }
    }

    protected void startScheduleJob(final Handler handler, long delay, long interval) {
        if (mTimer != null) {
            cancelTimer();
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (handler != null) {
                    handler.sendEmptyMessage(0);
                }
            }
        }, delay, interval);
    }

    protected void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadDialog();
        cancelTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (toolbar != null) {
            TCAgent.onPageStart(context, ((TextView) toolbar.findViewById(R.id.title_bar_title)).getText().toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (toolbar != null) {
            TCAgent.onPageEnd(context, ((TextView) toolbar.findViewById(R.id.title_bar_title)).getText().toString());
        }
    }

    @Override
    public void doPositiveClick() {

    }

    @Override
    public void doNegativeClick() {

    }
}
