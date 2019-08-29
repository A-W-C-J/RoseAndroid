package com.rose.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.CustomNavigationCallBack;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.PatchWithdrawalPwdContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MWithdrawalPwdModel;
import com.rose.android.presenter.PatchWithdrawalPwdPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.UserVM;

import java.util.regex.Pattern;

@Route(path = "/ui/setWithdrawalPwaActivity")
public class SetWithdrawalPwdActivity extends BaseActivity implements PatchWithdrawalPwdContract.View {
    @Autowired
    boolean hasBind;
    private String newPwd, newPwd2, oldPwd;
    private EditText newPwdEt, newPwd2Et, oldPwdEt;
    private Button btn;
    private PatchWithdrawalPwdPresenter patchWithdrawalPwdPresenter;
    public static final String REGEX_PWD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_set_withdrawal_pwd);
        setContentView(getRootView());
        patchWithdrawalPwdPresenter = new PatchWithdrawalPwdPresenter(userHttpClient, this);
        if (hasBind) {
            setTitle("修改提款密码");
        } else {
            setTitle("设置提款密码");
        }
        initViews();
        initListener();
        if (("未绑定").equals(UserVM.getInstance().getBankCard())) {
            RoseDialog.newInstance(new DialogClick() {
                @Override
                public void doPositiveClick() {
                    ARouter.getInstance().build(RouterConfig.authBankCardActivity).navigation(context, new CustomNavigationCallBack(SetWithdrawalPwdActivity.this, true));
                }

                @Override
                public void doNegativeClick() {

                }
            }, "去认证", getString(R.string.cancel), getString(R.string.hint), "亲，请先绑定银行卡", null)
                    .show(getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (patchWithdrawalPwdPresenter != null) {
            patchWithdrawalPwdPresenter.dispose();
            patchWithdrawalPwdPresenter = null;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        newPwdEt = (EditText) findViewById(R.id.pwd);
        newPwd2Et = (EditText) findViewById(R.id.pwd2);
        oldPwdEt = (EditText) findViewById(R.id.pwd3);
        btn = (Button) findViewById(R.id.next);
        if (hasBind) {
            findViewById(R.id.input_pwd3).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.input_pwd3).setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        btn.setOnClickListener(v -> {
            if (hasBind) {
                oldPwd = oldPwdEt.getText().toString();
            }
            newPwd = newPwdEt.getText().toString();
            newPwd2 = newPwd2Et.getText().toString();
            if (Utils.isPasswordValid(newPwd)) {
                if (!newPwd.equals(newPwd2)) {
                    ToastWithIcon.init("两次密码不一致").show();
                } else {
                    patchPwd(Utils.parseStrToMd5L32(newPwd), oldPwd == null ? null : Utils.parseStrToMd5L32(oldPwd), null, null);
                }
            }
        });
        findViewById(R.id.tv_forget_pwd).setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.forgetLoginPassword).withBoolean("isLogin", false).withString("title", "忘记提现密码").navigation());
    }


    @Override
    public void patchPwd(String password, String oldPassword, String phone, String captcha) {
        patchWithdrawalPwdPresenter.patchPwd(password, oldPassword, phone, captcha);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MWithdrawalPwdModel) {
            RoseDialog.newInstance(new DialogClick() {
                @Override
                public void doPositiveClick() {
                    finish();
                }

                @Override
                public void doNegativeClick() {

                }
            }, getString(R.string.sure), null, getString(R.string.hint), "设置成功", null).show(getSupportFragmentManager(), "dialog");
        }
        return super.requestCallBack(baseModel);
    }
}
