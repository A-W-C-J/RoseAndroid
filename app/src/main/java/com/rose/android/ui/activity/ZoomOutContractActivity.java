package com.rose.android.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rose.android.R;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.GetOrderDetailsContract;
import com.rose.android.contract.GetZoomInfoContract;
import com.rose.android.contract.PutZoomContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.model.MZoomInfoModel;
import com.rose.android.model.MZoomModel;
import com.rose.android.presenter.GetOrderDetailsPresenter;
import com.rose.android.presenter.GetZoomInfoPresenter;
import com.rose.android.presenter.PutZoomPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;

import java.math.BigDecimal;

@Route(path = "/ui/zoomoutActivity")
public class ZoomOutContractActivity extends BaseActivity implements GetOrderDetailsContract.View, PutZoomContract.View, GetZoomInfoContract.View {
    @Autowired
    int orderId;
    private GetOrderDetailsPresenter getOrderDetailsPresenter;
    private PutZoomPresenter putZoomPresenter;
    private TextView beforeMargin, afterMargin, beforeLoan, afterLoan,
            beforeWaring, afterWaring, beforeStop, afterStop, beforeManagerFee, afterManagerFee, amount;
    private Button sure;
    private EditText etAddLoan;
    private Long loan = Long.parseLong(String.valueOf(0));
    private int min = 2000 * 1000;
    private GetZoomInfoPresenter getZoomInfoPresenter;
    private Long loanLimit;
    private TextView tvLoanLimit;
    private TextView addInfo;
    private MZoomInfoModel zoomInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_zoom_out_contract);
        getOrderDetailsPresenter = new GetOrderDetailsPresenter(this, userHttpClient);
        putZoomPresenter = new PutZoomPresenter(this, userHttpClient);
        getZoomInfoPresenter = new GetZoomInfoPresenter(this, userHttpClient);
        setContentView(getRootView());
        setTitle("放大合约");
        getOrderDetails(orderId);
        initViews();
        initListener();
    }

    @Override
    public void initViews() {
        super.initViews();
        beforeMargin = findViewById(R.id.tv_before_margin);
        afterMargin = findViewById(R.id.tv_after_margin);
        beforeLoan = findViewById(R.id.tv_before_loan);
        afterLoan = findViewById(R.id.tv_after_loan);
        beforeWaring = findViewById(R.id.tv_before_warning);
        afterWaring = findViewById(R.id.tv_after_waring);
        beforeStop = findViewById(R.id.tv_before_stop);
        afterStop = findViewById(R.id.tv_after_stop);
        beforeManagerFee = findViewById(R.id.tv_before_manager_fee);
        afterManagerFee = findViewById(R.id.tv_after_manager_fee);
        amount = findViewById(R.id.tv_amount);
        sure = findViewById(R.id.btn_sure);
        etAddLoan = findViewById(R.id.et_addloan);
        tvLoanLimit = findViewById(R.id.tv_loan_limit);
        addInfo = findViewById(R.id.tv_add_info);
    }

    @Override
    public void initListener() {
        super.initListener();
        sure.setOnClickListener(v -> {
            addLoan();
        });
        etAddLoan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    loan = Utils.multiply1000(new BigDecimal(etAddLoan.getText().toString()).longValue()).longValue();
                } catch (NumberFormatException e) {
                    loan = 0l;
                }
                if (loan != 0 && loan >= min) {
                    getZoomInfo(loan, String.valueOf(orderId));
                } else {
                    clearTxt();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    loan = Utils.multiply1000(new BigDecimal(etAddLoan.getText().toString()).longValue()).longValue();
                } catch (NumberFormatException e) {
                    loan = 0l;
                }
            }
        });
        etAddLoan.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                addLoan();
                return true;
            }
            return false;
        });
    }

    private void addLoan() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        if (loan != 0 && loan >= min && loan <= loanLimit) {
            if (loan % 1000000 == 0) {
                Long needAddFee = zoomInfoModel.getData().getNeededSupplyMargin() + zoomInfoModel.getData().getNeededZoomMargin() + zoomInfoModel.getData().getNeededAddFee();
                RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        putZoom(loan, String.valueOf(orderId));
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, "确定", "取消", "提示", "共计 <span style=\"color: red;\">" + Utils.formatWithScale(Utils.divide1000(needAddFee), 2) + "</span> " +
                        "元", "补充本金+追加杠杠本金+追加管理费").show(getSupportFragmentManager(), "dialog");
            } else {
                ToastWithIcon.init("追加金额必须为1000的倍数").show();
            }
        } else if (loan < min) {
            ToastWithIcon.init("追加金额不能小于" + min / 1000 + "元").show();
        } else if (loan > loanLimit) {
            ToastWithIcon.init("追加金额不得大于" + loanLimit / 1000 + "元").show();
        }
    }

    private void clearTxt() {
        afterMargin.setText("");
        afterLoan.setText("");
        afterWaring.setText("");
        afterStop.setText("");
        addInfo.setText("");
    }

    @Override
    public void getOrderDetails(int orderId) {
        getOrderDetailsPresenter.getOrderDetails(orderId);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MOrderDetailsModel) {
            MOrderDetailsModel orderDetailsModel = (MOrderDetailsModel) baseModel;
            beforeMargin.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getMargin()), 2));
            beforeLoan.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getLoan()), 2));
            beforeWaring.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getWarningLine()), 2));
            beforeStop.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getStopLine()), 2));
            beforeManagerFee.setText(orderDetailsModel.getData().getTotalFeeText());
            amount.setText(Utils.formatWithScale(Utils.divide1000(orderDetailsModel.getData().getAmount()), 2));
            loanLimit = orderDetailsModel.getData().getLoanUpLimit();
            tvLoanLimit.setText("最高可追加" + Utils.formatWithScale(Utils.divide1000(loanLimit), 2) + "元");
        } else if (baseModel instanceof MZoomInfoModel) {
            zoomInfoModel = (MZoomInfoModel) baseModel;
            afterMargin.setText(Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getMargin()), 2));
            afterLoan.setText(Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getLoan()), 2));
            afterWaring.setText(Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getWarningLine()), 2));
            afterStop.setText(Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getStopLine()), 2));
            afterManagerFee.setText(zoomInfoModel.getData().getFeeText());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                addInfo.setText(Html.fromHtml("<p>补充本金 <span style=\"color: red;\">" + Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getNeededSupplyMargin()), 2) + "</span> " +
                        "元+追加杠杆本金 <span style=\"color: red;\">" + Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getNeededZoomMargin()), 2) + "</span> 元+追加管理费 <span style=\"color: red;\">"
                        + Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getNeededAddFee()), 2) + "</span> 元</p >", 0));
            } else {
                addInfo.setText(Html.fromHtml("<p>补充本金 <span style=\"color: red;\">" + Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getNeededSupplyMargin()), 2) + "</span> " +
                        "元+追加杠杆本金 <span style=\"color: red;\">" + Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getNeededZoomMargin()), 2) + "</span> 元+追加管理费 <span style=\"color: red;\">"
                        + Utils.formatWithScale(Utils.divide1000(zoomInfoModel.getData().getNeededAddFee()), 2) + "</span> 元</p >"));
            }
        } else if (baseModel instanceof MZoomModel) {
            dismissLoadDialog();
            ToastWithIcon.init("放大成功").show();
            finish();
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void putZoom(Long addLoan, String productOrderId) {
        showLoadDialog();
        putZoomPresenter.putZoom(addLoan, productOrderId);
    }

    @Override
    public void getZoomInfo(Long addLoan, String productOrderId) {
        getZoomInfoPresenter.getZoomInfo(addLoan, productOrderId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getZoomInfoPresenter != null) {
            getZoomInfoPresenter.dispose();
            getZoomInfoPresenter = null;
        }
        if (putZoomPresenter != null) {
            putZoomPresenter.dispose();
            putZoomPresenter = null;
        }
    }
}
