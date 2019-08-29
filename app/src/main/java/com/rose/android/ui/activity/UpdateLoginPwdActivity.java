package com.rose.android.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.PathLoginPasswordContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MLoginPasswordModel;
import com.rose.android.presenter.PatchLoginPasswordPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;

import java.util.regex.Pattern;

@Route(path = "/ui/updateLoginPwdActivity")
public class UpdateLoginPwdActivity extends BaseActivity implements PathLoginPasswordContract.View {
  private EditText pwdEt, pwd2Et, pwd3Et;
  private String pwd, pwd2, pwd3;
  private Button btn;
  public static final String REGEX_PWD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
  private PatchLoginPasswordPresenter patchLoginPasswordPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_update_login_pwd);
    setContentView(getRootView());
    patchLoginPasswordPresenter = new PatchLoginPasswordPresenter(userHttpClient, this);
    setTitle("修改登录密码");
    initViews();
    initListener();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (patchLoginPasswordPresenter != null) {
      patchLoginPasswordPresenter.dispose();
      patchLoginPasswordPresenter = null;
    }
  }

  @Override
  public void initViews() {
    super.initViews();
    pwdEt = (EditText) findViewById(R.id.pwd);
    pwd2Et = (EditText) findViewById(R.id.pwd2);
    pwd3Et = (EditText) findViewById(R.id.pwd3);
    btn = (Button) findViewById(R.id.next);
  }

  @Override
  public void initListener() {
    super.initListener();
    btn.setOnClickListener(v -> {
      pwd = pwdEt.getText().toString();
      pwd2 = pwd2Et.getText().toString();
      pwd3 = pwd3Et.getText().toString();
      if (Utils.isPasswordValid(pwd2)) {
        if (!pwd2.equals(pwd3)) {
          ToastWithIcon.init("两次密码不一致").show();
        } else {
          patchPassword(Utils.parseStrToMd5L32(pwd2), Utils.parseStrToMd5L32(pwd));
        }
      }
    });
    findViewById(R.id.tv_forget_pwd).setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.forgetLoginPassword).withBoolean("isLogin", true)
        .withString("title", "忘记登录密码").navigation());
  }
  @Override
  public void patchPassword(String password, String oldPassword) {
    patchLoginPasswordPresenter.patchPassword(password, oldPassword);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MLoginPasswordModel) {
      RoseDialog.newInstance(new DialogClick() {
        @Override
        public void doPositiveClick() {
          finish();
        }

        @Override
        public void doNegativeClick() {

        }
      }, getString(R.string.sure), null, getString(R.string.hint), "修改成功", null).show(getSupportFragmentManager(), "dialog");
    }
    return super.requestCallBack(baseModel);
  }
}
