package com.rose.android.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.rose.android.utils.ShareHelper;
import com.rose.android.view.ToastWithIcon;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;


/**
 * Created by wenen on 2017/10/27.
 */

public class BaseShareableActivity extends AppCompatActivity implements UMShareListener {
    protected ShareHelper mShare;
    private static final String TAG = "BaseShareableActivity";

    public void startShareWeb(@NonNull UMWeb web) {
        if (mShare == null) {
            mShare = ShareHelper.instance(this, this, web);
        }
        mShare.showShareDialog();
    }

    @Override
    protected void onDestroy() {
        if (mShare != null) {
            mShare.reset();
            mShare = null;
        }
        super.onDestroy();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        if ("WEIXIN".equals(share_media.toString())) {
            ToastWithIcon.init("微信" + "分享成功").show();
        } else if ("WEIXIN_CIRCLE".equals(share_media.toString())) {
            ToastWithIcon.init("微信朋友圈" + "分享成功").show();
        } else
            ToastWithIcon.init(share_media.toString() + "分享成功").show();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        if ("WEIXIN".equals(share_media.toString())) {
            ToastWithIcon.init("微信" + "分享失败").show();
        } else if ("WEIXIN_CIRCLE".equals(share_media.toString())) {
            ToastWithIcon.init("微信朋友圈" + "分享失败").show();
        } else
            ToastWithIcon.init(share_media.toString() + "分享失败").show();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        if ("WEIXIN".equals(share_media.toString())) {
            ToastWithIcon.init("微信" + "分享取消").show();
        } else if ("WEIXIN_CIRCLE".equals(share_media.toString())) {
            ToastWithIcon.init("微信朋友圈" + "分享取消").show();
        } else
            ToastWithIcon.init(share_media.toString() + "分享取消").show();
    }
}
