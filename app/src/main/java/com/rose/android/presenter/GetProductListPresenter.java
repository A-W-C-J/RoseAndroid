package com.rose.android.presenter;

import com.rose.android.contract.GetProductListContract;
import com.rose.android.model.MProductListModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/6.
 */
public class GetProductListPresenter extends BasePresenter implements GetProductListContract.Presenter {
  private GetProductListContract.View view;
  private MProductListModel mProductListModel;

  public GetProductListPresenter(HttpClient httpClient, GetProductListContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getProductList(int type) {
    httpClient.getProductList(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MProductListModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MProductListModel productListModel) {
        mProductListModel = productListModel;
        view.showNext(productListModel, true, disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(mProductListModel);
      }
    });
  }
}
