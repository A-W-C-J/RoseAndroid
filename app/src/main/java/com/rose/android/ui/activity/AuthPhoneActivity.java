package com.rose.android.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.AuthPhoneContract;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.GetAuthCodeContract;
import com.rose.android.contract.LoginContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAuthCodeModel;
import com.rose.android.model.MAuthPhoneModel;
import com.rose.android.presenter.AuthPhonePresenter;
import com.rose.android.presenter.GetAuthCodePresenter;
import com.rose.android.presenter.LoginPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

@Route(path = "/ui/authPhoneActivity")
public class AuthPhoneActivity extends BaseActivity implements AuthPhoneContract.View,
    LoginContract.View, GetAuthCodeContract.View {
  private AuthPhonePresenter authPhonePresenter;
  private LoginPresenter loginPresenter;
  private GetAuthCodePresenter getAuthCodePresenter;
  private String authCode;
  private TextView tv_authCode;
  private Handler mCountDownHandler;
  private static int mCountDownTime = 60;
  private EditText phone, et_authCode, pwdEt;
  private String phoneNum;
  public static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";
  private Button btn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_auth_phone);
    setContentView(getRootView());
    authPhonePresenter = new AuthPhonePresenter(userHttpClient, this);
    loginPresenter = new LoginPresenter(userHttpClient, this);
    getAuthCodePresenter = new GetAuthCodePresenter(userHttpClient, this);
    setTitle("修改绑定手机");
    initViews();
    initListener();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (authPhonePresenter != null) {
      authPhonePresenter.dispose();
      authPhonePresenter = null;
    }
    if (loginPresenter != null) {
      loginPresenter.dispose();
      loginPresenter = null;
    }
    if (getAuthCodePresenter != null) {
      getAuthCodePresenter.dispose();
      getAuthCodePresenter = null;
    }
  }

  @Override
  public void pathPhone(String phone, String password, String captcha) {
    authPhonePresenter.pathPhone(phone, Utils.parseStrToMd5L32(password), captcha);
  }

  private static class CountDownHandler extends Handler {
    WeakReference<AuthPhoneActivity> weakReference;
    TextView authCode;

    public CountDownHandler(AuthPhoneActivity activity, TextView authCode) {
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
              authCode.setText(mCountDownTime + "s");
              authCode.setEnabled(false);
            }
          }
          mCountDownTime--;
        }
      }
    }
  }

  @Override
  public void initViews() {
    super.initViews();
    tv_authCode = (TextView) findViewById(R.id.tv_auth_code);
    phone = (EditText) findViewById(R.id.phone);
    et_authCode = (EditText) findViewById(R.id.auth_code);
    btn = (Button) findViewById(R.id.next);
    pwdEt = (EditText) findViewById(R.id.pwd);
  }

  @Override
  public void initListener() {
    super.initListener();
    initCountDownHandler(tv_authCode);
    tv_authCode.setOnClickListener(v13 -> {
      phoneNum = phone.getText().toString();
      if (isPhoneValid(phoneNum)) {
        getAuthCode(phoneNum, "BIND_PHONE");
        mCountDownTime = 60;
        startScheduleJob(mCountDownHandler, 0, 1000);
      }
    });
    btn.setOnClickListener(v -> {
      authCode = et_authCode.getText().toString().trim();
      pathPhone(phoneNum, pwdEt.getText().toString(), authCode);
    });
  }

  private void initCountDownHandler(TextView authCode) {
    mCountDownHandler = new CountDownHandler(this, authCode);
  }

  @Override
  public void login(String username, String password) {

  }

  @Override
  public void getAuthCode(String phone, String type) {
    getAuthCodePresenter.getAuthCode(phone, type);
  }


  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MAuthCodeModel) {
    } else if (baseModel instanceof MAuthPhoneModel) {
      RoseDialog.newInstance(new DialogClick() {
        @Override
        public void doPositiveClick() {
          finish();
        }

        @Override
        public void doNegativeClick() {

        }
      }, getString(R.string.sure), null, getString(R.string.hint), "绑定成功", null).show(getSupportFragmentManager(), "dialog");
    }
    return super.requestCallBack(baseModel);
  }

  private boolean isPhoneValid(String phone) {
    boolean b = Pattern.matches(REGEX_MOBILE, phone);
    if (!b) {
      ToastWithIcon.init("手机号码格式错误!").show();
    }
    return b;
  }
}
