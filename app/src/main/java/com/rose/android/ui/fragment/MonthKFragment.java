package com.rose.android.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.JsonObject;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.network.HttpClient;


public class MonthKFragment extends BaseFragment {
    private String symbol;
    private WebView webView;
    public MonthKFragment() {
    }

    public static MonthKFragment newInstance(String symbol) {
        MonthKFragment fragment = new MonthKFragment();
        Bundle args = new Bundle();
        args.putString("symbol", symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            symbol = getArguments().getString("symbol");
        } }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        create(R.layout.fragment_fs);
        View view = getRootView();
        setContentView(view);
        initViews();
        initViews(view);
    }
    @Override
    public void initViews() {
        super.initViews();
        webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl(HttpClient.getUserServiceUrl() + RouterConfig.UrlConfig.kx);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("onPageFinished", "onPageFinished: "+url );
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("symbol", symbol);
                    jsonObject.addProperty("type", 1);
                    webView.loadUrl("javascript:viewDidAppear(" + jsonObject + ")");
                super.onPageFinished(view, url);
            }
        });
    }
}
