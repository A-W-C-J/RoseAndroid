package com.rose.android.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.GetPreOrderContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MPreOrderModel;
import com.rose.android.model.MProductListModel;
import com.rose.android.network.HttpClient;
import com.rose.android.presenter.GetPreOrderPresenter;
import com.rose.android.ui.activity.ExperienceActivity;
import com.rose.android.utils.Utils;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.ActivityProductListVM;

import java.math.BigDecimal;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Created by xiaohuabu on 17/9/14.
 */
@SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
public class JoinExperienceFragment extends BaseFragment implements GetPreOrderContract.View {
  private static final String ARG_PARAM1 = "type";
  private static final String ARG_PARAM2 = "status";
  private RelativeLayout relativeLayout;
  private MProductListModel productListModel;
  private Button experienceNow;
  private String name = "";
  private int orderId;
  private long loan;
  private GetPreOrderPresenter getPreOrderPresenter;
  private WebView webView;
  private static final String TAG = "JoinExperienceFragment";
  private View[] views;
  private final static int[] temp = {100};
  private JsonArray info;

  public JoinExperienceFragment() {
  }

  public static JoinExperienceFragment newInstance(String param1, String param2) {
    JoinExperienceFragment fragment = new JoinExperienceFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  @Override
  protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(R.layout.layout_preview, container, false);
  }
  @Override
  protected void onCreateViewLazy(Bundle savedInstanceState) {
    super.onCreateViewLazy(savedInstanceState);
    create(R.layout.fragment_join_experience);
    View view = getRootView();
    getPreOrderPresenter = new GetPreOrderPresenter(this, userHttpClient);
    setContentView(view);
    initViews(view);
    initListener();
  }

  @Override
  public void onDestroyViewLazy() {
    super.onDestroyViewLazy();
    if (getPreOrderPresenter != null) {
      getPreOrderPresenter.dispose();
      getPreOrderPresenter = null;
    }
  }

  @Override
  public void initListener() {
    experienceNow.setOnClickListener(v -> {
      Log.e(TAG, "initListener: " + loan);
      if (loan <= 0) {
        ToastWithIcon.init("请选择配资品种!").show();
      } else {
        if (temp[0] <= views.length) {
          if (views[temp[0]].findViewById(R.id.image).getVisibility() == View.VISIBLE) {
            ToastWithIcon.init("你已经参加过该活动!").show();
          } else {
            getPreOrder(orderId, loan);
          }
        } else {
          getPreOrder(orderId, loan);
        }
      }
    });
  }

  public void changState(int i) {
    if (views != null) {
      ((TextView) views[i].findViewById(R.id.text)).setTextColor(ContextCompat.getColor(context, R.color.title_color_3));
      views[i].findViewById(R.id.text).setBackgroundResource(R.mipmap.ic_mf_experienced);
      ((TextView) views[i].findViewById(R.id.trade_day)).setTextColor(ContextCompat.getColor(context, R.color.title_color_3));
      views[i].findViewById(R.id.image).setVisibility(View.VISIBLE);
      ((TextView) views[i].findViewById(R.id.margin)).setTextColor(ContextCompat.getColor(context, R.color.title_color_3));
    }
  }

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"ANDROID_WEB_VIEW_JAVASCRIPT", "ANDROID_WEB_VIEW_JAVASCRIPT_INTERFACE"})
  @Override
  @SuppressLint("JavascriptInterface")
  public void initViews(View view) {
    experienceNow = (Button) view.findViewById(R.id.btn_experience_now);
    relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_experience);
    webView = (WebView) view.findViewById(R.id.webview);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setAllowFileAccess(true);
    webView.clearHistory();
    webView.clearCache(true);
    webView.clearFormData();
    webView.getSettings().setUseWideViewPort(true);//设定支持viewport
    webView.getSettings().setLoadWithOverviewMode(true);   //自适应屏幕
    webView.addJavascriptInterface(new WebViewResizer(), "WebViewResizer");
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {
        Log.e(TAG, "initViews: " + info);
        webView.loadUrl("javascript:setInfo(" + info + ")");
        webView.loadUrl("javascript:window.WebViewResizer.processHeight(document.querySelector('body').offsetHeight);");
        super.onPageFinished(view, url);
        int w = View.MeasureSpec.makeMeasureSpec(0,
            View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
            View.MeasureSpec.UNSPECIFIED);
        //重新测量
        webView.measure(w, h);
      }
    });
    info = new JsonArray();
    productListModel = ActivityProductListVM.getInstance().getProductListModel();
    if (productListModel != null) {
      relativeLayout.removeAllViews();
      views = new View[productListModel.getData().getProductList().size()];
      TextView[] textViews = new TextView[productListModel.getData().getProductList().size()];
      for (int i = 0; i < views.length; i++) {
        MProductListModel.DataBean.ProductListBean data = productListModel.getData().getProductList().get(i);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", data.getName());
        jsonObject.addProperty("money", data.getItems().size() > 0 ?
            Utils.divide1000((new BigDecimal(data.getMaxLoan())
                .divide(new BigDecimal(data.getItems().get(0).getMultiple()),0, BigDecimal.ROUND_HALF_EVEN)).longValue()) : 0);
        jsonObject.addProperty("giveMoney", data.getMaxLoan() / 1000);
        jsonObject.addProperty("days", data.getValidTradingDaysText());
        if ("免费体验".equals(data.getName())) {
          jsonObject.addProperty("tips", "亏损全赔付");
        } else {
          jsonObject.addProperty("tips", "亏损你承担");
        }
        info.add(jsonObject);
        views[i] = LayoutInflater.from(context).inflate(R.layout.experience_item, null, false);
        views[i].setId(i + 1);
        textViews[i] = (TextView) views[i].findViewById(R.id.text);
        textViews[i].setText(data.getName());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((Utils.getWidth() - Utils.dip2px(context, 64) - 48) / 2, Utils.getHeight() / 4);
        lp.addRule(RelativeLayout.BELOW, i + 1 - 2);
        lp.setMargins(24, 12, 0, 12);
        if (i % 2 == 0) {
          lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
          lp.addRule(RelativeLayout.RIGHT_OF, i);
        }
        relativeLayout.addView(views[i], i, lp);
        TextView margin = (TextView) views[i].findViewById(R.id.margin);
        TextView tradeDay = (TextView) views[i].findViewById(R.id.trade_day);
        if (data != null && data.getItems() != null && data.getItems().size() > 0) {
          ((ExperienceActivity) JoinExperienceFragment.this.getActivity())
                  .setPeopleCount(String.valueOf(data.getInvolvedUsersCount()));
          margin.setText(String.valueOf(Utils.divide1000((data.getMaxLoan() + (new BigDecimal(data.getMaxLoan()).divide(new BigDecimal(data.getItems().get(0).getMultiple()),0, BigDecimal.ROUND_HALF_EVEN)).intValue()))));
          tradeDay.setText(data.getValidTradingDaysText());
          int finalI = i;
          views[i].setOnClickListener(v -> {
            loan = data.getMaxLoan();
            name = data.getName();
            orderId = data.getItems().get(0).getId();
            if (temp[0] != finalI && temp[0] <= views.length) {
              views[temp[0]].setBackgroundResource(R.drawable.shape_experience_item);
            }
            temp[0] = finalI;
            views[finalI].setBackgroundResource(R.drawable.shape_white);
          });
        }
      }
      webView.loadUrl(HttpClient.getUserServiceUrl() + RouterConfig.UrlConfig.experience);
    }
  }

  @Override
  public void getPreOrder(int productItemId, long loan) {
    showLoadDialog();
    Log.e(TAG, "getPreOrder: " + loan);
    getPreOrderPresenter.getPreOrder(productItemId, loan);
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MPreOrderModel) {
      ARouter.getInstance().build(RouterConfig.contractDetails)
          .withBoolean("isActivity", true).withString("orderName", name).withLong("loan", loan)
          .withInt("orderId", orderId)
//          .withInt("productId",productListModel.getData().getProductList().get(i).getId())
          .navigation();
    }
    return super.requestCallBack(baseModel);
  }

  private class WebViewResizer {
    @JavascriptInterface
    public void processHeight(String height) {
      Log.e(TAG, "processHeight: " + height);
      Message message = new Message();
      message.arg1 = Utils.dip2px(JoinExperienceFragment.this.context, Float.valueOf(height));
      handler.sendMessage(message);
    }
  }

  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) webView.getLayoutParams();
      lp.height = msg.arg1;
      webView.setLayoutParams(lp);
    }
  };
}
