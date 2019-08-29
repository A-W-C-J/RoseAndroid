package com.rose.android.contract;


import com.rose.android.model.BaseModel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiaohuabu on 17/8/29.
 */

public interface BaseContract {
  interface View {
    BaseModel requestCallBack(BaseModel baseModel);

    void showError(String s, android.view.View.OnClickListener listener);

    void showError();

    void showError(String s);

    void onCancel();

    void showError(Throwable throwable);

    void showReLogin(boolean routerUrl);

    void showRecharge();

    void showAuthName(String msg);

    void initViews();

    void initViews(android.view.View view);

    void initListener();

    void showAuthError(String msg);

    void showNext(BaseModel baseModel, boolean next, Disposable disposable);
  }
  interface Presenter {
    void dispose();
  }
}
