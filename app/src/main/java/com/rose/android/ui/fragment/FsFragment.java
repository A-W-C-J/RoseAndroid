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


public class FsFragment extends BaseFragment {
    private WebView webView;
    private String symbol;
    public FsFragment() {
    }

    public static FsFragment newInstance(String symbol) {
        FsFragment fragment = new FsFragment();
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
        }
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        create(R.layout.fragment_fs);
        View view = getRootView();
        setContentView(view);
        initViews(view);
    }
    @Override
    public void initViews(View view) {
        super.initViews(view);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.loadUrl(HttpClient.getUserServiceUrl() + RouterConfig.UrlConfig.fs);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("onPageFinished", "onPageFinished: "+url );
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("symbol", symbol);
                webView.loadUrl("javascript:viewDidAppear(" + jsonObject + ")");
                super.onPageFinished(view, url);
            }
        });
    }
}
