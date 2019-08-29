package com.rose.android.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.GetAuthCodeContract;
import com.rose.android.contract.PatchWithdrawalPwdContract;
import com.rose.android.contract.UpdateUserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAuthCodeModel;
import com.rose.android.model.MUpdateUserInfoModel;
import com.rose.android.model.MWithdrawalPwdModel;
import com.rose.android.presenter.GetAuthCodePresenter;
import com.rose.android.presenter.PatchWithdrawalPwdPresenter;
import com.rose.android.presenter.UpdateUserInfoPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.ToastWithIcon;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.regex.Pattern;

@Route(path = "/ui/forgetLoginPassword")
public class ForgetLoginPwdActivity extends BaseActivity implements PatchWithdrawalPwdContract.View, GetAuthCodeContract.View
        , UpdateUserInfoContract.View {
    private Handler mCountDownHandler;
    private static int mCountDownTime = 60;
    private GetAuthCodePresenter getAuthCodePresenter;
    private String authCode;
    private String phoneNumRegistered;
    private String pwdRegistered;
    private EditText etAuthCode;
    private PatchWithdrawalPwdPresenter patchWithdrawalPwdPresenter;
    private UpdateUserInfoPresenter updateUserInfoPresenter;
    @Autowired
    boolean isLogin;
    @Autowired
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_forget_login_pwd);
        setContentView(getRootView());
        patchWithdrawalPwdPresenter = new PatchWithdrawalPwdPresenter(userHttpClient, this);
        getAuthCodePresenter = new GetAuthCodePresenter(userHttpClient, this);
        updateUserInfoPresenter = new UpdateUserInfoPresenter(userHttpClient, this);
        initViews();
        setTitle(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (patchWithdrawalPwdPresenter != null) {
            patchWithdrawalPwdPresenter.dispose();
            patchWithdrawalPwdPresenter = null;
        }
        if (getAuthCodePresenter != null) {
            getAuthCodePresenter.dispose();
            getAuthCodePresenter = null;
        }
        if (updateUserInfoPresenter != null) {
            updateUserInfoPresenter.dispose();
            updateUserInfoPresenter = null;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        Button btnSure = findViewById(R.id.btn_sure);
        AutoCompleteTextView phone = findViewById(R.id.phone);
        EditText password = findViewById(R.id.password);
        btnSure.setOnClickListener(v16 -> {
            phoneNumRegistered = phone.getText().toString();
            pwdRegistered = password.getText().toString();
            if (Utils.isPhoneValid(phoneNumRegistered) && Utils.isPasswordValid(pwdRegistered)) {
                authCode = etAuthCode.getText().toString().trim();
                if (isLogin) {
                    updateUserInfo(phoneNumRegistered, pwdRegistered, authCode, null);
                } else {
                    patchPwd(pwdRegistered, null, phoneNumRegistered, authCode);
                }
            }
        });
        etAuthCode = findViewById(R.id.auth_code);
        TextView authCode = findViewById(R.id.tv_auth_code);
        initCountDownHandler(authCode);
        authCode.setOnClickListener(v13 -> {
            phoneNumRegistered = phone.getText().toString();
            if (Utils.isPhoneValid(phoneNumRegistered)) {
                if ("忘记提现密码".equals(title)) {
                    getAuthCode(phoneNumRegistered, "FIND_BACK_WITHDRAW_PASSWORD");
                } else {
                    getAuthCode(phoneNumRegistered, "FIND_BACK_LOGIN_PASSWORD");
                }
                mCountDownTime = 60;
                startScheduleJob(mCountDownHandler, 0, 1000);
            }
        });
        setArrowBackClick(v1 -> {
            onBackPressed();
        });
    }

    @Override
    public void getAuthCode(String phone, String type) {
        getAuthCodePresenter.getAuthCode(phone, type);
    }

    @Override
    public void updateUserInfo(String username, String password, String captcha, String nickname) {
        showLoadDialog();
        updateUserInfoPresenter.updateUserInfo(username, Utils.parseStrToMd5L32(password), captcha, nickname);
    }

    @Override
    public void patchPwd(String password, String oldPassword, String phone, String captcha) {
        patchWithdrawalPwdPresenter.patchPwd(Utils.parseStrToMd5L32(password), oldPassword, phone, captcha);
    }

    private static class CountDownHandler extends Handler {
        WeakReference<ForgetLoginPwdActivity> weakReference;
        TextView authCode;

        public CountDownHandler(ForgetLoginPwdActivity activity, TextView authCode) {
            weakReference = new WeakReference<>(activity);
            this.authCode = authCode;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference != null) {
                if (mCountDownTime >= 0) {
                    if (mCountDownTime == 0) {
                        if (authCode != null) {
                            authCode.setText(R.string.re_send_verify_code);
                            authCode.setEnabled(true);
                        }
                    } else {
                        if (authCode != null) {
                            authCode.setText(String.format(Locale.CHINA,"%ds", mCountDownTime));
                            authCode.setEnabled(false);
                        }
                    }
                    mCountDownTime--;
                }
            }
        }
    }

    private void initCountDownHandler(TextView authCode) {
        mCountDownHandler = new CountDownHandler(this, authCode);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        ToastWithIcon.init("密码修改成功！").show();
        onBackPressed();
        return super.requestCallBack(baseModel);
    }
}
