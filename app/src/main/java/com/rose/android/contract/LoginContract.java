package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/5.
 */

public interface LoginContract {
  interface View extends BaseContract.View {
    void login(String username, String password);

  }

  interface Presenter extends BaseContract.Presenter {
    void login(String username, String password);
  }
}
