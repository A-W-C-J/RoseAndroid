package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/19.
 */

public interface GetLimitedListContract {
  interface View extends BaseContract.View {
    void getLimitedList();
  }


  interface Presenter extends BaseContract.Presenter {
    void getLimitedList();
  }
}
