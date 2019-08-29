package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/12.
 */

public interface PostWithdrawalContract {
  interface View extends BaseContract.View {
    void postWithdrawal(long amount, String pwd);
  }

  interface Presenter extends BaseContract.Presenter {
    void postWithdrawal(long amount, String pwd);
  }
}
