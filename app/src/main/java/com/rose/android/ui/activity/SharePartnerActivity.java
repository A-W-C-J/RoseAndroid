package com.rose.android.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.GetPartnerInfoContract;
import com.rose.android.contract.GetShareInfoContract;
import com.rose.android.contract.PostBrokerageWithdrawContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MBrokerageWithdrawModel;
import com.rose.android.model.MPartnerInfoModel;
import com.rose.android.model.MShareInfoModel;
import com.rose.android.presenter.GetPartnerInfoPresenter;
import com.rose.android.presenter.GetShareInfoPresenter;
import com.rose.android.presenter.PostBrokerageWithdrawPresenter;
import com.rose.android.utils.QRCode;
import com.rose.android.utils.Utils;
import com.rose.android.view.ToastWithIcon;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

@Route(path = "/ui/sharePartnerActivity", extras = 1)
public class SharePartnerActivity extends BaseActivity implements GetShareInfoContract.View, GetPartnerInfoContract.View, PostBrokerageWithdrawContract.View {
    private GetShareInfoPresenter getShareInfoPresenter;
    private GetPartnerInfoPresenter getPartnerInfoPresenter;
    private TextView link, partnerCount, partnerCashCount;
    private ImageView qrCode;
    private Button button;
    private View way2, way3;
    private MShareInfoModel shareInfoModel;
    private TextView shareDetails;
    private TextView availabeBrokerageAmount;
    private TextView frozenBrokerageAmount;
    private PostBrokerageWithdrawPresenter postBrokerageWithdrawPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_share_partner);
        setContentView(getRootView());
        getShareInfoPresenter = new GetShareInfoPresenter(userHttpClient, this);
        getPartnerInfoPresenter = new GetPartnerInfoPresenter(userHttpClient, this);
        postBrokerageWithdrawPresenter = new PostBrokerageWithdrawPresenter(userHttpClient, this);
        setTitle("瑞云平台合伙人");
        setRightTxt("赚钱说明");
        setRightTxtClick(v -> {
            ARouter.getInstance().build(RouterConfig.webActivity).withString("url", RouterConfig.UrlConfig.shareGuide).withBoolean("hasHost", false)
                    .navigation();
        });
        initViews();
        initListener();
        getShareInfo();
        getPartnerInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getShareInfoPresenter != null) {
            getShareInfoPresenter.dispose();
            getShareInfoPresenter = null;
        }
        if (getPartnerInfoPresenter != null) {
            getPartnerInfoPresenter.dispose();
            getPartnerInfoPresenter = null;
        }
        if (postBrokerageWithdrawPresenter != null) {
            postBrokerageWithdrawPresenter.dispose();
            postBrokerageWithdrawPresenter = null;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        link = findViewById(R.id.link);
        link.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        qrCode = findViewById(R.id.qrcode);
        partnerCount = findViewById(R.id.partner_count);
        partnerCashCount = findViewById(R.id.partner_cash_count);
        button = findViewById(R.id.btn);
        way2 = findViewById(R.id.way2_parent);
        way3 = findViewById(R.id.qrcode_parent);
        shareDetails = findViewById(R.id.share_details);
        shareDetails.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        availabeBrokerageAmount = findViewById(R.id.avaliable_fee);
        frozenBrokerageAmount = findViewById(R.id.disavaliable_fee);
    }

    @Override
    public void initListener() {
        super.initListener();
        button.setOnClickListener(v -> postBrokerageWithdraw());
        shareDetails.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.shareDetailsActivity).navigation());
        way2.setOnClickListener(v -> {
            if (shareInfoModel != null) {
                UMWeb web = new UMWeb(shareInfoModel.getData().getUrl());
                web.setTitle(shareInfoModel.getData().getTitle());
                web.setThumb(new UMImage(context, R.mipmap.ic_launcher));
                web.setDescription(shareInfoModel.getData().getContent());
                startShareWeb(web);
            } else {
                ToastWithIcon.init("分享信息未获取到").show();
            }
        });
        way3.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.qrcodeActivity).withString("url", shareInfoModel.getData().getUrl()).navigation());
        link.setOnLongClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newHtmlText("link", link.getText(), link.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
            ToastWithIcon.init("复制成功").show();
            return true;
        });
    }

    @Override
    public void getShareInfo() {
        getShareInfoPresenter.getShareInfo();
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MShareInfoModel) {
            shareInfoModel = (MShareInfoModel) baseModel;
            link.setText(shareInfoModel.getData().getUrl());
            Bitmap bitmap = QRCode.createQRCode(shareInfoModel.getData().getUrl());
            qrCode.setImageBitmap(bitmap);
        } else if (baseModel instanceof MPartnerInfoModel) {
            MPartnerInfoModel partnerInfoModel = (MPartnerInfoModel) baseModel;
            partnerCount.setText(String.valueOf(partnerInfoModel.getData().getInviteUserCount()));
            partnerCashCount.setText(Utils.formatWithScale(Utils.divide1000(partnerInfoModel.getData().getInviteUserCount()), 2));
            availabeBrokerageAmount.setText(Utils.formatWithScale(Utils.divide1000(partnerInfoModel.getData().getAvailabeBrokerageAmount()), 2));
            frozenBrokerageAmount.setText(Utils.formatWithScale(Utils.divide1000(partnerInfoModel.getData().getFrozenBrokerageAmount()), 2));
        } else if (baseModel instanceof MBrokerageWithdrawModel) {
            ToastWithIcon.init("佣金转入成功").show();
            getPartnerInfo();
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void getPartnerInfo() {
        showLoadDialog();
        getPartnerInfoPresenter.getPartnerInfo();
    }

    @Override
    public void postBrokerageWithdraw() {
        showLoadDialog();
        postBrokerageWithdrawPresenter.postBrokerageWithdraw();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成回调
    }
}
