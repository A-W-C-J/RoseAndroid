package com.rose.android.contract;


/**
 * Created by xiaohuabu on 17/9/1.
 */

public interface GetProductListContract {
  interface View extends BaseContract.View {
    void getProductList(int type);
  }

  interface Presenter extends BaseContract.Presenter {
    void getProductList(int type);
  }
}
