package com.rose.android.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jiyunxueyuanandroid.util.SMPayUtil;
import com.rose.android.BuildConfig;
import com.rose.android.contract.ShoumiPayContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MShoumiModel;
import com.rose.android.presenter.ShoumiPayPresenter;

import java.math.BigDecimal;

/**
 * Created by wenen on 2017/12/12.
 */
@Route(path = "/ui/shoumiActivity")
public class ShoumiPayRechargeActivity extends BaseRechargeActivity implements ShoumiPayContract.View {
    private ShoumiPayPresenter shoumiPayPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoumiPayPresenter = new ShoumiPayPresenter(this, userHttpClient);
        if (BuildConfig.DEBUG) {
            mMinCoin = 0;
        } else {
            mMinCoin = 50;
        }
    }

    @Override
    public void postShoumiPay(Long amount, Integer source) {
        showLoadDialog();
        shoumiPayPresenter.postShoumiPay(amount, source);
    }

    @Override
    public void initEditText() {

    }

    @Override
    public void initKuaiJieBtn() {

    }

    @Override
    public void initPayTypeBtn() {

    }

    @Override
    public void onPayClickListenter(BigDecimal amt, int position) {
        postShoumiPay(amt.longValue(), 1);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MShoumiModel) {
            gotopaybyJson(((MShoumiModel) baseModel).getData().toString());
        }

        return super.requestCallBack(baseModel);

    }

    private void gotopaybyJson(final String s) {
        runOnUiThread(() -> SMPayUtil.Init(ShoumiPayRechargeActivity.this, s));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shoumiPayPresenter != null) {
            shoumiPayPresenter.dispose();
            shoumiPayPresenter = null;
        }
    }
}
