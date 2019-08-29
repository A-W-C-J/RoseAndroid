package com.rose.android.presenter;

import com.rose.android.contract.PostWithdrawalContract;
import com.rose.android.model.MWithdrawlModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/12.
 */
public class PostWithdrawalPresenter extends BasePresenter implements PostWithdrawalContract.Presenter {
  private MWithdrawlModel withdrawlModel;
  private PostWithdrawalContract.View view;

  public PostWithdrawalPresenter(HttpClient httpClient, PostWithdrawalContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void postWithdrawal(long amount, String pwd) {
    httpClient.postWithdrawl(amount, pwd).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MWithdrawlModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MWithdrawlModel mWithdrawlModel) {
            withdrawlModel = mWithdrawlModel;
            view.showNext(mWithdrawlModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(withdrawlModel);
          }
        });
  }
}
