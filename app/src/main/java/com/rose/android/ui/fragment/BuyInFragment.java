package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.GetStockMarketInfoContract;
import com.rose.android.contract.PostOrderPositionsContract;
import com.rose.android.contract.PostStockOrderContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MOrderPositionsModel;
import com.rose.android.model.MStockMarketInfoModel;
import com.rose.android.model.MStockOrderModel;
import com.rose.android.presenter.GetStockMarketInfoPresenter;
import com.rose.android.presenter.PostOrderPositionsPresenter;
import com.rose.android.presenter.PostStockOrderPresenter;
import com.rose.android.ui.adapter.OrderPositionAdapter;
import com.rose.android.utils.Utils;
import com.rose.android.view.StockBuyOrSaleDialog;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.view.numbereditor.NumberEditText;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.annotations.Nullable;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class BuyInFragment extends BaseFragment implements PostStockOrderContract.View, PostOrderPositionsContract.View,
        ClickResponseListener, GetStockMarketInfoContract.View {
    private static final String ARG_PARAM1 = "productOrderId";
    private static final String ARG_PARAM3 = "symbol";
    private static final String ARG_PARAM2 = "orderId";
    private boolean isSale;
    private Button buyButton;
    private PostStockOrderPresenter postStockOrderPresenter;
    private EditText stockNum, buyCount;
    private TextView stockName, kmValue;
    private NumberEditText numberEditText;
    private TextView sale1, sale2, sale3, sale4, sale5, saleValue1, saleValue2, saleValue3, saleValue4, saleValue5, saleCount1, saleCount2,
            saleCount3, saleCount4, saleCount5, buy1, buy2, buy3, buy4, buy5, buyValue1, buyValue2, buyValue3, buyValue4, buyValue5, buyCount1, buyCount2,
            buyCount3, buyCount4, buyCount5, maxDown, maxUp, km;
    private TextView[] sales = new TextView[5];
    private TextView[] salesValue = new TextView[5];
    private TextView[] salesCount = new TextView[5];
    private TextView[] buys = new TextView[5];
    private TextView[] buysValue = new TextView[5];
    private TextView[] buysCount = new TextView[5];
    public int productOrderId;
    private String action = "BUY";
    private double min;
    private double max;
    private double currentValue;
    private static final String TAG = "BuyInFragment";
    private PostOrderPositionsPresenter postOrderPositionsPresenter;
    private GetStockMarketInfoPresenter getStockMarketInfoPresenter;
    private RecyclerView list;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private OrderPositionAdapter stockOrderListAdapter;
    private MOrderPositionsModel orderPositionsModel;
    private Timer timer;
    private Timer timer2;
    private int pageNo = 1, pageSize = 10;
    private ArrayList<MOrderPositionsModel.DataBean.ProductOrderPositionListBean> productOrderPositionListBeans;
    private MStockMarketInfoModel stockMarketInfoModel;
    private int realKm;
    private String symbol;

    public BuyInFragment() {
    }

    public static BuyInFragment newInstance(int param1, boolean param2, String symbol) {
        BuyInFragment fragment = new BuyInFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putBoolean(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productOrderId = getArguments().getInt(ARG_PARAM1);
            isSale = getArguments().getBoolean(ARG_PARAM2);
            symbol = getArguments().getString(ARG_PARAM3);
        }
    }

    public void updateProductId(int id) {
        productOrderId = id;
        Log.e(TAG, "updateProductId: " + id);
    }

    public void updateSymbol(String symbol) {
        this.symbol = symbol;
        Log.e(TAG, "updateSymbol: " + symbol);
        if (stockNum != null) {
            stockNum.setText(symbol);
        }
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        create(R.layout.fragment_buy_in);
        postOrderPositionsPresenter = new PostOrderPositionsPresenter(userHttpClient, this);
        postStockOrderPresenter = new PostStockOrderPresenter(this, userHttpClient);
        getStockMarketInfoPresenter = new GetStockMarketInfoPresenter(this, userHttpClient);
        View view = getRootView();
        setContentView(view);
        initViews(view);
        initListener();
    }
    @Override
    public void initViews(View view) {
        super.initViews(view);
        refreshLayout = view.findViewById(R.id.smart_refresh_layout);
        kmValue = (TextView) view.findViewById(R.id.km_value);
        list = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        productOrderPositionListBeans = new ArrayList<>();
        stockOrderListAdapter = new OrderPositionAdapter(list, productOrderPositionListBeans, this);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(stockOrderListAdapter);
        list.setAdapter(headerAndFooterRecyclerViewAdapter);
        buyButton = (Button) view.findViewById(R.id.btn_buy);
        if (isSale) {
            buyButton.setText("卖出");
            buyButton.setBackgroundResource(R.drawable.shape_green_btn);
            action = "SELL";
        }
        sale1 = (TextView) view.findViewById(R.id.tv_sale1);
        sale2 = (TextView) view.findViewById(R.id.tv_sale2);
        sale3 = (TextView) view.findViewById(R.id.tv_sale3);
        sale4 = (TextView) view.findViewById(R.id.tv_sale4);
        sale5 = (TextView) view.findViewById(R.id.tv_sale5);
        sales = new TextView[]{sale1, sale2, sale3, sale4, sale5};
        saleValue1 = (TextView) view.findViewById(R.id.tv_sale1_value);
        saleValue2 = (TextView) view.findViewById(R.id.tv_sale2_value);
        saleValue3 = (TextView) view.findViewById(R.id.tv_sale3_value);
        saleValue4 = (TextView) view.findViewById(R.id.tv_sale4_value);
        saleValue5 = (TextView) view.findViewById(R.id.tv_sale5_value);
        saleCount1 = (TextView) view.findViewById(R.id.tv_sale1_count);
        saleCount2 = (TextView) view.findViewById(R.id.tv_sale2_count);
        saleCount3 = (TextView) view.findViewById(R.id.tv_sale3_count);
        saleCount4 = (TextView) view.findViewById(R.id.tv_sale4_count);
        saleCount5 = (TextView) view.findViewById(R.id.tv_sale5_count);
        salesValue = new TextView[]{saleValue1, saleValue2, saleValue3, saleValue4, saleValue5};
        salesCount = new TextView[]{saleCount1, saleCount2, saleCount3, saleCount4, saleCount5};
        buy1 = (TextView) view.findViewById(R.id.tv_buy1);
        buy2 = (TextView) view.findViewById(R.id.tv_buy2);
        buy3 = (TextView) view.findViewById(R.id.tv_buy3);
        buy4 = (TextView) view.findViewById(R.id.tv_buy4);
        buy5 = (TextView) view.findViewById(R.id.tv_buy5);
        buyValue1 = (TextView) view.findViewById(R.id.tv_buy1_value);
        buyValue2 = (TextView) view.findViewById(R.id.tv_buy2_value);
        buyValue3 = (TextView) view.findViewById(R.id.tv_buy3_value);
        buyValue4 = (TextView) view.findViewById(R.id.tv_buy4_value);
        buyValue5 = (TextView) view.findViewById(R.id.tv_buy5_value);
        buyCount1 = (TextView) view.findViewById(R.id.tv_buy1_count);
        buyCount2 = (TextView) view.findViewById(R.id.tv_buy2_count);
        buyCount3 = (TextView) view.findViewById(R.id.tv_buy3_count);
        buyCount4 = (TextView) view.findViewById(R.id.tv_buy4_count);
        buyCount5 = (TextView) view.findViewById(R.id.tv_buy5_count);
        maxDown = (TextView) view.findViewById(R.id.max_down);
        maxUp = (TextView) view.findViewById(R.id.max_up);
        buys = new TextView[]{buy1, buy2, buy3, buy4, buy5};
        buysValue = new TextView[]{buyValue1, buyValue2, buyValue3, buyValue4, buyValue5};
        buysCount = new TextView[]{buyCount1, buyCount2, buyCount3, buyCount4, buyCount5};
        stockName = (TextView) view.findViewById(R.id.stock_name);
        numberEditText = (NumberEditText) view.findViewById(R.id.numberEditText);
        numberEditText.setHint("0.00");
        numberEditText.setZoom(new NumberEditText.Zoomer() {
            @Override
            public int scale() {
                return 2;
            }
        });
        numberEditText.setMinValue(0);
        numberEditText.setOnValueReachRangeListener(new NumberEditText.OnValueReachRangeListener() {
            @Override
            public void onValueReachMax(double input, double max) {

            }

            @Override
            public void onValueReachMin(double input, double min) {
                ToastWithIcon.init("输入的价格不得小于0").show();
            }
        });
        numberEditText.setNumberConvertor(value -> Utils.formatWithScale(value, 2));
        stockNum = (EditText) view.findViewById(R.id.stock_num);
        buyCount = (EditText) view.findViewById(R.id.buy_count);
        km = (TextView) view.findViewById(R.id.km);
        if (isSale) {
            buyCount.setHint("委卖数量");
            km.setText("可卖");
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        stockNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = null;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (productOrderId != 0)
                                getStockMarketInfo(s.toString(), productOrderId);
                        }
                    }, 0, 5000);
                } else {
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    removeTxt();
                }
            }
        });
        if (symbol != null) {
            Log.e(TAG, "initListener: " + symbol);
            stockNum.setText(((ExchangeCenterFragment) getParentFragment()).getSymbol());
        }
        numberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (BuyInFragment.this.getActivity() != null) {
                    if (s.length() > 0 && ((ExchangeCenterFragment) BuyInFragment.this.getParentFragment()).getAvailabelCash() > 0) {
                        long availabelCash = ((ExchangeCenterFragment) BuyInFragment.this.getParentFragment()).getAvailabelCash();
                        double curPrice = Double.parseDouble(s.toString());
                        if (!isSale) {
                            if (curPrice > 0) {
                                realKm = (Utils.divide1000(availabelCash).divide(new BigDecimal(curPrice), 10, RoundingMode.HALF_DOWN).divide(new BigDecimal(100), 10, RoundingMode.HALF_DOWN)).intValue() * 100;
                            }
                            if (realKm >= 0) {
                                kmValue.setText(String.valueOf(realKm));
                            } else {
                                kmValue.setText(String.valueOf(0));
                            }
                            if (realKm <= 0) {
                                buyCount.setFocusable(false);
                                buyCount.setFocusableInTouchMode(false);
                            } else {
                                buyCount.setFocusable(true);
                                buyCount.setFocusableInTouchMode(true);
                            }
                        }
                    }
                }
            }
        });
        buyCount.setOnClickListener(v -> {
            if (realKm <= 0) {
                if (!isSale) {
                    ToastWithIcon.init("可买入数量为0").show();
                } else {
                    ToastWithIcon.init("可卖出数量为0").show();
                }
            }
        });
        buyButton.setOnClickListener(v -> {
            if (numberEditText.getCurrentValue() <= min) {
                numberEditText.setCurrentValue(Double.valueOf(Utils.formatWithScale(min, 2)) + 0.01);
            }
            if (numberEditText.getCurrentValue() >= max) {
                numberEditText.setCurrentValue(Double.valueOf(Utils.formatWithScale(max, 2)) - 0.01);
            }
            if (numberEditText.getCurrentValue() <= 0) {
                ToastWithIcon.init("委托价格不得小于等于0").show();
            } else if (buyCount.getText().toString().length() <= 0 || Integer.parseInt(buyCount.getText().toString()) <= 0) {
                ToastWithIcon.init("委托数量不得小于100").show();
            } else if (Integer.parseInt(buyCount.getText().toString()) > realKm) {
                buyCount.setText(String.valueOf(realKm));
            } else if (Integer.parseInt(buyCount.getText().toString()) % 100 != 0) {
                ToastWithIcon.init("委托数量必须为100的倍数").show();
            } else {
                StockBuyOrSaleDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        postStockOrder(Integer.parseInt(buyCount.getText().toString()), stockNum.getText().toString(),
                                action, (long) (numberEditText.getCurrentValue() * 1000), productOrderId);
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, isSale ? "确认卖出" : "确认买入", null, isSale ? "委托卖出确认" : "委托买入确认", stockNum.getText().toString(), stockName.getText().toString(), Utils.formatWithScale(numberEditText.getCurrentValue(), 2), buyCount.getText().toString()).show(BuyInFragment.this.getChildFragmentManager(), "dialog");
            }
        });
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            pageSize = 10;
            pageNo = 1;
            postOrderPositions(pageNo, pageSize, String.valueOf(productOrderId));
        });
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            ++pageNo;
            postOrderPositions(pageNo, pageSize, String.valueOf(productOrderId));
        });
    }

    private void removeTxt() {
        for (int i = 0; i < sales.length; i++) {
            salesValue[i].setText("- -");
            salesCount[i].setText("- -");
            buysValue[i].setText("- -");
            buysCount[i].setText("- -");
            stockName.setText("");
            numberEditText.setCurrentValue(0.00);
            min = 0;
            max = 0;
        }
        buyCount.setText("");
        maxDown.setText("");
        maxUp.setText("");
    }

    @Override
    public void postStockOrder(int totalQuantity, String symbole, String action, long limitPrice, int productOrderId) {
        if (UserInfoManager.getInstance().getLoginStatus()) {
            showLoadDialog();
            postStockOrderPresenter.postStockOrder(totalQuantity, symbole, action, limitPrice, productOrderId);
        }
    }

    @Override
    public void getStockMarketInfo(String symbol, @Nullable Integer productOrderId) {
        if (UserInfoManager.getInstance().getLoginStatus())
            getStockMarketInfoPresenter.getStockMarketInfo(symbol, productOrderId);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MStockOrderModel) {
            ((ExchangeCenterFragment) this.getParentFragment()).updateAvailabelCash();
            ToastWithIcon.init("委托成功！").show();
            Log.e(TAG, "requestCallBack: " + baseModel.getMsg() + baseModel.getCode());
            ((ExchangeCenterFragment) this.getParentFragment()).changPage();
        } else if (baseModel instanceof MStockMarketInfoModel) {
            stockMarketInfoModel = (MStockMarketInfoModel) baseModel;
            stockName.setText(stockMarketInfoModel.getData().getName());
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
                buysValue[i].setText(String.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getBuyLevelPrice().get(size - 1 - i).getPrice()), 2)));
                buysCount[i].setText(Utils.addUnitWhenBeyondThousand(new BigDecimal(stockMarketInfoModel.getData().getBuyLevelPrice().get(size - 1 - i).getHandAmount())));
            }
            for (int i = 0; i < stockMarketInfoModel.getData().getSellLevelPrice().size(); i++) {
                sales[i].setText(stockMarketInfoModel.getData().getSellLevelPrice().get(i).getLevel());
                salesValue[i].setText(String.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getSellLevelPrice().get(i).getPrice()), 2)));
                salesCount[i].setText(Utils.addUnitWhenBeyondThousand(new BigDecimal(stockMarketInfoModel.getData().getSellLevelPrice().get(i).getHandAmount())));
            }
            if (numberEditText.getCurrentValue() == 0) {
                if (isSale) {
                    try {
                        numberEditText.setCurrentValue(Double.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getBuyLevelPrice().get(0).getPrice()), 2)));
                    } catch (NumberFormatException e) {
                        numberEditText.setCurrentValue(0.00);
                    }
                } else {
                    try {
                        numberEditText.setCurrentValue(Double.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getSellLevelPrice().get(0).getPrice()), 2)));
                    } catch (NumberFormatException e) {
                        numberEditText.setCurrentValue(0.00);
                    }
                }
            }
            maxUp.setText(String.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getLimitUpPrice()), 2)));
            maxDown.setText(String.valueOf(Utils.formatWithScale(Utils.divide1000(stockMarketInfoModel.getData().getLimitDownPrice()), 2)));
            min = Utils.divide1000(stockMarketInfoModel.getData().getLimitDownPrice()).doubleValue();
            max = Utils.divide1000(stockMarketInfoModel.getData().getLimitUpPrice()).doubleValue();
            currentValue = stockMarketInfoModel.getData().getCurrentPrice();
            if (isSale) {
                realKm = stockMarketInfoModel.getData().getAvailableSellVolumn();
                kmValue.setText(String.valueOf(realKm));
                if (realKm == 0) {
                    buyCount.setFocusable(false);
                    buyCount.setFocusableInTouchMode(false);
                } else {
                    buyCount.setFocusable(true);
                    buyCount.setFocusableInTouchMode(true);
                }
            }
        } else if (baseModel instanceof MOrderPositionsModel) {
            if (pageNo > 1) {
                orderPositionsModel.getData().getProductOrderPositionList().addAll(((MOrderPositionsModel) baseModel).getData().getProductOrderPositionList());
                refreshLayout.finishLoadmore(true);
            } else {
                orderPositionsModel = (MOrderPositionsModel) baseModel;
                refreshLayout.finishRefresh(true);
            }
            productOrderPositionListBeans = (ArrayList<MOrderPositionsModel.DataBean.ProductOrderPositionListBean>) orderPositionsModel.getData().getProductOrderPositionList();
            stockOrderListAdapter.updateData(productOrderPositionListBeans);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    public void onWholeClick(int position) {
        stockNum.setText(stockOrderListAdapter.getSymbol(position));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onResumeLazy();
        }
    }

    @Override
    public void onPauseLazy() {
        super.onPauseLazy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
        if (postOrderPositionsPresenter != null) {
            postOrderPositionsPresenter.dispose();
        }
        if (postStockOrderPresenter != null) {
            postStockOrderPresenter.dispose();
        }
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
        if (productOrderId != 0) {
            if (UserInfoManager.getInstance().getLoginStatus())
                showLoadDialog();
        }
        pageNo = 1;
        pageSize = 10;
        if (timer2 == null) {
            timer2 = new Timer();
        }
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (productOrderId != 0)
                    postOrderPositions(pageNo, pageSize, String.valueOf(productOrderId));
            }
        }, 0, 5000);
    }

    @Override
    public void postOrderPositions(int pageNo, int pageSize, String productOrderId) {
        if (UserInfoManager.getInstance().getLoginStatus())
            postOrderPositionsPresenter.postOrderPositions(pageNo, pageSize, productOrderId);
    }

}
