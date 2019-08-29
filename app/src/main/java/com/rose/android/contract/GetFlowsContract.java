package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/27.
 */

public interface GetFlowsContract {
  interface View extends BaseContract.View {
    void getFlows(int pageNo, int pageSize, String productOrderId, Integer timeStatus);
  }

  interface Presenter extends BaseContract.Presenter {
    void getFlows(int pageNo, int pageSize, String productOrderId, Integer timeStatus);
  }
}
