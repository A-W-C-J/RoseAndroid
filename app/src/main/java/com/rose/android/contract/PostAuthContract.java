package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface PostAuthContract {
  interface View extends BaseContract.View {
    void postAuth(String realName, String idCardNo);
  }
  interface Presenter extends BaseContract.Presenter {
    void postAuth(String realName, String idCardNo);
  }
}
