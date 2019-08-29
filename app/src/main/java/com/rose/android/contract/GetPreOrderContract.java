package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public interface GetPreOrderContract {
  interface View extends BaseContract.View {
    void getPreOrder(int productItemId, long loan);
  }

  interface Presenter extends BaseContract.Presenter {
    void getPreOrder(int productItemId, long loan);
  }
}
