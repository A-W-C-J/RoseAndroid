package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/13.
 */

public interface PathLoginPasswordContract {
  interface View extends BaseContract.View {
    void patchPassword(String password, String oldPassword);
  }

  interface Presenter extends BaseContract.Presenter {
    void patchPassword(String password, String oldPassword);
  }
}
