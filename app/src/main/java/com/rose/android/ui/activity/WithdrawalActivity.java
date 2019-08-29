package com.rose.android.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.PostWithdrawalContract;
import com.rose.android.contract.WithdrawalPwdDialogClick;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MWalletModel;
import com.rose.android.model.MWithdrawlModel;
import com.rose.android.presenter.PostWithdrawalPresenter;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.view.WithdrawalCheckPwdDialog;
import com.rose.android.viewmodel.UserVM;

import java.math.BigDecimal;

@Route(path = "/ui/withdrawalActivity")
public class WithdrawalActivity extends BaseActivity implements PostWithdrawalContract.View, UserInfoContract.View {
    private PostWithdrawalPresenter postWithdrawalPresenter;
    private TextView balance;
    private EditText amountEdit;
    private long amount;
    private UserInfoPresenter userInfoPresenter;
    private Button btn;
    private String withdrawalPwd;
    private TextView bankNum;
    private TextView bankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_withdrawal);
        setContentView(getRootView());
        postWithdrawalPresenter = new PostWithdrawalPresenter(userHttpClient, this);
        userInfoPresenter = new UserInfoPresenter(userHttpClient, this);
        setTitle("提现");
        initViews();
        initListener();
        requestUserAccount();
    }

    @Override
    public void initListener() {
        findViewById(R.id.ll_contract_me).setOnClickListener(v -> RoseDialog.newInstance(new DialogClick() {
            @Override
            public void doPositiveClick() {

            }

            @Override
            public void doNegativeClick() {

            }
        }, getString(R.string.sure), getString(R.string.cancel), "客服电话", getString(R.string.phone_num), null).show(getSupportFragmentManager(), "dialog"));
        btn.setOnClickListener(v -> {
            try {
                amount = new BigDecimal(amountEdit.getText().toString()).multiply(new BigDecimal(1000)).longValue();
            } catch (NumberFormatException e) {
                ToastWithIcon.init("提现金额输入错误").show();
                amount = 0;
            }
            if (amount != 0) {
                WithdrawalCheckPwdDialog.newInstance(new WithdrawalPwdDialogClick() {
                    @Override
                    public void doPositiveClick(String pwd) {
                        withdrawalPwd = pwd;
                        postWithdrawal(amount, Utils.parseStrToMd5L32(withdrawalPwd));
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, getString(R.string.sure), null, getString(R.string.hint)).show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void initViews() {
        super.initViews();
        balance = (TextView) findViewById(R.id.balance);
        amountEdit = (EditText) findViewById(R.id.amount);
        btn = (Button) findViewById(R.id.btn);
        bankNum = findViewById(R.id.tv_bank_num);
        bankName = findViewById(R.id.tv_bank_name);
        bankName.setText(UserVM.getInstance().getBankNo());
        bankNum.setText(UserVM.getInstance().getBankNo());
    }

    @Override
    public void postWithdrawal(long amount, String pwd) {
        showLoadDialog();
        postWithdrawalPresenter.postWithdrawal(amount, pwd);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MWalletModel) {
            balance.setText(String.format("%s 元", Utils.formatWithScale(Utils.divide1000(((MWalletModel) baseModel).getData().getCashAsset()), 2)));
        } else if (baseModel instanceof MWithdrawlModel) {
            ToastWithIcon.init("提现成功").show();
            requestUserAccount();
        }
        return super.requestCallBack(baseModel);
    }

    public void requestUserAccount() {
        showLoadDialog();
        userInfoPresenter.requestUserAccount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userInfoPresenter != null) {
            userInfoPresenter.dispose();
            userInfoPresenter = null;
        }
        if (postWithdrawalPresenter != null) {
            postWithdrawalPresenter.dispose();
            postWithdrawalPresenter.dispose();
        }
    }
}
