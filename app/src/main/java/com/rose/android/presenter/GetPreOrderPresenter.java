package com.rose.android.presenter;

import android.util.Log;

import com.rose.android.contract.GetPreOrderContract;
import com.rose.android.model.MPreOrderModel;
import com.rose.android.network.HttpClient;
import com.rose.android.viewmodel.PreOrderVM;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/11.
 */
public class GetPreOrderPresenter extends BasePresenter implements GetPreOrderContract.Presenter {
  private GetPreOrderContract.View view;
  private MPreOrderModel preOrderModel;
  private static final String TAG = "ApplicationContractPres";
  public GetPreOrderPresenter(GetPreOrderContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getPreOrder(int productItemId, long loan) {
    httpClient.getPreOrder(productItemId, loan).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MPreOrderModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MPreOrderModel mPreOrderModel) {
            preOrderModel = mPreOrderModel;
            view.showNext(mPreOrderModel, true, disposable);
          }
          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            Log.e(TAG, "onComplete: ");
            PreOrderVM.getInstance().setPreOrderModel(preOrderModel);
            view.requestCallBack(preOrderModel);
          }
        });
  }
}
