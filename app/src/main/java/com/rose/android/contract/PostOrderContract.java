package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public interface PostOrderContract {
  interface View extends BaseContract.View {
    void postOrder(int productItemId, long loan,@Nullable  Integer coupon_id);
  }

  interface Presenter extends BaseContract.Presenter {
    void postOrder(int productItemId, long loan,@Nullable  Integer coupon_id);
  }
}
