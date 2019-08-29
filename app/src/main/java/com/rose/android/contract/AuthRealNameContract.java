package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/30.
 */

public interface AuthRealNameContract {
  interface View extends BaseContract.View {
    void postRealName(String realName,String idCardNo);
  }

  interface Presenter extends BaseContract.Presenter {
    void postRealName(String realName,String idCardNo);
  }
}
