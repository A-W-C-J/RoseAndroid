package com.rose.android.ui.activity.newstruct;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.BroadcastAction;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.CustomNavigationCallBack;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.contract.newstruct.UserSigninContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAuthCodeModel;
import com.rose.android.model.MRegiestedModel;
import com.rose.android.model.MUpdateUserInfoModel;
import com.rose.android.model.newstruct.UserSigninModel;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.presenter.newstruct.UserSigninPresenter;
import com.rose.android.ui.activity.BaseActivity;
import com.rose.android.utils.Utils;
import com.rose.android.view.ToastWithIcon;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Set;

@Route(path = "/ui/userSigninActivity")
public class UserSigninActivity extends BaseActivity implements UserSigninContract.View, UserInfoContract.View {

    @Autowired
    protected boolean routerUrl;

    private UserSigninPresenter userSigninPresenter;
    private UserInfoPresenter userInfoPresenter;

    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private TextView forgetPwd;
    private ScrollView loginForm;
    private LinearLayout llLoginForm;
    private View registeredView;
    private Button loginBtn;
    private View forgetPwdView;
    private View registeredTxt;
    private Handler mCountDownHandler;
    private static int mCountDownTime = 60;

    private String authCode;
    private Set<String> hint;
    private String[] hintArray;
    private String phoneNumLogin;
    private String pwdLogin;
    private String phoneNumRegistered;
    private String pwdRegistered;
    private EditText etAuthCode;
    private TextView loginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        create(R.layout.activity_login);
        setContentView(getRootView());
        userSigninPresenter = new UserSigninPresenter(userHttpClient, this);
        userInfoPresenter = new UserInfoPresenter(userHttpClient, this);
        setTitle(getString(R.string.login));
        initLoginViews();
        initLoginListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initLoginListener() {
        mPasswordView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                phoneNumLogin = mPhoneView.getText().toString();
                pwdLogin = mPasswordView.getText().toString();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                signin(phoneNumLogin, pwdLogin);
                return true;
            }
            return false;
        });

        mPhoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loginBtn.setOnClickListener(v -> {
            phoneNumLogin = mPhoneView.getText().toString();
            pwdLogin = mPasswordView.getText().toString();
            if (Utils.isPhoneValid(phoneNumLogin) && Utils.isPasswordValid(pwdLogin)) {
                signin(phoneNumLogin, pwdLogin);
            }
        });

        forgetPwd.setOnClickListener(v -> {
            setTitle(getString(R.string.forget_pwd));
            if (forgetPwdView == null) {
                forgetPwdView = LayoutInflater.from(context)
                        .inflate(R.layout.forget_pwd_layout, null, false);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                removeView(llLoginForm, forgetPwdView);
                forgetPwdView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        v.removeOnLayoutChangeListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            addView(forgetPwdView);
                        }
                    }
                });
            } else {
                loginForm.removeAllViews();
                loginForm.addView(forgetPwdView);
            }
            Button btnSure = (Button) forgetPwdView.findViewById(R.id.btn_sure);
            AutoCompleteTextView phone = (AutoCompleteTextView) forgetPwdView.findViewById(R.id.phone);
            EditText password = (EditText) forgetPwdView.findViewById(R.id.password);
            btnSure.setOnClickListener(v16 -> {
                phoneNumRegistered = phone.getText().toString();
                pwdRegistered = password.getText().toString();
                if (Utils.isPhoneValid(phoneNumRegistered) && Utils.isPasswordValid(pwdRegistered)) {
                    authCode = etAuthCode.getText().toString().trim();
                    updateUserInfo(phoneNumRegistered, pwdRegistered, authCode, null);
                }
            });
            etAuthCode = (EditText) forgetPwdView.findViewById(R.id.auth_code);
            TextView authCode = (TextView) forgetPwdView.findViewById(R.id.tv_auth_code);
            initCountDownHandler(authCode);
            authCode.setOnClickListener(v13 -> {
                phoneNumRegistered = phone.getText().toString();
                if (Utils.isPhoneValid(phoneNumRegistered)) {
                    getAuthCode(phoneNumRegistered, "FIND_BACK_LOGIN_PASSWORD");
                    mCountDownTime = 60;
                    startScheduleJob(mCountDownHandler, 0, 1000);
                }
            });
            setArrowBackClick(v1 -> {
                onBackPressed();
            });
        });

        registeredTxt.setOnClickListener(v -> {
            setTitle(getString(R.string.registered));
            if (registeredView == null) {
                registeredView = LayoutInflater.from(context).inflate(R.layout.registered_layout, null, false);
            }
            loginNow = (TextView) registeredView.findViewById(R.id.tv_login_now);
            TextView gotoServiceTxt = (TextView) registeredView.findViewById(R.id.tv_user_contract);
            gotoServiceTxt.setOnClickListener(v17 -> {
                ActivityOptionsCompat optionsCompat = Utils.getActivityOptionsCompat(v17);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.userContract).withBoolean("hasHost", false).withOptionsCompat(optionsCompat).navigation(UserSigninActivity.this);
                } else {
                    ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.userContract).withBoolean("hasHost", false).navigation();
                }
            });
            loginNow.setOnClickListener(v15 -> onBackPressed());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                removeView(llLoginForm, registeredView);
                registeredView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        v.removeOnLayoutChangeListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            addView(registeredView);
                        }
                    }
                });
            } else {
                loginForm.removeAllViews();
                loginForm.addView(registeredView);
            }
            etAuthCode = (EditText) registeredView.findViewById(R.id.auth_code);
            TextView authCode = (TextView) registeredView.findViewById(R.id.tv_auth_code);
            Button btnSure = (Button) registeredView.findViewById(R.id.btn_sure);
            AutoCompleteTextView phone = (AutoCompleteTextView) registeredView.findViewById(R.id.phone);
            EditText password = (EditText) registeredView.findViewById(R.id.password);
            btnSure.setOnClickListener(v14 -> {
                phoneNumRegistered = phone.getText().toString();
                pwdRegistered = password.getText().toString();
                if (Utils.isPhoneValid(phoneNumRegistered) && Utils.isPasswordValid(pwdRegistered)) {
                    UserSigninActivity.this.authCode = etAuthCode.getText().toString().trim();
                    signup(phoneNumRegistered, pwdRegistered);
                }
            });
            initCountDownHandler(authCode);
            authCode.setOnClickListener(v13 -> {
                phoneNumRegistered = phone.getText().toString();
                if (Utils.isPhoneValid(phoneNumRegistered)) {
                    getAuthCode(phoneNumRegistered, "REGISTER");
                    mCountDownTime = 60;
                    startScheduleJob(mCountDownHandler, 0, 1000);
                }
            });
            setArrowBackClick(v12 -> {
                onBackPressed();
            });
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void removeView(View removeView, View addView) {
        int cx = (removeView.getLeft() + removeView.getRight()) / 2;
        int cy = (removeView.getTop() + removeView.getBottom()) / 2;
        int initialRadius = removeView.getWidth();
        Animator animator = ViewAnimationUtils.createCircularReveal(removeView, cx, cy, initialRadius, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                loginForm.removeAllViews();
                loginForm.addView(addView);
            }
        });
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addView(View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int initialRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, initialRadius);
        animator.start();
    }

    private void storeInfo(String phoneNum, String pwd, String token) {
        hint.add(phoneNum);
        spEditor.putStringSet("hint", hint);
        spEditor.putString("phone", phoneNum);
        spEditor.putString("password", pwd);
        spEditor.putString("token", token);
        spEditor.commit();
    }


    private void signin(String username, String password) {
        showLoadDialog();
        userSigninPresenter.signin(username, Utils.parseStrToMd5L32(password));
    }

    private void getAuthCode(String phone, String type) {
        userSigninPresenter.requestCaptcha(phone);
    }


    public void signup(String username, String password) {
        showLoadDialog();
        userSigninPresenter.signup(username, Utils.parseStrToMd5L32(password), authCode, null);
    }

    public void updateUserInfo(String username, String password, String captcha, String nickname) {
        showLoadDialog();
        //todo: 201811  和新接口不一致，暂时不处理
        //updateUserInfoPresenter.updateUserInfo(username, Utils.parseStrToMd5L32(password), captcha, nickname);
    }

    private static class CountDownHandler extends Handler {
        WeakReference<UserSigninActivity> weakReference;
        TextView authCode;

        public CountDownHandler(UserSigninActivity activity, TextView authCode) {
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

    private void initCountDownHandler(TextView authCode) {
        mCountDownHandler = new UserSigninActivity.CountDownHandler(this, authCode);
    }

    private void initLoginViews() {
        loginBtn = findViewById(R.id.email_sign_in_button);
        llLoginForm = findViewById(R.id.ll_login_form);
        mPhoneView = findViewById(R.id.phone);
        mPasswordView = findViewById(R.id.password);
        forgetPwd = findViewById(R.id.tv_forget_pwd);
        loginForm = findViewById(R.id.login_form);
        registeredTxt = findViewById(R.id.tv_sign_in);
        mPhoneView.setText(sp.getString("phone", ""));
        hint = sp.getStringSet("hint", new android.support.v4.util.ArraySet<>());
        hintArray = new String[hint.size()];
        Iterator<String> iterator = hint.iterator();
        for (int i = 0; i < hintArray.length; i++) {
            if (iterator.hasNext()) {
                hintArray[i] = iterator.next();
            }
        }
        ArrayAdapter<String> hintAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, hintArray);
        mPhoneView.setAdapter(hintAdapter);
    }

    @Override
    public void onBackPressed() {
        cancelTimer();
        if ((llLoginForm != null && registeredView != null)
                || (llLoginForm != null && forgetPwdView != null) && loginForm != null) {
            setTitle(getString(R.string.login));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (registeredView != null) {
                    removeView(registeredView, llLoginForm);
                    llLoginForm.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            v.removeOnLayoutChangeListener(this);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                addView(llLoginForm);
                            }
                        }
                    });
                } else if (forgetPwdView != null) {
                    removeView(forgetPwdView, llLoginForm);
                    llLoginForm.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            v.removeOnLayoutChangeListener(this);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                addView(llLoginForm);
                            }
                        }
                    });

                }
            } else {
                loginForm.removeAllViews();
                loginForm.addView(llLoginForm);
            }
            registeredView = null;
            forgetPwdView = null;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registeredView = null;
        forgetPwdView = null;
        if (null != userSigninPresenter) {
            userSigninPresenter.dispose();
            userSigninPresenter = null;
        }

        if (null != userInfoPresenter) {
            userInfoPresenter.dispose();
            userInfoPresenter = null;
        }
    }

    @Override
    public BaseModel requestCallBack(BaseModel sv) {
        sendBroadcast(new Intent(BroadcastAction.UPDATE_SELF_SELCET));
        if (sv instanceof MRegiestedModel) {
            ToastWithIcon.init("注册成功！").show();
            storeInfo(phoneNumRegistered, pwdRegistered, ((MRegiestedModel) sv).getData().getToken());
            if (!routerUrl) {
                ARouter.getInstance().build(RouterConfig.main).withInt("position", 4).navigation(context, new CustomNavigationCallBack(this, true));
            } else {
                finish();
            }
        } else if (sv instanceof UserSigninModel) {
            storeInfo(phoneNumLogin, pwdLogin, ((UserSigninModel) sv).getData().getToken());
            if (!routerUrl) {
                ARouter.getInstance().build(RouterConfig.main).withInt("position", 4).navigation(context, new CustomNavigationCallBack(this, true));
            } else {
                finish();
            }
        } else if (sv instanceof MAuthCodeModel) {
        } else if (sv instanceof MUpdateUserInfoModel) {
            ToastWithIcon.init("密码修改成功！").show();
            onBackPressed();
        }
        return super.requestCallBack(sv);
    }

}
