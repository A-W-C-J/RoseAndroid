package com.rose.android.presenter;

import android.support.annotation.Nullable;

import com.rose.android.contract.GetWalletCashFlowListContract;
import com.rose.android.model.MWalletCashFlowListModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/11.
 */
public class GetWalletCashFlowListPresenter extends BasePresenter implements GetWalletCashFlowListContract.Presenter {
  private MWalletCashFlowListModel walletCashFlowListModel;
  private GetWalletCashFlowListContract.View view;

  public GetWalletCashFlowListPresenter(HttpClient httpClient, GetWalletCashFlowListContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getWalletCashFlowList(int walletFlowType, @Nullable Integer productOrderId,@Nullable String pageNo) {
    httpClient.getWalletCashFlowList(walletFlowType, productOrderId,pageNo).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MWalletCashFlowListModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MWalletCashFlowListModel mWalletCashFlowListModel) {
            walletCashFlowListModel = mWalletCashFlowListModel;
            view.showNext(mWalletCashFlowListModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(walletCashFlowListModel);
          }
        });
  }
}
