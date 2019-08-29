package com.rose.android.presenter;

import com.rose.android.contract.PostGuaguaContract;
import com.rose.android.model.MGuaguaModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/12/9.
 */
public class PostGuaguaPresenter extends BasePresenter implements PostGuaguaContract.Presenter {
  private PostGuaguaContract.View view;
  private MGuaguaModel guaguaModel;

  public PostGuaguaPresenter(PostGuaguaContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postGuagua(Integer type, Long amount, Integer source) {
    httpClient.postGuagua(type, amount, source).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MGuaguaModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable=d;
          }

          @Override
          public void onNext(MGuaguaModel mGuaguaModel) {
            guaguaModel = mGuaguaModel;
            view.showNext(guaguaModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(guaguaModel);
          }
        });
  }

}
