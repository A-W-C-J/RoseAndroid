package com.rose.android.presenter;

import com.rose.android.contract.PostBrokerageWithdrawContract;
import com.rose.android.model.MBrokerageWithdrawModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/20.
 */
public class PostBrokerageWithdrawPresenter extends BasePresenter implements PostBrokerageWithdrawContract.Presenter {
  private PostBrokerageWithdrawContract.View view;
  private MBrokerageWithdrawModel brokerageWithdrawModel;

  public PostBrokerageWithdrawPresenter(HttpClient httpClient, PostBrokerageWithdrawContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void postBrokerageWithdraw() {
    httpClient.postBrokerageWithdraw().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .subscribe(new Observer<MBrokerageWithdrawModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MBrokerageWithdrawModel mBrokerageWithdrawModel) {
            brokerageWithdrawModel = mBrokerageWithdrawModel;
            view.showNext(mBrokerageWithdrawModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(brokerageWithdrawModel);
          }
        });
  }

}
