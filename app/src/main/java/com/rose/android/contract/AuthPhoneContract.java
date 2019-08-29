package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/13.
 */

public interface AuthPhoneContract {
  interface View extends BaseContract.View {
    void pathPhone(String phone, String password, String captcha);
  }

  interface Presenter extends BaseContract.Presenter {
    void pathPhone(String phone, String password, String captcha);
  }
}
