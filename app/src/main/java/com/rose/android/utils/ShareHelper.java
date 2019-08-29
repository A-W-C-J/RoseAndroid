
package com.rose.android.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;

import com.rose.android.view.WithOutSinaDialogShareSelector;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by wenen on 2017/10/27.
 */
public final class ShareHelper {
    private FragmentActivity mContext;
    private UMShareListener mCallback;
    private WithOutSinaDialogShareSelector mPlatformSelector;
    private UMWeb umWeb;

    public static ShareHelper instance(FragmentActivity context, UMShareListener callback, UMWeb web) {
        return new ShareHelper(context, callback, web);
    }

    private ShareHelper(FragmentActivity context, UMShareListener callback, UMWeb web) {
        mContext = context;
        mCallback = callback;
        umWeb = web;
        if (context == null) {
            throw new NullPointerException();
        }
    }

    public void setCallback(UMShareListener mCallback) {
        this.mCallback = mCallback;
    }

    public void showShareDialog() {
        mPlatformSelector = new WithOutSinaDialogShareSelector(mContext, () -> {
        }, mShareItemClick);
        mPlatformSelector.show();
    }

    void onShareSelectorDismiss() {
        reset();
        mContext = null;
    }

    public void hideShareWindow() {
        if (mPlatformSelector != null) {
            mPlatformSelector.dismiss();
        }
    }

    private AdapterView.OnItemClickListener mShareItemClick = (parent, view, position, id) -> {
        WithOutSinaDialogShareSelector.ShareTarget item = (WithOutSinaDialogShareSelector.ShareTarget) parent.getItemAtPosition(position);
        shareTo(item);
        hideShareWindow();
    };

    public void shareTo(WithOutSinaDialogShareSelector.ShareTarget item) {
        new ShareAction(mContext).setPlatform(item.media).withMedia(umWeb).setCallback(mCallback).share();
    }

    public Context getContext() {
        return mContext;
    }

    public void reset() {
        if (mPlatformSelector != null) {
            mPlatformSelector.release();
            mPlatformSelector = null;
        }
        mShareItemClick = null;
    }
}
