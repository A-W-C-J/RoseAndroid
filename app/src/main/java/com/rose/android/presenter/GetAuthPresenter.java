package com.rose.android.presenter;

import com.rose.android.contract.GetAuthContract;
import com.rose.android.model.MAuthModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class GetAuthPresenter extends BasePresenter implements GetAuthContract.Presenter {
  private GetAuthContract.View view;
  private MAuthModel authModel;

  public GetAuthPresenter(GetAuthContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getAuth() {
    httpClient.getAuth().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MAuthModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MAuthModel mAuthModel) {
            authModel = mAuthModel;
            view.showNext(mAuthModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(authModel);
          }
        });
  }

}
