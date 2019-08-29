package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/9.
 */

public interface AuthBankCardContract {
  interface View extends BaseContract.View {
    void postBankCard(String bankCardNo, String name, String phone);
  }

  interface Presenter extends BaseContract.Presenter {
    void postBankCard(String bankCardNo, String name, String phone);
  }
}
