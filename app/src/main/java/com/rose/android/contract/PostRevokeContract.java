package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/21.
 */

public interface PostRevokeContract {
  interface View extends BaseContract.View {
    void postRevoke(String stockOrderNo, String productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void postRevoke(String stockOrderNo, String productOrderId);
  }
}
