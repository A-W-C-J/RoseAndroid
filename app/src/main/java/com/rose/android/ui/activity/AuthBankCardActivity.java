package com.rose.android.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.AuthBankCardContract;
import com.rose.android.contract.CustomNavigationCallBack;
import com.rose.android.contract.DialogClick;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAuthBankCardModel;
import com.rose.android.presenter.AuthBankCardPresenter;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.UserVM;

import java.util.regex.Pattern;

@Route(path = "/ui/authBankCardActivity")
public class AuthBankCardActivity extends BaseActivity implements AuthBankCardContract.View{
    private EditText bankCard, phone;
    private TextView name;
    private Button btn;
    private AuthBankCardPresenter authBankCardPresenter;
    @Autowired
    String hasBankCard;
    @Autowired
    boolean hasUserAuthFreeze;
    public static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";
    private TextView bankNum;
    private TextView bankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_auth_bank_card);
        authBankCardPresenter = new AuthBankCardPresenter(this, userHttpClient);
        setContentView(getRootView());
        setTitle("绑定银行卡");
        initViews();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (authBankCardPresenter != null) {
            authBankCardPresenter.dispose();
            authBankCardPresenter = null;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        if (hasUserAuthFreeze) {
            findViewById(R.id.blank).setVisibility(View.VISIBLE);
            findViewById(R.id.login_form).setVisibility(View.GONE);
        } else {
            findViewById(R.id.login_form).setVisibility(View.VISIBLE);
            findViewById(R.id.blank).setVisibility(View.GONE);
        }
        bankCard = (EditText) findViewById(R.id.bank_card);
        phone = (EditText) findViewById(R.id.phone_num);
        btn = (Button) findViewById(R.id.next);
        name = (TextView) findViewById(R.id.name);
        bankNum = findViewById(R.id.tv_bank_num);
        bankName = findViewById(R.id.tv_bank_name);
        if (!("未实名").equals(UserVM.getInstance().getRealName())) {
            name.setText(UserVM.getInstance().getRealName());
        } else {
            RoseDialog.newInstance(new DialogClick() {
                @Override
                public void doPositiveClick() {
                    ARouter.getInstance().build(RouterConfig.authRealNameActivity).navigation(context, new CustomNavigationCallBack(AuthBankCardActivity.this,true));
                }

                @Override
                public void doNegativeClick() {
                    finish();
                }
            }, "去认证", getString(R.string.cancel), "提示", "亲，请先进行实名认证", null).show(getSupportFragmentManager(), "authBankCard");
        }
        if (("已绑定").equals(hasBankCard)) {
            findViewById(R.id.ll_bind).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_no_bind).setVisibility(View.GONE);
            bankNum.setText(UserVM.getInstance().getBankNo());
            bankName.setText(UserVM.getInstance().getBankName());
        } else {
            findViewById(R.id.ll_bind).setVisibility(View.GONE);
            findViewById(R.id.ll_no_bind).setVisibility(View.VISIBLE);
        }
    }
    private boolean isPhoneValid(String phone) {
        boolean b = Pattern.matches(REGEX_MOBILE, phone);
        if (!b) {
            ToastWithIcon.init("手机号码格式错误!").show();
        }
        return b;
    }

    @Override
    public void initListener() {
        super.initListener();
        btn.setOnClickListener(v -> {
            if (isPhoneValid(phone.getText().toString())) {
                AuthBankCardActivity.this.postBankCard(bankCard.getText().toString(), name.getText().toString(), phone.getText().toString());
            }
        });
        findViewById(R.id.ll_contract_me).setOnClickListener(v -> RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {

            }

            @Override
            public void doNegativeClick() {

            }
        }, getString(R.string.sure), getString(R.string.cancel), "客服电话", getString(R.string.phone_num), null).show(getSupportFragmentManager(), "dialog"));
    }

    @Override
    public void postBankCard(String bankCardNo, String name, String phone) {
        authBankCardPresenter.postBankCard(bankCardNo, name, phone);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MAuthBankCardModel) {
            RoseDialog.newInstance(new DialogClick() {
                @Override
                public void doPositiveClick() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else {
                        finish();
                    }
                }

                @Override
                public void doNegativeClick() {
                }
            }, "确定", null, "提示", "绑定成功", null).show(getSupportFragmentManager(), "auth");
        }
        return super.requestCallBack(baseModel);
    }

}
