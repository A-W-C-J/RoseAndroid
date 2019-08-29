package com.rose.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.rose.android.BuildConfig;
import com.rose.android.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static com.rose.android.ui.activity.BaseRechargeActivity.PayTypes.PAY_TYPE_QQ;
import static com.rose.android.ui.activity.BaseRechargeActivity.PayTypes.PAY_TYPE_WX;
import static com.rose.android.ui.activity.BaseRechargeActivity.PayTypes.PAY_TYPE_ZFB;

/**
 * Created by yangbin on 16/12/29.
 * 封装支付类
 */

@SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
abstract class BaseRechargeActivity extends BaseActivity {

  // 布局
  private static final int layoutId = R.layout.activity_recharge_base;

  /**
   * 支付方式:0微信支付，1银联支付，2支付宝支付（飞客），3QQ支付
   */
  static class PayTypes {
    /**
     * 微信支付
     */
    static final int PAY_TYPE_WX = 0;
    /**
     * 银联支付
     */
    static final int PAY_TYPE_YL = 2;
    /**
     * 支付宝支付
     */
    static final int PAY_TYPE_ZFB = 1;
    /**
     * QQ支付
     */
    static final int PAY_TYPE_QQ = 3;
    /**
     * 微信扫码
     */
    static final int PAY_TYPE_WXSM = 4;
    /**
     * 快捷支付（汇付宝）
     */
    static final int PAY_TYPE_KJ = 5;
    static final int PAY_ZFB_WAP = 6;

    static final int GFB_KJBOR = 7;
    static final int GFB_KJSAV = 8;
  }

  // 快捷支付
  private TextView[] mKJAmountTvs;

  public int[] getKjAmountInt() {
    return kjAmountInt;
  }

  public void setKjAmountInt(int[] kjAmountInt) {
    this.kjAmountInt = kjAmountInt;
  }

  /**
   * 快捷输入的值
   */
  public int[] kjAmountInt = {1000, 3000, 5000, 10000};
  private int[] kjResIds = {R.id.tv_rmb0, R.id.tv_rmb1, R.id.tv_rmb2, R.id.tv_rmb3};

  /**
   * 充值下限
   */
  public long mMinCoin = 500;

  /**
   * 输入框
   */
  private EditText mEditText;
  private ImageView mClearIV;

  // 支付方式
  private View[] mPayRls;
  private static final int[] rlIds = {R.id.rl_pay_zfb,R.id.rl_pay_weixin,  R.id.rl_pay_qq};
  private CheckBox[] mCheckBoxes;
  private static final int[] cbIds = {R.id.cb_zfb,R.id.cb_wx, R.id.cb_qq};
  private static final int[] types = { PAY_TYPE_ZFB, PAY_TYPE_WX, PAY_TYPE_QQ};

  /**
   * 设置支付方式
   */
  public List<Integer> mPayMapList = new ArrayList<>();

  /**
   * 选择的支付类型 index
   */
  private int mTypePostion = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    create(layoutId);
    setContentView(getRootView());
    if (BuildConfig.DEBUG) {
      mMinCoin = 0;
    }
    initEditText();
    initKuaiJieBtn();
    initPayTypeBtn();
    initViews();
    setTitle("入金");
  }

  @Override
  public void initViews() {
    super.initViews();
    {
      mClearIV = (ImageView) findViewById(R.id.clear);
      mClearIV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mEditText.setText("");// 清空输入框数据
          mClearIV.setVisibility(View.INVISIBLE);
        }
      });

      mEditText = (EditText) findViewById(R.id.et_rmb);
      mEditText.addTextChangedListener(new TextWatcher() {
        boolean deleteLastChar;// 是否需要删除末尾

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          if (s.toString().contains(".")) {
            // 如果点后面有超过三位数值,则删掉最后一位
            int length = s.length() - s.toString().lastIndexOf(".");
            // 说明后面有三位数值
            deleteLastChar = length >= 4;
          }
        }

        @Override
        public void afterTextChanged(Editable s) {
          mClearIV.setVisibility(s.length() == 0 ? View.INVISIBLE : View.VISIBLE);
          if (s == null) {
            return;
          }
          if (deleteLastChar) {
            // 设置新的截取的字符串
            mEditText.setText(s.toString().substring(0, s.toString().length() - 1));
            // 光标强制到末尾
            mEditText.setSelection(mEditText.getText().length());
          }
          // 以小数点开头，前面自动加上 "0"
          if (s.toString().startsWith(".")) {
            mEditText.setText("0" + s);
            mEditText.setSelection(mEditText.getText().length());
          }
        }
      });
      setEditTextValue(mMinCoin + "");

      // 快捷输入金额
      if (kjAmountInt == null || kjAmountInt.length == 0) {
        findViewById(R.id.ll_kj_rmb).setVisibility(View.GONE);
      } else {
        mKJAmountTvs = new TextView[4];
        for (int i = 0; i < 4; i++) {
          final int finalI = i;
          mKJAmountTvs[i] = (TextView) findViewById(kjResIds[i]);
          mKJAmountTvs[i].setText(kjAmountInt[i] + "元");
          mKJAmountTvs[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              setEditTextValue(kjAmountInt[finalI]+ "");
            }
          });
        }
      }

      // 支付方式
      if (mPayMapList.size() == 0) {
        findViewById(R.id.tv_select_pay_type).setVisibility(View.INVISIBLE);
        findViewById(R.id.sv_select_pay_type).setVisibility(View.INVISIBLE);
      } else {
        mPayRls = new View[mPayMapList.size()];
        mCheckBoxes = new CheckBox[mPayMapList.size()];
        for (int i = 0; i < types.length; i++) {
          for (int j = 0; j < mPayMapList.size(); j++) {
            if (types[i] == mPayMapList.get(j)) {
              final int finalJ = j;
              mPayRls[j] = findViewById(rlIds[i]);
              mCheckBoxes[j] = (CheckBox) findViewById(cbIds[i]);
              mCheckBoxes[j].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  updatePayRL(finalJ);
                }
              });
              mPayRls[j].setVisibility(View.VISIBLE);
              mPayRls[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  updatePayRL(finalJ);
                }
              });
              break;
            }
          }
        }
        updatePayRL(0);
      }

      // 支付按钮
      TextView onPayTV = (TextView) findViewById(R.id.tv_onpay);
      onPayTV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (checkString()) {
            onPayClickListenter(amt, mTypePostion);
          }
        }
      });

      findViewById(R.id.ll_recharge_base).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          // 隐藏软键盘
          imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
      });
    }
  }

  /**
   * 初始化最少充值金额
   */
  public abstract void initEditText();

  /**
   * 初始化快捷输入按钮(有默认值)
   */
  public abstract void initKuaiJieBtn();

  /**
   * 初始化支付方式
   */
  public abstract void initPayTypeBtn();

  /**
   * 点击事件
   */
  public abstract void onPayClickListenter(BigDecimal amt, int position);

  /**
   * 更新支付方式按钮
   *
   * @param position
   */
  private void updatePayRL(int position) {
    if (position == mTypePostion) {
      return;
    }

    if (mTypePostion != -1) {
      mCheckBoxes[mTypePostion].setChecked(false);
    }

    mTypePostion = position;
    mCheckBoxes[mTypePostion].setChecked(true);
  }

  /**
   * 设置值到输入框
   *
   * @param val
   */
  private void setEditTextValue(String val) {
    mEditText.setText(val);
    mEditText.setSelection(mEditText.getText().toString().trim().length());
    mClearIV.setVisibility(val.length() == 0 ? View.INVISIBLE : View.VISIBLE);
  }

  private BigDecimal amt;

  /**
   * 检测金额
   */
  private boolean checkString() {
    String fee = mEditText.getText().toString().trim();// 最小单位为元
    if (TextUtils.isEmpty(fee)) {
      Toast.makeText(this, "输入金额不能为空", Toast.LENGTH_SHORT).show();
      return false;
    }
    try {
      BigDecimal bigDecimal = new BigDecimal(fee);
      amt = bigDecimal.multiply(new BigDecimal(1000));
    } catch (NumberFormatException e) {
    }
    if (BuildConfig.DEBUG) {
      if (amt.longValue() < mMinCoin*1000) {
        Toast.makeText(this, "最少充值" + mMinCoin + "元", Toast.LENGTH_SHORT).show();
        return false;
      }
    }
    return true;
  }
}
