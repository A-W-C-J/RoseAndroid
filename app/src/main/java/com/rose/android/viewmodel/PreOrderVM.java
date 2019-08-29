package com.rose.android.viewmodel;

import android.util.Log;

import com.rose.android.model.MPreOrderModel;
import com.rose.android.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public class PreOrderVM {
  private static final String TAG = "PreOrderVM";
  private static PreOrderVM preOrderVM;
  private MPreOrderModel preOrderModel;

  private PreOrderVM() {
  }

  public static PreOrderVM getInstance() {
    if (preOrderVM == null) {
      preOrderVM = new PreOrderVM();
    }
    return preOrderVM;
  }

  public MPreOrderModel getPreOrderModel() {
    return preOrderModel;
  }

  public void setPreOrderModel(MPreOrderModel preOrderModel) {
    this.preOrderModel = preOrderModel;
  }

  public String getMargin() {
    if (preOrderModel != null) {
      return Utils.formatWithScale(Utils.divide1000(preOrderModel.getData().getMargin()), 2) + "元";
    } else {
      return 0 + "元";
    }
  }

  public String getFee() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getFeeText();
    } else {
      return "";
    }
  }

  public String getFeeHint() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getFeeHint();
    } else {
      return "";
    }
  }

  public String getMarginHint() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getMarginHint();
    } else {
      return "";
    }
  }

  public String getWarningLine() {
    if (preOrderModel != null) {
      return Utils.formatWithScale(Utils.divide1000(preOrderModel.getData().getWarningLine()), 2) + "元";
    } else {
      return 0 + "元";
    }
  }

  public String getStopLine() {
    if (preOrderModel != null) {
      return Utils.formatWithScale(Utils.divide1000(preOrderModel.getData().getStopLine()), 2) + "元";
    } else {
      return 0 + "元";
    }
  }

  public String getTradingDays() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getValidTradingDaysText();
    } else {
      return 0 + "个交易日，自动续费";
    }
  }

  public String getBeginDate() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getStartTradingDate();
    } else {
      return "";
    }
  }

  public String getCoupon() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getAvialableCouponCount() + "张可用";
    } else {
      return "";
    }
  }

  public int getCouponCount() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getAvialableCouponCount();
    } else {
      return 0;
    }
  }

  public String getCouponIsSelect() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getAvialableCouponCount() - 1 + "张可用";
    } else {
      return "";
    }
  }

  public String getPrice() {
    if (preOrderModel != null) {
      return Utils.formatWithThousandsSeparator(Utils.divide1000(preOrderModel.getData().getAmount()));
    } else {
      return "";
    }
  }

  public String getSharingRate() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getProfitSharingRate() / 10 + "%盈利分配";
    } else {
      return "";
    }
  }

  public String getTotalPayment() {
    if (preOrderModel != null) {
      Log.e(TAG, "getTotalPayment: " + (+preOrderModel.getData().getMargin()));
      BigDecimal bigDecimal = new BigDecimal(preOrderModel.getData().getTotalFee());
      BigDecimal bigDecimal1 = new BigDecimal(preOrderModel.getData().getMargin());
      return bigDecimal.add(bigDecimal1).divide(new BigDecimal(1000), 2, RoundingMode.DOWN).toString();
    }
    return "";
  }
  public String getDescription() {
    if (preOrderModel != null) {
      return preOrderModel.getData().getProductPositionLimit().getDescription();
    }
    return "";
  }
}
