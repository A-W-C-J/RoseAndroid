package com.rose.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RoseApplication;
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
import com.shizhefei.fragment.LazyFragment;

import java.util.Timer;
import java.util.TimerTask;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiaohuabu on 17/9/1.
 */
@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class BaseFragment extends LazyFragment implements BaseContract.View, IDelegate {
    public View view;
    public Context context;
    private LoadProgressDialog loadProgressDialog;
    protected HttpClient userHttpClient;
    protected RefreshLayout refreshLayout;
    private static final String TAG = "BaseFragment";
    private int code;
    private Timer mTimer;

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
        if (this.isAdded() && this.getActivity() != null) {
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
            getFragmentManager().executePendingTransactions();
            if (!roseDialog.isAdded()) {
                roseDialog.show(getFragmentManager(), "reLogin");
            }
        }
    }

    @Override
    public void showRecharge() {
        if (this.isAdded() && this.getActivity() != null) {
            dismissLoadDialog();
            releaseRefreshView();
            RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
                @Override
                public void doPositiveClick() {
                    ToastWithIcon.init("亲，此功能正在开发中~").show();
                }

                @Override
                public void doNegativeClick() {

                }
            }, "去充值", getString(R.string.cancel), getString(R.string.hint), "余额不足", null);
            roseDialog.show(getFragmentManager(), "reLogin");
        }
    }

    @Override
    public void showAuthName(String msg) {
        if (this.isAdded() && this.getActivity() != null) {
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
            roseDialog.show(getFragmentManager(), "reLogin");
        }
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
        {
            if (this.isAdded() && this.getActivity() != null) {
                dismissLoadDialog();
                releaseRefreshView();
                RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        getActivity().finish();
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, getString(R.string.sure), null, getString(R.string.hint), msg, null);
                roseDialog.show(getFragmentManager(), "reLogin");
            }
        }
    }

    private void releaseRefreshView() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadmore(false);
            refreshLayout.finishRefresh(false);
        }
    }

    @Override
    public void showNext(BaseModel baseModel, boolean next, Disposable disposable) {
        if (baseModel != null && (code = baseModel.getCode()) == 200) {
        } else {
            Log.e(TAG, "showNext: " + code);
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

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        context = this.getActivity();
        userHttpClient = HttpClient.Builder.getUserService(RoseApplication.getAppContext());
        super.onCreateViewLazy(savedInstanceState);
    }

    @Override
    public void create(int layoutId, ViewGroup v, Bundle b) {
    }

    @Override
    public void create(int layoutId) {
        view = inflater.inflate(layoutId, null, false);
        if (view != null && view.findViewById(R.id.smart_refresh_layout) != null) {
            refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        }
    }

    @Override
    public void create(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layoutId, container, false);
        if (view != null && view.findViewById(R.id.smart_refresh_layout) != null) {
            refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        }
    }

    @Override
    public View getRootView() {
        return view;
    }

    @Override
    public void showLoadDialog() {
        Log.e(TAG, "showLoadDialog: "+ this.getClass().getName());
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

    public void showNotCompleted() {
        RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {
            }

            @Override
            public void doNegativeClick() {
            }
        }, getString(R.string.sure), null, getString(R.string.hint), "亲，此功能正在开发中~", null);
        roseDialog.show(getFragmentManager(), "dialog");
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
}
