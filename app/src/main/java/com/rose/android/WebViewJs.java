package com.rose.android;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.ui.activity.WebActivity;

/**
 * Created by wenen on 2017/11/28.
 */

public class WebViewJs {

  private static final String TAG = "WebViewJs";

  private Context context;

  public WebViewJs(Context context) {
    this.context = context;
  }

  /**
   * 获取用户Token
   */
  @JavascriptInterface
  public String getToken() {
    String token = "";
    if (UserInfoManager.getInstance().getLoginStatus()) {
      token = UserInfoManager.getInstance().getToken();
    }
    return token;
  }

  @JavascriptInterface
  public void gotoBackActivity() {
    if (context instanceof WebActivity) {
      ((WebActivity) context).finish();
    }
  }

  @JavascriptInterface
  public void openGuaGuaPayActivity(String payType) {
    ARouter.getInstance().build(RouterConfig.GuaguazhifuActivity).withString("payType", payType).navigation();
  }

  @JavascriptInterface
  public void openShouMiPayActivity() {
    ARouter.getInstance().build(RouterConfig.ShoumiPayActivity).navigation();
  }

  @JavascriptInterface
  public void gotoWeb() {
    ARouter.getInstance().build(RouterConfig.ShoumiPayActivity).navigation();
  }
}
