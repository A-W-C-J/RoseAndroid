package com.rose.android.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.AuthRealNameContract;
import com.rose.android.contract.DialogClick;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAuthRealNameModel;
import com.rose.android.presenter.AuthRealNamePresenter;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;

import java.util.regex.Pattern;

/**
 * Created by xiaohuabu on 17/9/29.
 */
@Route(path = "/ui/authRealNameActivity")
public class AuthNameActivity extends BaseActivity implements AuthRealNameContract.View {
  private EditText realName, idCardId;
  private Button btn;
  private AuthRealNamePresenter authRealNamePresenter;
  public static final String REGEX_PWD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
  public static final String REGEX_IDCARD = "^(\\d{14}|\\d{17})(\\d|[xX])$";
  @Autowired
  boolean hasUserAuthFreeze;
  private View noBlank, blank;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_auth_name);
    authRealNamePresenter = new AuthRealNamePresenter(this, userHttpClient);
    setContentView(getRootView());
    setTitle("实名认证");
    initViews();
    initListener();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (authRealNamePresenter != null) {
      authRealNamePresenter.dispose();
      authRealNamePresenter = null;
    }
  }

  @Override
  public void initViews() {
    super.initViews();
    realName = (EditText) findViewById(R.id.name);
    idCardId = (EditText) findViewById(R.id.id_card);
    btn = (Button) findViewById(R.id.next);
    blank = findViewById(R.id.blank);
    noBlank = findViewById(R.id.login_form);
    if (hasUserAuthFreeze) {
      blank.setVisibility(View.VISIBLE);
      noBlank.setVisibility(View.GONE);
    } else {
      blank.setVisibility(View.GONE);
      noBlank.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void initListener() {
    super.initListener();
    btn.setOnClickListener(v -> {
      if (isIdCardValid(idCardId.getText().toString())) {
        AuthNameActivity.this.postRealName(realName.getText().toString(), idCardId.getText().toString());
      }
    });
  }

  private boolean isIdCardValid(String idCard) {
    boolean b = Pattern.matches(REGEX_IDCARD, idCard);
    if (!b) {
      ToastWithIcon.init("身份证号码格式错误!").show();
    }
    return b;
  }

  @Override
  public void postRealName(String realName, String idCardNo) {
    authRealNamePresenter.postRealName(realName, idCardNo);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MAuthRealNameModel) {
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
