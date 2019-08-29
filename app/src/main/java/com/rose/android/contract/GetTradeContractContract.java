package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface GetTradeContractContract {
  interface View extends BaseContract.View {
    void getTradeContract(int productItemId, long loan);
  }

  interface Presenter extends BaseContract.Presenter {
    void getTradeContract(int productItemId, long loan);
  }
}
