package com.rose.android.presenter;

import com.rose.android.contract.PostOrderContract;
import com.rose.android.model.MOrderModel;
import com.rose.android.model.MPostOrderModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/11.
 */
public class PostOrderPresenter extends BasePresenter implements PostOrderContract.Presenter {
  private PostOrderContract.View view;
  private MPostOrderModel orderModel;

  public PostOrderPresenter(PostOrderContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postOrder(int productItemId, long loan, @Nullable Integer coupon_id) {
    httpClient.postOrder(productItemId, loan, coupon_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MPostOrderModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MPostOrderModel mOrderModel) {
        orderModel = mOrderModel;
        view.showNext(mOrderModel, true, disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(orderModel);
      }
    });
  }
}
