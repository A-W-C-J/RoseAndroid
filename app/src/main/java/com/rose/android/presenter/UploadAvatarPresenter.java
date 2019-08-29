package com.rose.android.presenter;

import com.rose.android.contract.UploadAvatarContract;
import com.rose.android.model.MUploadAvatarModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * Created by wenen on 2017/12/5.
 */

public class UploadAvatarPresenter extends BasePresenter implements UploadAvatarContract.Presenter {
  private MUploadAvatarModel uploadAvatarModel;
  private UploadAvatarContract.View view;

  public UploadAvatarPresenter(HttpClient httpClient, UploadAvatarContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }
  @Override
  public void upload(MultipartBody.Part image) {
    httpClient.uploadAvatar(image).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MUploadAvatarModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MUploadAvatarModel mUploadAvatarModel) {
            uploadAvatarModel = mUploadAvatarModel;
            view.showNext(uploadAvatarModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(uploadAvatarModel);
          }
        });
  }
}
