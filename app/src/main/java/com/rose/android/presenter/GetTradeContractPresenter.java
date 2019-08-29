package com.rose.android.presenter;

import com.rose.android.contract.GetTradeContractContract;
import com.rose.android.model.MTradeContractModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class GetTradeContractPresenter extends BasePresenter implements GetTradeContractContract.Presenter {
  private GetTradeContractContract.View view;
  private MTradeContractModel tradeContractModel;

  public GetTradeContractPresenter(GetTradeContractContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }


  @Override
  public void getTradeContract(int productItemId, long loan) {
    httpClient.getTradeContract(productItemId, loan).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MTradeContractModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MTradeContractModel mTradeContractModel) {
            tradeContractModel = mTradeContractModel;
            view.showNext(mTradeContractModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(tradeContractModel);
          }
        });
  }

}
