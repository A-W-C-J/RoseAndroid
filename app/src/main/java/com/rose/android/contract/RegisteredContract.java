package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface RegisteredContract {
  interface View extends BaseContract.View {
    void registered(String username, String password);
  }

  interface Presenter extends BaseContract.Presenter {
    void registered(String username, String password, String captcha);
  }

}
