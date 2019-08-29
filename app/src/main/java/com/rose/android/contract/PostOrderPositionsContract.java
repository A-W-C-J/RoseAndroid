package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/22.
 */

public interface PostOrderPositionsContract {
  interface View extends BaseContract.View {
    void postOrderPositions(int pageNo,int pageSize,String productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void postOrderPositions(int pageNo,int pageSize,String productOrderId);
  }
}
