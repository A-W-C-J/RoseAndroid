package com.rose.android.presenter;

import com.rose.android.contract.GetFlowsContract;
import com.rose.android.model.MFlowsModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/27.
 */
public class GetFlowsPresenter extends BasePresenter implements GetFlowsContract.Presenter {
  private GetFlowsContract.View view;
  private MFlowsModel flowsModel;

  public GetFlowsPresenter(HttpClient httpClient, GetFlowsContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getFlows(int pageNo, int pageSize, String productOrderId, Integer timeStatus) {
    httpClient.getFlows(pageNo, pageSize, productOrderId, timeStatus).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MFlowsModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MFlowsModel mFlowsModel) {
            flowsModel = mFlowsModel;
            view.showNext(mFlowsModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(flowsModel);
          }
        });
  }
}
