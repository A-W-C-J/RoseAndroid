package com.rose.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.GetMinAddMarginContract;
import com.rose.android.contract.PostAddMarginContract;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAddMarginModel;
import com.rose.android.model.MWalletModel;
import com.rose.android.presenter.GetMinAddMarginPresenter;
import com.rose.android.presenter.PostAddMarginPresenter;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.ToastWithIcon;

import java.math.BigDecimal;

@Route(path = "/ui/addmarginActivity")
public class AddMarginActivity extends BaseActivity implements GetMinAddMarginContract.View, PostAddMarginContract.View, UserInfoContract.View {
    @Autowired
    int orderId;
    @Autowired
    Long min;
    private GetMinAddMarginPresenter getMinAddMarginPresenter;
    private PostAddMarginPresenter postAddMarginPresenter;
    private TextView addLimit;
    private TextView balance;
    private EditText etLimit;
    private Long loan = Long.parseLong(String.valueOf(0));
    private Button sure;
    private UserInfoPresenter userInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_add_margin);
        setContentView(getRootView());
        setTitle("追加本金");
        getMinAddMarginPresenter = new GetMinAddMarginPresenter(this, userHttpClient);
        postAddMarginPresenter = new PostAddMarginPresenter(userHttpClient, this);
        userInfoPresenter = new UserInfoPresenter(userHttpClient, this);
        initViews();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getMinAddMarginPresenter != null) {
            getMinAddMarginPresenter.dispose();
            getMinAddMarginPresenter = null;
        }
        if (postAddMarginPresenter != null) {
            postAddMarginPresenter.dispose();
            postAddMarginPresenter = null;
        }
        if (userInfoPresenter != null) {
            userInfoPresenter.dispose();
            userInfoPresenter = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestUserAccount();
    }

    @Override
    public void initViews() {
        super.initViews();
        addLimit = findViewById(R.id.tv_add_limit);
        balance = findViewById(R.id.tv_balance);
        etLimit = findViewById(R.id.et_addlimit);
        sure = findViewById(R.id.btn_sure);
        addLimit.setText(String.format("追加本金不能少于总操盘资金的1%%，最低可追加%s元", Utils.formatWithScale(Utils.divide1000(min / 100), 2)));
    }

    @Override
    public void initListener() {
        super.initListener();
        sure.setOnClickListener(v -> {
            if (loan != null && loan != 0 && loan >= min / 1000 / 100) {
                postAddMargin(loan, String.valueOf(orderId));
            } else if (loan < min) {
                ToastWithIcon.init("亲，追加本金不能少于总操盘资金的1%").show();
            }
        });
        etLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    loan = Utils.multiply1000(new BigDecimal(etLimit.getText().toString()).longValue()).longValue();
                } catch (NumberFormatException e) {
                    loan = 0l;
                }
            }
        });
        etLimit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                if (loan != null && loan != 0 && loan >= min / 1000 / 100) {
                    postAddMargin(loan, String.valueOf(orderId));
                } else if (loan < min) {
                    ToastWithIcon.init("亲，追加本金不能少于总操盘资金的1%").show();
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public void getMinAddMargin(String productOrderId) {
        getMinAddMarginPresenter.getMinAddMargin(productOrderId);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MAddMarginModel) {
            ToastWithIcon.init("追加本金成功").show();
            finish();
        } else if (baseModel instanceof MWalletModel) {
            MWalletModel walletModel = (MWalletModel) baseModel;
            balance.setText(Utils.formatWithScale(Utils.divide1000(walletModel.getData().getCashAsset()), 2));
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void postAddMargin(Long margin, String productOrderId) {
        postAddMarginPresenter.postAddMargin(margin, productOrderId);
    }

    public void requestUserAccount() {
        userInfoPresenter.requestUserAccount();
    }
}
