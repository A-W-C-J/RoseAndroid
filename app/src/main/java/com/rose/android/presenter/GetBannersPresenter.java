package com.rose.android.presenter;

import com.rose.android.contract.GetBannerContract;
import com.rose.android.model.MBannersModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class GetBannersPresenter extends BasePresenter implements GetBannerContract.Presenter {
  private GetBannerContract.View view;
  private MBannersModel bannersModel;

  public GetBannersPresenter(HttpClient httpClient, GetBannerContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }
  @Override
  public void getBanners() {
    httpClient.getBanners().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MBannersModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MBannersModel mBannersModel) {
        bannersModel=mBannersModel;
        view.showNext(mBannersModel,false,disposable);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {
        view.requestCallBack(bannersModel);
      }
    });
  }

}
