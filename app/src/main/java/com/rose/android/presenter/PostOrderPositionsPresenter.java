package com.rose.android.presenter;

import com.rose.android.contract.PostOrderPositionsContract;
import com.rose.android.model.MOrderPositionsModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/22.
 */
public class PostOrderPositionsPresenter extends BasePresenter implements PostOrderPositionsContract.Presenter {
  private MOrderPositionsModel orderPositionsModel;
  private PostOrderPositionsContract.View view;

  public PostOrderPositionsPresenter(HttpClient httpClient, PostOrderPositionsContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void postOrderPositions(int pageNo, int pageSize, @Nullable  String productOrderId) {
    httpClient.postOrderPositions(pageNo, pageSize, productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MOrderPositionsModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MOrderPositionsModel mOrderPositionsModel) {
            orderPositionsModel = mOrderPositionsModel;
            view.showNext(mOrderPositionsModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(orderPositionsModel);
          }
        });
  }

}
