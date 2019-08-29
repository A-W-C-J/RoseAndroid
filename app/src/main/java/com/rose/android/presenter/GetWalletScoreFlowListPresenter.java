package com.rose.android.presenter;

import android.support.annotation.Nullable;

import com.rose.android.contract.GetWalletScoreFlowListContract;
import com.rose.android.model.MWalletCashFlowListModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class GetWalletScoreFlowListPresenter extends BasePresenter implements GetWalletScoreFlowListContract.Presenter {
  private MWalletCashFlowListModel walletScoreFlowListModel;
  private GetWalletScoreFlowListContract.View view;

  public GetWalletScoreFlowListPresenter(HttpClient httpClient, GetWalletScoreFlowListContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }
  @Override
  public void getWalletScoreFlowList(int walletFlowType,@Nullable String pageNo) {
    httpClient.getWalletScoreFlowList(walletFlowType,pageNo).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MWalletCashFlowListModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MWalletCashFlowListModel mWalletCashFlowListModel) {
            walletScoreFlowListModel = mWalletCashFlowListModel;
            view.showNext(mWalletCashFlowListModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(walletScoreFlowListModel);
          }
        });
  }

}
