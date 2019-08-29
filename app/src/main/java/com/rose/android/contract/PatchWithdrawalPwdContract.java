package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/10/13.
 */

public interface PatchWithdrawalPwdContract {
  interface View extends BaseContract.View {
    void patchPwd(String password, @Nullable String oldPassword,@Nullable String phone,@Nullable String captcha);
  }

  interface Presenter extends BaseContract.Presenter {
    void patchPwd(String password, @Nullable String oldPassword,@Nullable String phone,@Nullable String captcha);
  }
}
