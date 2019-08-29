package com.rose.android.presenter;

import com.rose.android.contract.PatchWithdrawalPwdContract;
import com.rose.android.model.MWithdrawalPwdModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/13.
 */
public class PatchWithdrawalPwdPresenter extends BasePresenter implements PatchWithdrawalPwdContract.Presenter {
  private MWithdrawalPwdModel withdrawalPwdModel;
  private PatchWithdrawalPwdContract.View view;

  public PatchWithdrawalPwdPresenter(HttpClient httpClient, PatchWithdrawalPwdContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void patchPwd(String password, String oldPassword, String phone, String captcha) {
    httpClient.pathPassword(password, oldPassword,phone,captcha).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MWithdrawalPwdModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MWithdrawalPwdModel mWithdrawalPwdModel) {
            withdrawalPwdModel = mWithdrawalPwdModel;
            view.showNext(mWithdrawalPwdModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(withdrawalPwdModel);
          }
        });
  }
}
