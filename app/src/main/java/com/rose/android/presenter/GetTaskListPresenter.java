package com.rose.android.presenter;

import com.rose.android.contract.GetTaskListContract;
import com.rose.android.model.MTaskModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/10/26.
 */
public class GetTaskListPresenter extends BasePresenter implements GetTaskListContract.Presenter {
  private GetTaskListContract.View view;
  private MTaskModel taskModel;

  public GetTaskListPresenter(HttpClient httpClient, GetTaskListContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getTaskList() {
    httpClient.getTaskList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MTaskModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MTaskModel mTaskModel) {
            taskModel = mTaskModel;
            view.showNext(mTaskModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(taskModel);
          }
        });
  }
}
