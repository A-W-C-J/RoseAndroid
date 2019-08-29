package com.rose.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.CodeConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.GetTradeContractContract;
import com.rose.android.contract.PostOrderContract;
import com.rose.android.contract.DialogClick;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderModel;
import com.rose.android.model.MPostOrderModel;
import com.rose.android.model.MTradeContractModel;
import com.rose.android.presenter.GetTradeContractPresenter;
import com.rose.android.presenter.PostOrderPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.ContractDetailItemView;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.CouponListVM;
import com.rose.android.viewmodel.PreOrderVM;

/**
 * Created by xiaohuabu on 17/9/11.
 */
@Route(path = "/ui/ContractDetailActivity")
public class ApplicationContractDetailsActivity extends BaseActivity implements PostOrderContract.View, GetTradeContractContract.View {
    @Autowired
    public String orderName;
    @Autowired
    public int orderId;
    @Autowired
    public boolean isActivity;
    @Autowired
    public int productId;
    @Autowired
    public long loan;
    @Autowired
    public int position;
    private ContractDetailItemView basePrice;
    private ContractDetailItemView managerFee;
    private ContractDetailItemView warningLine;
    private ContractDetailItemView stopLine;
    private ContractDetailItemView tradingDay;
    private ContractDetailItemView beginDate;
    private ContractDetailItemView coupon;
    private ContractDetailItemView hold;
    private TextView sharingRate;
    private PreOrderVM preOrderVM;
    private TextView price;
    private Button applicationNow;
    private PostOrderPresenter postOrderPresenter;
    private GetTradeContractPresenter getTradeContractPresenter;
    private String couponId;
    private String selectPosition;
    private View contractTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        create(R.layout.activity_contract_detail);
        setContentView(getRootView());
        postOrderPresenter = new PostOrderPresenter(this, userHttpClient);
        getTradeContractPresenter = new GetTradeContractPresenter(this, userHttpClient);
        setTitle(orderName);
        initViews();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (postOrderPresenter != null) {
            postOrderPresenter.dispose();
            postOrderPresenter = null;
        }
        if (getTradeContractPresenter != null) {
            getTradeContractPresenter.dispose();
            getTradeContractPresenter = null;
        }
    }

    @Override
    public void initListener() {
        contractTxt.setOnClickListener(v -> getTradeContract(orderId, loan));
        applicationNow.setOnClickListener(v -> {
            String totalFee = "共计" + preOrderVM.getTotalPayment() + "元";
            RoseDialog roseDialog = RoseDialog.newInstance(new DialogClick() {
                @Override
                public void doPositiveClick() {
                    postOrder(orderId, loan, couponId == null ? null : Integer.valueOf(couponId));
                }

                @Override
                public void doNegativeClick() {

                }
            }, getString(R.string.application_now), getString(R.string.cancel), getString(R.string.hint), totalFee, getString(R.string.application_sub_content));
            roseDialog.show(getSupportFragmentManager(), "warning");
        });
        warningLine.setOnClickListener(v -> {
            RoseDialog roseDialog = RoseDialog.newInstance(ApplicationContractDetailsActivity.this, getString(R.string.get_it), null, getString(R.string.warning_line), getString(R.string.warning_content), null);
            roseDialog.show(getSupportFragmentManager(), "warning");
        });
        stopLine.setOnClickListener(v -> {
            RoseDialog roseDialog = RoseDialog.newInstance(ApplicationContractDetailsActivity.this, getString(R.string.get_it), null, getString(R.string.stop_line), getString(R.string.stop_line_content), null);
            roseDialog.show(getSupportFragmentManager(), "stop");
        });
        coupon.setOnClickListener(v -> {
                    if (preOrderVM.getCouponCount() != 0) {
                        ARouter.getInstance().build(RouterConfig.couponsListActivity).withString("position", selectPosition).withInt("productId", productId).navigation(ApplicationContractDetailsActivity.this, CodeConfig.COUPONSCODE);
                    } else {
                        ToastWithIcon.init("亲，当前合约类型无可用抵用券哦～").show();
                    }
                }
        );
    }

    @Override
    public void initViews() {
        contractTxt = findViewById(R.id.contract_txt);
        applicationNow = findViewById(R.id.btn_application_now);
        price = findViewById(R.id.tv_contract_price);
        basePrice = findViewById(R.id.ci_base_price);
        managerFee = findViewById(R.id.ci_manager_fee);
        warningLine = findViewById(R.id.ci_warning_line);
        warningLine.setIconVisiable(true);
        tradingDay = findViewById(R.id.ci_trading_day);
        beginDate = findViewById(R.id.ci_begin_date);
        coupon = findViewById(R.id.ci_coupon);
        coupon.setRightIconVisiable(true);
        sharingRate = findViewById(R.id.tv_sharing_rate);
        stopLine = findViewById(R.id.ci_stop_line);
        hold = findViewById(R.id.ci_hold);
        stopLine.setIconVisiable(true);
        preOrderVM = PreOrderVM.getInstance();
        if (preOrderVM != null) {
            initData(preOrderVM);
        }
        if (isActivity) {
            managerFee.setVisibility(View.GONE);
            coupon.setVisibility(View.GONE);
            sharingRate.setVisibility(View.GONE);
        }
    }

    private void initData(PreOrderVM preOrderVM) {
        basePrice.setRightText(preOrderVM.getMargin());
        price.setText(preOrderVM.getPrice());
        managerFee.setRightText(preOrderVM.getFee());
        warningLine.setRightText(preOrderVM.getWarningLine());
        stopLine.setRightText(preOrderVM.getStopLine());
        tradingDay.setRightText(preOrderVM.getTradingDays());
        beginDate.setRightText(preOrderVM.getBeginDate());
        coupon.setRightText(preOrderVM.getCoupon());
        sharingRate.setText(preOrderVM.getSharingRate());
        if (preOrderVM.getFeeHint() != null && preOrderVM.getFeeHint().length() > 0) {
            managerFee.setSubTitle(preOrderVM.getFeeHint());
        }
        if (preOrderVM.getMarginHint() != null && preOrderVM.getMarginHint().length() > 0) {
            basePrice.setSubTitle(preOrderVM.getMarginHint());
        }
        hold.setRightText(preOrderVM.getDescription());
    }

    @Override
    public void postOrder(int productItemId, long loan, @Nullable Integer coupon_id) {
        showLoadDialog();
        postOrderPresenter.postOrder(productItemId, loan, coupon_id);
    }

    @Override
    public void getTradeContract(int productItemId, long loan) {
        getTradeContractPresenter.getTradeContract(productItemId, loan);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MPostOrderModel) {
            ToastWithIcon.init("申请成功！").show();
            ARouter.getInstance().build(RouterConfig.orderDetailsActivity).withInt("orderId", ((MPostOrderModel) baseModel).getData().getId())
                    .withString("orderName", ((MPostOrderModel) baseModel).getData().getName()).withInt("selectPosition", position)
                    .withBoolean("isActivity", isActivity).navigation();
        } else if (baseModel instanceof MTradeContractModel) {
            Log.e(TAG, "requestCallBack: " + ((MTradeContractModel) baseModel).getData().toString());
            ARouter.getInstance().build(RouterConfig.webActivity)
                    .withString("url", RouterConfig.UrlConfig.tradeContract).withObject("data", ((MTradeContractModel) baseModel).getData()).navigation();
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeConfig.COUPONSCODE && requestCode == CodeConfig.COUPONSCODE && data != null) {
            couponId = data.getStringExtra("position");
            selectPosition = data.getStringExtra("selectPosition");
            Log.e(TAG, "onActivityResult: " + couponId);
            if (selectPosition != null && selectPosition.length() > 0) {
                coupon.setRightText(Utils.divide1000(CouponListVM.getInstance().getCoupons().getData().getUserCouponList().get(Integer.parseInt(selectPosition)).getCashDiscount()) + "元" + CouponListVM.getInstance().getCoupons().getData().getUserCouponList().get(Integer.parseInt(selectPosition)).getName());
            } else {
                couponId = null;
                coupon.setRightText(PreOrderVM.getInstance().getCoupon());
            }
        }
    }
}
