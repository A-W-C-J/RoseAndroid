package com.rose.android;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.view.ToastWithIcon;

/**
 * Created by wenen on 2017/11/20.
 */
@Interceptor(priority = 1)
public class HasLoginInterceptor implements IInterceptor {
  private Context mContext;

  @Override
  public void process(Postcard postcard, InterceptorCallback callback) {
    if (postcard.getExtra() == 1) {
      if (UserInfoManager.getInstance().getLoginStatus()) {
        callback.onContinue(postcard);
      } else {
        callback.onInterrupt(new Exception("<<<<<<<<<<<<<请先登录>>>>>>>>>>>>"));
        ARouter.getInstance().build(RouterConfig.userSignin).withBoolean("routerUrl", true).navigation(mContext, new NavigationCallback() {
          @Override
          public void onFound(Postcard postcard) {

          }

          @Override
          public void onLost(Postcard postcard) {

          }

          @Override
          public void onArrival(Postcard postcard) {

          }

          @Override
          public void onInterrupt(Postcard postcard) {

          }
        });
        MainLooper.runOnUiThread(() -> {
          ToastWithIcon.init("请先登录!").show();
        });
      }
    } else {
      callback.onContinue(postcard);
    }
  }

  @Override
  public void init(Context context) {
    mContext = context;
  }
}
