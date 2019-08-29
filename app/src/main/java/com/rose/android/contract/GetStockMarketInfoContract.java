package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface GetStockMarketInfoContract {
  interface View extends BaseContract.View {
    void getStockMarketInfo(String symbol,@Nullable Integer productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void getStockMarketInfo(String symbol,@Nullable Integer productOrderId);
  }
}
