package com.rose.android.contract;

import android.support.annotation.Nullable;

/**
 * Created by xiaohuabu on 17/10/11.
 */

public interface GetWalletCashFlowListContract {
  interface View extends BaseContract.View {
    void getWalletCashFlowList(int walletFlowType, @Nullable Integer productOrderId,@Nullable String pageNo);

  }

  interface Presenter extends BaseContract.Presenter {
    void getWalletCashFlowList(int walletFlowType, @Nullable Integer productOrderId,@Nullable String pageNo);
  }
}
