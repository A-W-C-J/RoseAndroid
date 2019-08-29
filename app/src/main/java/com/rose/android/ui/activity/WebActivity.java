package com.rose.android.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.WebViewJs;
import com.rose.android.model.NavInfo;
import com.rose.android.network.HttpClient;

/**
 * Created by xiaohuabu on 17/9/25.
 */
@Route(path = "/ui/webActivity")
public class WebActivity extends BaseActivity {
  @Autowired
  public String url;
  @Autowired
  public boolean hasHost;
  @Autowired
  public Object data;
  @Autowired
  String title;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_web);
    setContentView(getRootView());
    setArrowBackClick(v -> finish());
    initViews();
    initListener();
  }

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("ANDROID_WEB_VIEW_JAVASCRIPT")
  @Override
  public void initViews() {
    super.initViews();
    WebView webView = (WebView) findViewById(R.id.webview);
    if (url != null) {
      if (!hasHost) {
        url = HttpClient.getUserServiceUrl() + url;
      }
      webView.loadUrl(url);
    }
    if (title != null) {
      setTitle(title);
    }
    ProgressBar progressBar = findViewById(R.id.pg);
    WebSettings settings = webView.getSettings();
    settings.setUseWideViewPort(true);
    settings.setJavaScriptEnabled(true);
    settings.setAllowFileAccess(true);
    settings.setBuiltInZoomControls(true);
    settings.setSupportZoom(true);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    webView.addJavascriptInterface(new WebViewJs(this), "AppJs");
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {
        Log.e(TAG, "onPageFinished: " + url);
        if (url.indexOf(RouterConfig.UrlConfig.tradeContract) != -1) {
          Log.e(TAG, "onPageFinished: " + url.indexOf(RouterConfig.UrlConfig.tradeContract));
          Log.e(TAG, "onPageFinished: " + data);
          webView.loadUrl("javascript:setTradeInfo(" + data + ")");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
          webView.evaluateJavascript("getNavInfo()", value -> {
            if (value != null) {
              final NavInfo navInfo = new Gson().fromJson(value, NavInfo.class);
              if (navInfo != null) {
                setTitle(navInfo.navTitle);
                setRightTxt(navInfo.navRightText);
                setRightTxtClick(v -> ARouter.getInstance().build(RouterConfig.webActivity).withString("url", navInfo.navRightUrl).navigation());
              } else {
                if (title == null) {
                  setTitle(view.getTitle());
                }
              }
            }
          });
        } else {
          if (title == null) {
            setTitle(view.getTitle());
          }
        }
      }
    });
    webView.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        progressBar.setProgress(newProgress);
      }
    });
  }
}
