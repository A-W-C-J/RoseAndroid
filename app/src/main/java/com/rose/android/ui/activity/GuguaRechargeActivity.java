package com.rose.android.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.BuildConfig;
import com.rose.android.RouterConfig;
import com.rose.android.contract.PostGuaguaContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MGuaguaModel;
import com.rose.android.presenter.PostGuaguaPresenter;

import java.math.BigDecimal;

/**
 * Created by wenen on 2017/12/9.
 */
@Route(path = "/ui/guaguaRechargeActivity")
public class GuguaRechargeActivity extends BaseRechargeActivity implements PostGuaguaContract.View {
    private PostGuaguaPresenter guaguaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guaguaPresenter = new PostGuaguaPresenter(this, userHttpClient);
        if (BuildConfig.DEBUG) {
            mMinCoin = 0;
        } else {
            mMinCoin = 50;
        }
    }

    private Integer payChannel;
    @Autowired
    public String payType;

    @Override
    public void initEditText() {

    }

    @Override
    public void initKuaiJieBtn() {

    }

    @Override
    public void initPayTypeBtn() {
        if (payType != null) {
            String[] strings = payType.split("-");
            for (int i = 0; i < strings.length; i++) {
                if (i == 0 && Integer.parseInt(strings[i]) != 0) {
                    mPayMapList.add(PayTypes.PAY_TYPE_ZFB);
                } else if (i == 1 && Integer.parseInt(strings[i]) != 0) {
                    mPayMapList.add(PayTypes.PAY_TYPE_WX);
                } else if (i == 2 && Integer.parseInt(strings[i]) != 0) {
                    mPayMapList.add(PayTypes.PAY_TYPE_QQ);
                }
            }
        } else {
            mPayMapList.add(PayTypes.PAY_TYPE_ZFB);
            mPayMapList.add(PayTypes.PAY_TYPE_WX);
            mPayMapList.add(PayTypes.PAY_TYPE_QQ);
        }
    }

    @Override
    public void onPayClickListenter(BigDecimal amt, int position) {
        if (mPayMapList.get(position) == PayTypes.PAY_TYPE_WX) {
            payChannel = 3;
        } else if (mPayMapList.get(position) == PayTypes.PAY_TYPE_ZFB) {
            payChannel = 5;
        } else {
            payChannel = 6;
        }
        Integer source = 1;
        postGuagua(payChannel, amt.longValue(), source);
    }

    @Override
    public void postGuagua(Integer type, Long amount, Integer source) {
        showLoadDialog();
        guaguaPresenter.postGuagua(type, amount, source);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MGuaguaModel) {
            String name = "";
            switch (payChannel) {
                case 3:
                    name = "微信";
                    break;
                case 5:
                    name = "支付宝";
                    break;
                case 6:
                    name = "QQ";
                    break;
                default:
                    name = "QQ";
                    break;
            }
            ARouter.getInstance().build(RouterConfig.qrcodeActivity).withString("url", ((MGuaguaModel) baseModel).getData().getCodeUrl())
                    .withString("payChannel", name)
                    .withString("title", "支付二维码").navigation();
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (guaguaPresenter != null) {
            guaguaPresenter.dispose();
            guaguaPresenter = null;
        }
    }
}
