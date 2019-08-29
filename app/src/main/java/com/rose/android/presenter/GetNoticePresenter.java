package com.rose.android.presenter;

import com.rose.android.contract.GetNoticeContract;
import com.rose.android.model.MNoticeModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/12/6.
 */
public class GetNoticePresenter extends BasePresenter implements GetNoticeContract.Presenter {
  private GetNoticeContract.View view;
  private MNoticeModel noticeModel;

  public GetNoticePresenter(GetNoticeContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getNotice(int pageNo,int pageSize) {
    httpClient.getNotice(pageNo,pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MNoticeModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MNoticeModel mNoticeModel) {
            noticeModel = mNoticeModel;
            view.showNext(noticeModel, false, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError();
          }

          @Override
          public void onComplete() {
            view.requestCallBack(noticeModel);
          }
        });
  }

}
