package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface GetBannerContract {
  interface View extends BaseContract.View {
    void getBanners();
  }

  interface Presenter extends BaseContract.Presenter {
    void getBanners();
  }
}
