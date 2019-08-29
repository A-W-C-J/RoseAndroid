package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/9/12.
 */

public interface GetOrdersContract {
  interface View extends BaseContract.View {
    void getOrders(Integer pageNo, Integer pageSize, String status, String type,@Nullable String symbol);
  }

  interface Presenter extends BaseContract.Presenter {
    void getOrders(Integer pageNo, Integer pageSize, String status, String type,@Nullable String symbol);
  }
}
