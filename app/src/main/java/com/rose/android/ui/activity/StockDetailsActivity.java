package com.rose.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.rose.android.BroadcastAction;
import com.rose.android.BuildConfig;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.DeleteSelfSelectStockContract;
import com.rose.android.contract.GetStockMarketInfoContract;
import com.rose.android.contract.PostSelfSelectStockContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MSelectStockDeleteModel;
import com.rose.android.model.MSelfSelectStockModel;
import com.rose.android.model.MStockMarketInfoModel;
import com.rose.android.presenter.DeleteSelfSelectStockPresenter;
import com.rose.android.presenter.GetStockMarketInfoPresenter;
import com.rose.android.presenter.PostSelfSelectStockPresenter;
import com.rose.android.ui.adapter.OneByOnedapter;
import com.rose.android.ui.adapter.PagerAdapter;
import com.rose.android.ui.fragment.BaseFragment;
import com.rose.android.ui.fragment.DayKFragment;
import com.rose.android.ui.fragment.FsFragment;
import com.rose.android.ui.fragment.MonthKFragment;
import com.rose.android.ui.fragment.WeekkFragment;
import com.rose.android.utils.Utils;
import com.rose.android.view.NoScrollTimeViewPager;
import com.rose.android.view.ToastWithIcon;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

@Route(path = "/ui/StockDetailActivity")
public class StockDetailsActivity extends BaseActivity implements GetStockMarketInfoContract.View, PostSelfSelectStockContract.View, DeleteSelfSelectStockContract.View {
    @Autowired
    public String symbol;
    @Autowired
    public String StockExchange;
    @Autowired
    public boolean isSelf;
    private GetStockMarketInfoPresenter getStockMarketInfoPresenter;
    private NoScrollTimeViewPager viewPager;
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fragments;
    private String[] tabNames;
    private MStockMarketInfoModel stockMarketInfoModel;
    private TextView price, openPrice, closePrice, maxPrice, minPrice, change, volumn;
    private ImageView back, add;
    private TextView stockName, stockNum;
    private ImageView type;
    private Button buy;
    private PostSelfSelectStockPresenter postSelfSelectStockPresenter;
    private DeleteSelfSelectStockPresenter deleteSelfSelectStockPresenter;
    private int kType = 3;
    private NoScrollTimeViewPager viewPagerWeb;
    private FixedIndicatorView indicatorWeb;
    private IndicatorViewPager indicatorViewPagerWeb;
    private ArrayList<Fragment> fragmentsWeb;
    private String[] tabNamesWeb = {"分时", "日K", "周K", "月K"};
    private TextView stockStatus;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_stock_details);
        View view = getRootView();
        setContentView(view);
        getStockMarketInfoPresenter = new GetStockMarketInfoPresenter(this, userHttpClient);
        postSelfSelectStockPresenter = new PostSelfSelectStockPresenter(userHttpClient, this);
        deleteSelfSelectStockPresenter = new DeleteSelfSelectStockPresenter(userHttpClient, this);
        fragments = new ArrayList<>();
        fragmentsWeb = new ArrayList<>();
        fragments.add(FiveMarketFragment.newInstance(symbol));
        fragments.add(ExChangeOneByOne.newInstance(symbol));
        fragmentsWeb.add(FsFragment.newInstance(symbol));
        fragmentsWeb.add(DayKFragment.newInstance(symbol));
        fragmentsWeb.add(WeekkFragment.newInstance(symbol));
        fragmentsWeb.add(MonthKFragment.newInstance(symbol));
        initViews();
        initListener();
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getStockMarketInfo(symbol, null);
            }
        }, 0, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        viewPagerWeb = findViewById(R.id.vp_viewpager_web);
        viewPagerWeb.setOffscreenPageLimit(4);
        viewPagerWeb.setNoScroll(true);
        indicatorWeb = findViewById(R.id.fxidv_indicator_web);
        indicatorWeb.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
        viewPager = findViewById(R.id.vp_viewpager);
        indicator = findViewById(R.id.fxidv_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(context, R.color.color_gold, R.color.title_color_3));
        View view = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
        indicator.setCenterView(view);
        indicator.setSplitMethod(1);
        View viewWeb = LayoutInflater.from(context).inflate(R.layout.center_view_layout, null, false);
        indicatorWeb.setCenterView(viewWeb);
        indicatorWeb.setSplitMethod(1);
        ColorBar colorBar = new ColorBar(context, ContextCompat.getColor(context, R.color.color_gold), 8);
        colorBar.setWidth(200);
        indicator.setScrollBar(colorBar);
        indicatorWeb.setScrollBar(colorBar);
        tabNames = new String[]{"买卖五档", "逐笔成交"};
        indicatorViewPagerWeb = new IndicatorViewPager(indicatorWeb, viewPagerWeb);
        indicatorViewPagerWeb.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabNamesWeb, context, fragmentsWeb));
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabNames, context, fragments));
        price = findViewById(R.id.tv_price);
        openPrice = findViewById(R.id.tv_open_today_price);
        closePrice = findViewById(R.id.tv_close_yesterday_price);
        maxPrice = findViewById(R.id.tv_today_price_max);
        change = findViewById(R.id.tv_change);
        minPrice = findViewById(R.id.tv_min_price);
        volumn = findViewById(R.id.tv_volumn);
        back = findViewById(R.id.imageView2);
        stockName = findViewById(R.id.tv_stock_name);
        stockNum = findViewById(R.id.tv_stock_num);
        type = findViewById(R.id.iv_image);
        buy = findViewById(R.id.buy);
        add = findViewById(R.id.iv_add);
        if (isSelf) {
            add.setImageResource(R.mipmap.ic_delete_self_selection);
        } else {
            add.setImageResource(R.mipmap.ic_add_self_selection);
        }
        stockStatus = findViewById(R.id.stock_status);
    }

    @Override
    public void initListener() {
        super.initListener();
        back.setOnClickListener(v -> onBackPressed());
        buy.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.main)
                .withInt("position", 3).withString("symbol", symbol).navigation());
    }

    @Override
    public void getStockMarketInfo(String symbol, Integer productOrderId) {
        getStockMarketInfoPresenter.getStockMarketInfo(symbol, productOrderId);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        Intent intent = new Intent(BroadcastAction.UPDATE_SELF_SELCET);
        if (baseModel instanceof MStockMarketInfoModel) {
            stockMarketInfoModel = (MStockMarketInfoModel) baseModel;
            if (stockMarketInfoModel.getData().getChangePrice() >= 0) {
                price.setTextColor(ContextCompat.getColor(context, R.color.red));
                price.setText(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getCurrentPrice()), 2));
                change.setText(String.format(Locale.CHINA, "+%s[+%s%%]", Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getChangePrice()), 2), Utils.formatWithScale(new BigDecimal(stockMarketInfoModel
                        .getData().getChangePercent()).divide(new BigDecimal(100)), 2)));
                change.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                price.setTextColor(ContextCompat.getColor(context, R.color.green));
                price.setText(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getCurrentPrice()), 2));
                change.setText(String.format(Locale.CHINA, "%s[%s%%]", Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getChangePrice()), 2), Utils.formatWithScale(new BigDecimal(stockMarketInfoModel
                        .getData().getChangePercent()).divide(new BigDecimal(100)), 2)));
                change.setTextColor(ContextCompat.getColor(context, R.color.green));
            }
            openPrice.setText(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getOpenPrice()), 2));
            closePrice.setText(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getClosePrice()), 2));
            maxPrice.setText(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getHighestPrice()), 2));
            if (stockMarketInfoModel.getData().getVolumn() >= 10000) {
                volumn.setText(String.format(Locale.CHINA, "%s万手", Utils.formatWithScale(new BigDecimal(stockMarketInfoModel.getData().getVolumn()).divide(new BigDecimal(1000000)), 2)));
            } else {
                volumn.setText(String.format(Locale.CHINA, "%s手", Utils.formatWithScale(new BigDecimal(stockMarketInfoModel.getData().getVolumn()).divide(new BigDecimal(10000)), 2)));
            }
            minPrice.setText(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getLowestPrice()), 2));
            stockName.setText(stockMarketInfoModel.getData().getName());
            stockNum.setText(stockMarketInfoModel.getData().getSymbol());
            add.setOnClickListener(v -> {
                if (isSelf) {
                    deleteSelfSelect(StockExchange, stockMarketInfoModel.getData().getName(), symbol);
                } else {
                    postSelfSelect(StockExchange, stockMarketInfoModel.getData().getName(), symbol);
                }
            });
            switch (StockExchange) {
                case "SH":
                    type.setImageResource(R.mipmap.ic_sh);
                    break;
                case "SZ":
                    type.setImageResource(R.mipmap.ic_sz);
                    break;
                default:
                    type.setImageResource(R.mipmap.ic_sh);
                    break;
            }
            stockStatus.setText(stockMarketInfoModel.getData().getStockStatusText());
            if (stockMarketInfoModel.getData().getStockStatus()==3){
                openPrice.setText("--");
                maxPrice.setText("--");
                minPrice.setText("--");
                volumn.setText("--");
                price.setText(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getClosePrice()),2));
            }

        } else if (baseModel instanceof MSelfSelectStockModel) {
            ToastWithIcon.init("添加自选成功").show();
            isSelf = true;
            add.setImageResource(R.mipmap.ic_delete_self_selection);
            sendBroadcast(intent);
        } else if (baseModel instanceof MSelectStockDeleteModel) {
            ToastWithIcon.init("删除自选成功").show();
            isSelf = false;
            add.setImageResource(R.mipmap.ic_add_self_selection);
            sendBroadcast(intent);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void postSelfSelect(String stockExchange, String stockName, String stockSymbol) {
        showLoadDialog();
        postSelfSelectStockPresenter.postSelfSelect(stockExchange, stockName, stockSymbol);
    }

    @Override
    public void deleteSelfSelect(String stockExchange, String stockName, String stockSymbol) {
        showLoadDialog();
        deleteSelfSelectStockPresenter.deleteSelfSelect(stockExchange, stockName, stockSymbol);
    }

    public static class FiveMarketFragment extends BaseFragment implements GetStockMarketInfoContract.View {
        private TextView sale1, sale2, sale3, sale4, sale5, saleValue1,
                saleValue2, saleValue3, saleValue4, saleValue5,
                saleCount1, saleCount2,
                saleCount3, saleCount4, saleCount5, buy1,
                buy2, buy3, buy4, buy5, buyValue1, buyValue2,
                buyValue3, buyValue4, buyValue5, buyCount1, buyCount2,
                buyCount3, buyCount4, buyCount5;
        private TextView[] sales = new TextView[5];
        private TextView[] salesValue = new TextView[5];
        private TextView[] salesCount = new TextView[5];
        private TextView[] buys = new TextView[5];
        private TextView[] buysValue = new TextView[5];
        private TextView[] buysCount = new TextView[5];
        private MStockMarketInfoModel stockMarketInfoModel;
        public String symbol;
        private GetStockMarketInfoPresenter getStockMarketInfoPresenter;
        private Timer timer;
        public FiveMarketFragment() {
        }

        public static FiveMarketFragment newInstance(String symbol) {
            FiveMarketFragment fragment = new FiveMarketFragment();
            Bundle args = new Bundle();
            args.putString("symbol", symbol);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
            return inflater.inflate(R.layout.layout_preview, container, false);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            if (getArguments() != null) {
                symbol = getArguments().getString("symbol");
            }
            super.onCreate(savedInstanceState);
        }

        @Override
        protected void onCreateViewLazy(Bundle savedInstanceState) {
            create(R.layout.fragment_five_market);
            super.onCreateViewLazy(savedInstanceState);
            View view = getRootView();
            setContentView(view);
            getStockMarketInfoPresenter = new GetStockMarketInfoPresenter(this, userHttpClient);
            initViews(view);
            initListener();
            if (timer != null) {
                timer.cancel();
            }
            timer = null;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getStockMarketInfo(symbol, null);
                }
            }, 0, 5000);
        }

        @Override
        protected void onDestroyViewLazy() {
            super.onDestroyViewLazy();
            if (timer!=null){
                timer.cancel();
            }
        }

        @Override
        public void initViews(View view) {
            super.initViews(view);
            sale1 = view.findViewById(R.id.tv_sale1);
            sale2 = view.findViewById(R.id.tv_sale2);
            sale3 = view.findViewById(R.id.tv_sale3);
            sale4 = view.findViewById(R.id.tv_sale4);
            sale5 = view.findViewById(R.id.tv_sale5);
            sales = new TextView[]{sale1, sale2, sale3, sale4, sale5};
            saleValue1 = view.findViewById(R.id.tv_sale1_value);
            saleValue2 = view.findViewById(R.id.tv_sale2_value);
            saleValue3 = view.findViewById(R.id.tv_sale3_value);
            saleValue4 = view.findViewById(R.id.tv_sale4_value);
            saleValue5 = view.findViewById(R.id.tv_sale5_value);
            saleCount1 = view.findViewById(R.id.tv_sale1_count);
            saleCount2 = view.findViewById(R.id.tv_sale2_count);
            saleCount3 = view.findViewById(R.id.tv_sale3_count);
            saleCount4 = view.findViewById(R.id.tv_sale4_count);
            saleCount5 = view.findViewById(R.id.tv_sale5_count);
            salesValue = new TextView[]{saleValue1, saleValue2, saleValue3, saleValue4, saleValue5};
            salesCount = new TextView[]{saleCount1, saleCount2, saleCount3, saleCount4, saleCount5};
            buy1 = view.findViewById(R.id.tv_buy1);
            buy2 = view.findViewById(R.id.tv_buy2);
            buy3 = view.findViewById(R.id.tv_buy3);
            buy4 = view.findViewById(R.id.tv_buy4);
            buy5 = view.findViewById(R.id.tv_buy5);
            buyValue1 = view.findViewById(R.id.tv_buy1_value);
            buyValue2 = view.findViewById(R.id.tv_buy2_value);
            buyValue3 = view.findViewById(R.id.tv_buy3_value);
            buyValue4 = view.findViewById(R.id.tv_buy4_value);
            buyValue5 = view.findViewById(R.id.tv_buy5_value);
            buyCount1 = view.findViewById(R.id.tv_buy1_count);
            buyCount2 = view.findViewById(R.id.tv_buy2_count);
            buyCount3 = view.findViewById(R.id.tv_buy3_count);
            buyCount4 = view.findViewById(R.id.tv_buy4_count);
            buyCount5 = view.findViewById(R.id.tv_buy5_count);
            buys = new TextView[]{buy1, buy2, buy3, buy4, buy5};
            buysValue = new TextView[]{buyValue1, buyValue2, buyValue3, buyValue4, buyValue5};
            buysCount = new TextView[]{buyCount1, buyCount2, buyCount3, buyCount4, buyCount5};
        }

        @Override
        public void getStockMarketInfo(String symbol, Integer productOrderId) {
            getStockMarketInfoPresenter.getStockMarketInfo(symbol, productOrderId);
        }

        @Override
        public BaseModel requestCallBack(BaseModel baseModel) {
            if (baseModel instanceof MStockMarketInfoModel) {
                stockMarketInfoModel = (MStockMarketInfoModel) baseModel;
                for (int i = 0; i < stockMarketInfoModel.getData().getBuyLevelPrice().size(); i++) {
                    if (stockMarketInfoModel.getData().getStatus() == 3) {
                        buysValue[i].setTextColor(ContextCompat.getColor(context, R.color.red));
                        salesValue[i].setTextColor(ContextCompat.getColor(context, R.color.red));
                    } else if (stockMarketInfoModel.getData().getStatus() == 2) {
                        buysValue[i].setTextColor(ContextCompat.getColor(context, R.color.green));
                        salesValue[i].setTextColor(ContextCompat.getColor(context, R.color.green));
                    } else {
                        buysValue[i].setTextColor(ContextCompat.getColor(context, R.color.title_color_2));
                        salesValue[i].setTextColor(ContextCompat.getColor(context, R.color.title_color_2));
                    }
                    int size = stockMarketInfoModel.getData().getSellLevelPrice().size();
                    buys[i].setText(stockMarketInfoModel.getData().getBuyLevelPrice().get(size - 1 - i).getLevel());
                    if (stockMarketInfoModel.getData().getStockStatus() == 3) {
                        buysValue[i].setText("--");
                        buysCount[i].setText("--");
                    } else {
                        buysValue[i].setText(String.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getBuyLevelPrice().get(size - 1 - i).getPrice()), 2)));
                        buysCount[i].setText(Utils.addUnitWhenBeyondThousand(new BigDecimal(stockMarketInfoModel.getData().getBuyLevelPrice().get(size - 1 - i).getHandAmount())));
                    }
                }
                for (int i = 0; i < stockMarketInfoModel.getData().getSellLevelPrice().size(); i++) {
                    sales[i].setText(stockMarketInfoModel.getData().getSellLevelPrice().get(i).getLevel());
                    if (stockMarketInfoModel.getData().getStockStatus() == 3) {
                        salesValue[i].setText("--");
                        salesCount[i].setText("--");
                    } else {
                        salesValue[i].setText(String.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getSellLevelPrice().get(i).getPrice()), 2)));
                        salesCount[i].setText(Utils.addUnitWhenBeyondThousand(new BigDecimal(stockMarketInfoModel.getData().getSellLevelPrice().get(i).getHandAmount())));
                    }
                }
            }
            return super.requestCallBack(baseModel);
        }
    }

    public static class ExChangeOneByOne extends BaseFragment implements ClickResponseListener, GetStockMarketInfoContract.View {
        private RecyclerView recyclerView;
        private OneByOnedapter oneByOnedapter;
        private ArrayList<MStockMarketInfoModel.DataBean.StockTransactionList> stockTransactionLists;
        private GetStockMarketInfoPresenter getStockMarketInfoPresenter;
        private String symbol;
        private Timer timer;
        public ExChangeOneByOne() {
        }

        public static ExChangeOneByOne newInstance(String symbol) {
            ExChangeOneByOne fragment = new ExChangeOneByOne();
            Bundle args = new Bundle();
            args.putString("symbol", symbol);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
            return inflater.inflate(R.layout.layout_preview, container, false);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            if (getArguments() != null) {
                symbol = getArguments().getString("symbol");
            }
            super.onCreate(savedInstanceState);
        }
        @Override
        protected void onDestroyViewLazy() {
            super.onDestroyViewLazy();
            if (timer!=null){
                timer.cancel();
            }
        }
        @Override
        protected void onCreateViewLazy(Bundle savedInstanceState) {
            create(R.layout.fragment_exchange_one_by_one);
            super.onCreateViewLazy(savedInstanceState);
            View view = getRootView();
            setContentView(view);
            getStockMarketInfoPresenter = new GetStockMarketInfoPresenter(this, userHttpClient);
            initViews(view);
            initListener();
            if (timer != null) {
                timer.cancel();
            }
            timer = null;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getStockMarketInfo(symbol, null);
                }
            }, 0, 5000);
        }

        @Override
        public void initViews(View view) {
            super.initViews(view);
            recyclerView = view.findViewById(R.id.list);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            stockTransactionLists = new ArrayList<>();
            oneByOnedapter = new OneByOnedapter(context, this, stockTransactionLists, recyclerView);
            recyclerView.setAdapter(oneByOnedapter);
        }

        @Override
        public BaseModel requestCallBack(BaseModel baseModel) {
            if (baseModel instanceof MStockMarketInfoModel) {
                stockTransactionLists = (ArrayList<MStockMarketInfoModel.DataBean.StockTransactionList>) ((MStockMarketInfoModel) baseModel).getData().getStockTransactionList();
                if (oneByOnedapter != null) {
                    oneByOnedapter.updateData(stockTransactionLists);
                }
            }
            return super.requestCallBack(baseModel);
        }

        @Override
        public void onWholeClick(int position) {

        }

        @Override
        public void getStockMarketInfo(String symbol, Integer productOrderId) {
            getStockMarketInfoPresenter.getStockMarketInfo(symbol, null);
        }
    }
}
