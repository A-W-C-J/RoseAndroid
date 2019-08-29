package com.rose.android.contract;


/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface GetAuthCodeContract {
  interface View extends BaseContract.View {
    void getAuthCode(String phone, String type);

  }

  interface Presenter extends BaseContract.Presenter {
    void getAuthCode(String phone, String type);
  }

}
