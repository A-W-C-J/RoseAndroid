package com.rose.android.presenter;

import com.rose.android.contract.PostStockOrderListContract;
import com.rose.android.model.MStockOrderListModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/21.
 */
public class PostStockOrderListPresenter extends BasePresenter implements PostStockOrderListContract.Presenter {
  private MStockOrderListModel stockOrderListModel;
  private PostStockOrderListContract.View view;

  public PostStockOrderListPresenter(HttpClient httpClient, PostStockOrderListContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void postStockOrderList(int productOrderId,@Nullable Integer orderStatus, @Nullable Integer timeStatus, int pageNo, int pageSize) {
    httpClient.getStockOrderList(productOrderId, orderStatus, timeStatus, pageNo, pageSize).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .subscribe(new Observer<MStockOrderListModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MStockOrderListModel mStockOrderListModel) {
            stockOrderListModel = mStockOrderListModel;
            view.showNext(mStockOrderListModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(stockOrderListModel);
          }
        });
  }
}
