package com.rose.android.contract;

import android.support.annotation.Nullable;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface GetWalletScoreFlowListContract {
  interface View extends BaseContract.View {

    void getWalletScoreFlowList(int walletFlowType,@Nullable String pageNo);
  }

  interface Presenter extends BaseContract.Presenter {

    void getWalletScoreFlowList(int walletFlowType,@Nullable String pageNo);
  }
}
