package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/21.
 */

public interface PostStockOrderContract {
  interface View extends BaseContract.View {
    void postStockOrder(int totalQuantity, String symbole, String action, long limitPrice, int productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void postStockOrder(int totalQuantity, String symbole, String action, long limitPrice, int productOrderId);
  }
}
