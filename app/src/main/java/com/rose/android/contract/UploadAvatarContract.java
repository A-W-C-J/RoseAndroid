package com.rose.android.contract;

import okhttp3.MultipartBody;

/**
 * Created by wenen on 2017/12/5.
 */

public interface UploadAvatarContract {
  interface View extends BaseContract.View {
    void upload(MultipartBody.Part image);
  }

  interface Presenter extends BaseContract.Presenter {
    void upload(MultipartBody.Part image);
  }
}
