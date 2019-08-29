package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public class MPreOrderModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"productTypeEnum":"PRODUCT_TYPE_DAY","validateTradingDay":2,"extendModelEnum":"EXTEND_MODEL_AUTOMATIC","margin":1000000,"fee":4050,"feeStr":"4.05元/交易日","feeHint":"预存两日费用","marginHint":"","amount":4000000,"warningLine":3500000,"stopLine":3300000,"validTradingDaysText":"2个交易日,自动续费","beginTradeDate":"2017-09-12","avialableCouponCount":8,"profitSharingRate":1000}
   */

  private DataBean data;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    /**
     * productTypeEnum : PRODUCT_TYPE_DAY
     * validateTradingDay : 2
     * extendModelEnum : EXTEND_MODEL_AUTOMATIC
     * margin : 1000000
     * fee : 4050
     * feeStr : 4.05元/交易日
     * feeHint : 预存两日费用
     * marginHint :
     * amount : 4000000
     * warningLine : 3500000
     * stopLine : 3300000
     * validTradingDaysText : 2个交易日,自动续费
     * beginTradeDate : 2017-09-12
     * avialableCouponCount : 8
     * profitSharingRate : 1000
     */

    private String productTypeEnum;
    private Integer validateTradingDay;
    private String extendModelEnum;
    private long margin;

    public long getDailyFee() {
      return dailyFee;
    }

    public void setDailyFee(long dailyFee) {
      this.dailyFee = dailyFee;
    }

    public long getTotalFee() {
      return totalFee;
    }

    public void setTotalFee(long totalFee) {
      this.totalFee = totalFee;
    }

    public String getFeeText() {
      return feeText;
    }

    public void setFeeText(String feeText) {
      this.feeText = feeText;
    }

    private long dailyFee;
    private long totalFee;
    private String feeText;
    private String feeHint;
    private String marginHint;
    private long amount;
    private long warningLine;
    private long stopLine;

    public String getProductTypeEnum() {
      return productTypeEnum;
    }

    public void setProductTypeEnum(String productTypeEnum) {
      this.productTypeEnum = productTypeEnum;
    }

    public Integer getValidateTradingDay() {
      return validateTradingDay;
    }

    public void setValidateTradingDay(Integer validateTradingDay) {
      this.validateTradingDay = validateTradingDay;
    }

    public String getExtendModelEnum() {
      return extendModelEnum;
    }

    public void setExtendModelEnum(String extendModelEnum) {
      this.extendModelEnum = extendModelEnum;
    }

    public long getMargin() {
      return margin;
    }

    public void setMargin(long margin) {
      this.margin = margin;
    }

    public String getFeeHint() {
      return feeHint;
    }

    public void setFeeHint(String feeHint) {
      this.feeHint = feeHint;
    }

    public String getMarginHint() {
      return marginHint;
    }

    public void setMarginHint(String marginHint) {
      this.marginHint = marginHint;
    }

    public long getAmount() {
      return amount;
    }

    public void setAmount(long amount) {
      this.amount = amount;
    }

    public long getWarningLine() {
      return warningLine;
    }

    public void setWarningLine(long warningLine) {
      this.warningLine = warningLine;
    }

    public long getStopLine() {
      return stopLine;
    }

    public void setStopLine(long stopLine) {
      this.stopLine = stopLine;
    }

    public String getValidTradingDaysText() {
      return validTradingDaysText;
    }

    public void setValidTradingDaysText(String validTradingDaysText) {
      this.validTradingDaysText = validTradingDaysText;
    }

    public String getBeginTradeDate() {
      return beginTradeDate;
    }

    public void setBeginTradeDate(String beginTradeDate) {
      this.beginTradeDate = beginTradeDate;
    }

    public Integer getAvialableCouponCount() {
      return avialableCouponCount;
    }

    public void setAvialableCouponCount(Integer avialableCouponCount) {
      this.avialableCouponCount = avialableCouponCount;
    }

    public long getProfitSharingRate() {
      return profitSharingRate;
    }

    public void setProfitSharingRate(long profitSharingRate) {
      this.profitSharingRate = profitSharingRate;
    }

    private String validTradingDaysText;
    private String beginTradeDate;
    private Integer avialableCouponCount;
    private long profitSharingRate;
    private String startTradingDate;

    public String getStartTradingDate() {
      return startTradingDate;
    }

    public void setStartTradingDate(String startTradingDate) {
      this.startTradingDate = startTradingDate;
    }

    public String getEndTradingDate() {
      return endTradingDate;
    }

    public void setEndTradingDate(String endTradingDate) {
      this.endTradingDate = endTradingDate;
    }

    private String endTradingDate;
    private ProductPositionLimitBean productPositionLimit;

    public ProductPositionLimitBean getProductPositionLimit() {
      return productPositionLimit;
    }

    public void setProductPositionLimit(ProductPositionLimitBean productPositionLimit) {
      this.productPositionLimit = productPositionLimit;
    }

    public static class ProductPositionLimitBean {
      private long minCapital;
      private long maxCapital;
      private int mainBoard;
      private int smallMediumBoard;
      private int growthEnterpriseBoard;
      private String description;

      public long getMinCapital() {
        return minCapital;
      }

      public void setMinCapital(long minCapital) {
        this.minCapital = minCapital;
      }

      public long getMaxCapital() {
        return maxCapital;
      }

      public void setMaxCapital(long maxCapital) {
        this.maxCapital = maxCapital;
      }

      public int getMainBoard() {
        return mainBoard;
      }

      public void setMainBoard(int mainBoard) {
        this.mainBoard = mainBoard;
      }

      public int getSmallMediumBoard() {
        return smallMediumBoard;
      }

      public void setSmallMediumBoard(int smallMediumBoard) {
        this.smallMediumBoard = smallMediumBoard;
      }

      public int getGrowthEnterpriseBoard() {
        return growthEnterpriseBoard;
      }

      public void setGrowthEnterpriseBoard(int growthEnterpriseBoard) {
        this.growthEnterpriseBoard = growthEnterpriseBoard;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }
    }

  }
}
