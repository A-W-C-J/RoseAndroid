package com.rose.android.presenter;

import com.rose.android.contract.GetAllCouponsContract;
import com.rose.android.model.MAllCouponsModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class GetAllCouponsPresenter extends BasePresenter implements GetAllCouponsContract.Presenter {
  private GetAllCouponsContract.View view;
  private MAllCouponsModel allCouponsModel;

  public GetAllCouponsPresenter(GetAllCouponsContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }


  @Override
  public void getAllCoupons() {
    httpClient.getAllCoupons().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MAllCouponsModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MAllCouponsModel mCoupons) {
        allCouponsModel = mCoupons;
        view.showNext(mCoupons, true, disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(allCouponsModel);
      }
    });
  }



}
