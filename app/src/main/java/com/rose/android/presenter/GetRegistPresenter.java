package com.rose.android.presenter;

import com.rose.android.contract.GetRegistListContract;
import com.rose.android.model.MRegistListModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/6.
 */
public class GetRegistPresenter extends BasePresenter implements GetRegistListContract.Presenter {
  private MRegistListModel registListModel;
  private GetRegistListContract.View view;

  public GetRegistPresenter(HttpClient httpClient, GetRegistListContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getRegistList(int pageNo, int pageSize) {
    httpClient.getRegistList(pageNo, pageSize).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MRegistListModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MRegistListModel mRegistListModel) {
            registListModel = mRegistListModel;
            view.showNext(mRegistListModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(registListModel);
          }
        });
  }
}
