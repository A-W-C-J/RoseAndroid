package com.rose.android.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lantouzi.wheelview.WheelView;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.GetPreOrderContract;
import com.rose.android.contract.GetProductListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MPreOrderModel;
import com.rose.android.model.MProductListModel;
import com.rose.android.presenter.GetPreOrderPresenter;
import com.rose.android.presenter.GetProductListPresenter;
import com.rose.android.utils.Utils;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.ProductListVM;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public class ApplicationContractFragment extends BaseFragment implements GetPreOrderContract.View, GetProductListContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private String mParam2;
    private MProductListModel productListModel;
    private RelativeLayout rateParent;
    private EditText editText;
    private String cash;
    private int temp = 100;
    private int productItemId;
    private boolean hasChecked;
    private long min = 2000 * 1000;
    private long max = 5000000 * 1000;
    private Button nextBtn;
    private GetPreOrderPresenter getPreOrderPresenter;
    private long loan = 0l;
    private TextInputLayout textInputLayout;
    private WheelView wheelView;
    private List<String> items = new ArrayList<>();
    private boolean hasEdit;
    private TextView[] rateButtons;
    private static final String TAG = "ApplicationContractFrag";
    private GetProductListPresenter getProductListPresenter;
    private WheelViewHandler wheelViewHandler;
    private long maxLoan;
    private Runnable runnable;

    public ApplicationContractFragment() {
        //do nothing
    }

    public static ApplicationContractFragment newInstance(int position, String param2) {
        ApplicationContractFragment fragment = new ApplicationContractFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        updateData();
    }

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        create(R.layout.fragment_application_contract);
        getPreOrderPresenter = new GetPreOrderPresenter(this, userHttpClient);
        getProductListPresenter = new GetProductListPresenter(userHttpClient, this);
        View view = getRootView();
        setContentView(view);
        initViews(view);
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (getPreOrderPresenter != null) {
            getPreOrderPresenter.dispose();
            getPreOrderPresenter = null;
        }
        if (wheelViewHandler != null) {
            wheelViewHandler.removeCallbacks(runnable);
        }
    }

    public void setDefaultSelect() {
        if (editText != null) {
            editText.setText(String.valueOf(min / 1000));
        }
    }

    public void updateData() {
        if (getUserVisibleHint()) {
            int type = 1;
            getProductList(type);
        }
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        nextBtn = view.findViewById(R.id.btn_next);
        rateParent = view.findViewById(R.id.rl_rate);
        editText = view.findViewById(R.id.et_edit);
        textInputLayout = view.findViewById(R.id.text_input);
        wheelView = view.findViewById(R.id.horizontalscaleView);
        productListModel = ProductListVM.getInstance().getProductListModel();
        wheelView.setItems(items);
        wheelViewHandler = new WheelViewHandler(ApplicationContractFragment.this);
        runnable = () -> {
            if (items != null) {
                items.clear();
                for (int i = 0; i <= maxLoan / 1000 / 1000; i++) {
                    items.add(String.valueOf(i * 1000));
                }
                Message message = new Message();
                message.what = productListModel.getData().getProductList().get(mParam1).getId();
                wheelViewHandler.sendMessage(message);
            }
        };
        sendMessageToUpdate();
        initRateView(mParam1);
        initListener();
    }

    private static class WheelViewHandler extends Handler {
        WeakReference<ApplicationContractFragment> weakReference;

        WheelViewHandler(ApplicationContractFragment activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference != null && weakReference.get() != null &&
                    weakReference.get().productListModel != null && weakReference.get().productListModel.getData() != null
                    && weakReference.get().productListModel.getData().getProductList() != null &&
                    msg.what == weakReference.get().productListModel.getData().getProductList().get(weakReference.get().mParam1).getId()) {
                assert weakReference.get().wheelView != null;
                weakReference.get().wheelView.setItems(weakReference.get().items);
                if (weakReference.get().editText != null) {
                    weakReference.get().editText.setText(String.valueOf(weakReference.get().min / 1000));
                }
            }
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        nextBtn.setOnClickListener(v -> {
            if (loan != 0 && hasChecked && loan >= min && loan <= max) {
                if (loan % 1000000 == 0) {
                    getPreOrder(productItemId, loan);
                } else {
                    ToastWithIcon.init("申请金额必须为1000的倍数").show();
                }
            } else if (loan < min) {
                ToastWithIcon.init("申请金额不能小于" + min / 1000 + "元").show();
            } else if (loan > max) {
                ToastWithIcon.init("申请金额不能大于" + max / 1000 / 10000 + "万元").show();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cash = editText.getText().toString();
                temp = 100;
                hasChecked = false;
                initRateView(mParam1);
                hasEdit = true;
                wheelView.selectIndex(items.indexOf(editText.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    loan = Utils.multiply1000(new BigDecimal(editText.getText().toString()).longValue()).longValue();
                } catch (NumberFormatException e) {
                    loan = 0l;
                }
            }
        });
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                InputMethodManager inputMethodManager = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                if (loan != 0 && hasChecked && loan >= min && loan <= max) {
                    if (loan % 1000000 == 0) {
                        getPreOrder(productItemId, loan);
                    } else {
                        ToastWithIcon.init("申请金额必须为1000的倍数").show();
                    }
                } else if (loan < min) {
                    ToastWithIcon.init("申请金额不能小于" + min / 1000 + "元").show();
                } else if (loan > max) {
                    ToastWithIcon.init("申请金额不能大于" + max / 1000 / 10000 + "万元").show();
                }
                return true;
            }
            return false;
        });
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(WheelView wheelView, int position) {
                if (!hasEdit) {
                    editText.setText(wheelView.getItems().get(position));
                }
                hasEdit = false;
            }

            @Override
            public void onWheelItemSelected(WheelView wheelView, int position) {
                //do nothing
            }
        });
        setDefaultSelect();
    }

    private void initRateView(int position) {
        min = productListModel.getData().getProductList().get(position).getMinLoan();
        max = productListModel.getData().getProductList().get(position).getMaxLoan();
        editText.setHint(String.format(Locale.CHINA, "%d元起配，最高%d万元", min / 1000, max / 1000 / 10000));
        int rateCount = productListModel.getData().getProductList().get(position).getItems().size();
        if (rateButtons == null) {
            rateButtons = new TextView[rateCount];
        }
        for (int j = 0; j < rateCount; j++) {
            if (rateButtons[j] == null) {
                rateButtons[j] = new TextView(context);
            }
            rateButtons[j].setId(j + 100);
            rateButtons[j].setBackgroundResource(R.drawable.shape_contract_item);
            rateButtons[j].setGravity(Gravity.CENTER);
            rateButtons[j].setTextColor(ContextCompat.getColor(context, R.color.title_color_2));
            String str = "杠杆本金";
            if (cash != null && cash.length() > 0 && Integer.parseInt(cash) != 0 && Long.parseLong(cash) >= min / 1000 && Long.parseLong(cash) <= max / 1000) {
                rateButtons[0].setBackgroundResource(R.drawable.shape_contract_item_select);
                rateButtons[0].setTextColor(ContextCompat.getColor(context, R.color.bg_color_2));
                temp = 0;
                productItemId = productListModel.getData().getProductList().get(position).getItems().get(0).getId();
                hasChecked = true;
                nextBtn.setText(String.format("支付本金%s马上申请 >>", Utils.formatWithThousandsSeparatorWithoutScale(Double.valueOf(cash) /
                        productListModel.getData().getProductList().get(position).getItems().get(0).getMultiple()) + "元，"));
            }
            rateButtons[j].setText(String.format(Locale.CHINA, "%d倍%n%s", productListModel.getData().getProductList().get(position).getItems().get(j).getMultiple(), str));
            RelativeLayout.LayoutParams rateLp = new RelativeLayout.LayoutParams((Utils.getWidth() - (24 * 4)) / 3, Utils.getHeight() / 100 * 12);
            rateLp.setMargins(24, 12, 0, 12);
            rateLp.addRule(RelativeLayout.BELOW, j + 100 - 3);
            if (j % 3 == 0) {
                rateLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            } else {
                rateLp.addRule(RelativeLayout.RIGHT_OF, j + 99);
            }
            if (rateButtons[j].getParent() == null) {
                rateParent.addView(rateButtons[j], j, rateLp);
            } else {
                ((ViewGroup) rateButtons[j].getParent()).removeView(rateButtons[j]);
                rateParent.addView(rateButtons[j], j, rateLp);
            }
            int finalJ = j;
            rateButtons[j].setOnClickListener(v -> {
                productItemId = productListModel.getData().getProductList().get(position).getItems().get(finalJ).getId();
                hasChecked = true;
                if (finalJ != temp) {
                    rateButtons[finalJ].setBackgroundResource(R.drawable.shape_contract_item_select);
                    rateButtons[finalJ].setTextColor(ContextCompat.getColor(context, R.color.bg_color_2));
                    if (cash != null && !cash.isEmpty()) {
                        nextBtn.setText(String.format("支付本金%s马上申请 >>", Utils.formatWithThousandsSeparatorWithoutScale(Double.valueOf(cash) /
                                productListModel.getData().getProductList().get(position).getItems().get(finalJ).getMultiple()) + "元，"));
                    } else {
                        nextBtn.setText("支付本金马上申请 >>");
                    }
                    if (temp < rateButtons.length) {
                        rateButtons[temp].setBackgroundResource(R.drawable.shape_contract_item);
                        rateButtons[temp].setTextColor(ContextCompat.getColor(context, R.color.title_color_2));
                    }
                    temp = finalJ;
                }
            });
        }
    }

    @Override
    public void getPreOrder(int productItemId, long loan) {
        showLoadDialog();
        getPreOrderPresenter.getPreOrder(productItemId, loan);
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MPreOrderModel) {
            ARouter.getInstance().build(RouterConfig.contractDetails).withString("orderName", productListModel.getData().getProductList().get(mParam1).getName())
                    .withInt("orderId", productItemId)
                    .withInt("productId", productListModel.getData().getProductList().get(mParam1).getId())
                    .withInt("position", mParam1)
                    .withBoolean("isActivity", false).withLong("loan", loan).navigation();
        } else if (baseModel instanceof MProductListModel) {
            ProductListVM.getInstance().setProductListModel((MProductListModel) baseModel);
            productListModel = ProductListVM.getInstance().getProductListModel();
            sendMessageToUpdate();
            initRateView(mParam1);
        }
        return super.requestCallBack(baseModel);
    }

    private void sendMessageToUpdate() {
        maxLoan = productListModel.getData().getProductList().get(mParam1).getMaxLoan();
        if (wheelViewHandler != null) {
            wheelViewHandler.post(runnable);
        }
    }

    @Override
    public void getProductList(int type) {
        if (getProductListPresenter != null) {
            getProductListPresenter.getProductList(type);
        }
    }
}
