package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface UpdateUserInfoContract {
  interface View extends BaseContract.View {
    void updateUserInfo(String username, @Nullable String password, @Nullable String captcha, @Nullable String nickname);
  }

  interface Presenter extends BaseContract.Presenter {
    void updateUserInfo(String username, @Nullable String password, @Nullable String captcha, @Nullable String nickname);
  }

}
