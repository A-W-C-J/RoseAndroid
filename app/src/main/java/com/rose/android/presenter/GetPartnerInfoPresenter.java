package com.rose.android.presenter;

import com.rose.android.contract.GetPartnerInfoContract;
import com.rose.android.model.MPartnerInfoModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/6.
 */
public class GetPartnerInfoPresenter extends BasePresenter implements GetPartnerInfoContract.Presenter {
  private MPartnerInfoModel partnerInfoModel;
  private GetPartnerInfoContract.View view;

  public GetPartnerInfoPresenter(HttpClient httpClient, GetPartnerInfoContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getPartnerInfo() {
    httpClient.getPartnerInfo().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MPartnerInfoModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MPartnerInfoModel mPartnerInfoModel) {
            partnerInfoModel = mPartnerInfoModel;
            view.showNext(mPartnerInfoModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(partnerInfoModel);
          }
        });
  }

}
