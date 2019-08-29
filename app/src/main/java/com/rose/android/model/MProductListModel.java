package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/6.
 */

public class MProductListModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"productList":[{"id":1,"name":"天天盈","minLoan":2000000,"maxLoan":5000000000,"warningRate":500,"stopRate":300,"validTradingDays":2,"iconUri":"\u2018\u2019","billboardUri":"\u2018\u2019","extendMode":"自动续约","items":[{"id":43,"multiple":3,"profitShareRate":null,"dailyFeeRate":1350},{"id":44,"multiple":4,"profitShareRate":null,"dailyFeeRate":1620},{"id":45,"multiple":5,"profitShareRate":null,"dailyFeeRate":1800},{"id":46,"multiple":6,"profitShareRate":null,"dailyFeeRate":1980},{"id":47,"multiple":7,"profitShareRate":null,"dailyFeeRate":2160},{"id":48,"multiple":8,"profitShareRate":null,"dailyFeeRate":2340}],"minMutiple":3,"maxMutiple":8,"description":"2000元起，按天计费"},{"id":2,"name":"周周盈","minLoan":2000000,"maxLoan":5000000000,"warningRate":500,"stopRate":300,"validTradingDays":5,"iconUri":"","billboardUri":"","extendMode":"自动续约","items":[{"id":49,"multiple":3,"profitShareRate":null,"dailyFeeRate":1080},{"id":50,"multiple":4,"profitShareRate":null,"dailyFeeRate":1296},{"id":51,"multiple":5,"profitShareRate":null,"dailyFeeRate":1440},{"id":52,"multiple":6,"profitShareRate":null,"dailyFeeRate":1584},{"id":53,"multiple":7,"profitShareRate":null,"dailyFeeRate":1728},{"id":54,"multiple":8,"profitShareRate":null,"dailyFeeRate":1872}],"minMutiple":3,"maxMutiple":8,"description":"2000元起，按周计费"},{"id":3,"name":"月月盈","minLoan":2000000,"maxLoan":5000000000,"warningRate":500,"stopRate":300,"validTradingDays":20,"iconUri":"","billboardUri":"","extendMode":"自动续约","items":[{"id":55,"multiple":3,"profitShareRate":null,"dailyFeeRate":855},{"id":56,"multiple":4,"profitShareRate":null,"dailyFeeRate":900},{"id":57,"multiple":5,"profitShareRate":null,"dailyFeeRate":990},{"id":58,"multiple":6,"profitShareRate":null,"dailyFeeRate":1100}],"minMutiple":3,"maxMutiple":6,"description":"2000元起，按月计费"},{"id":4,"name":"互惠盈","minLoan":1000000,"maxLoan":1000000000,"warningRate":500,"stopRate":300,"validTradingDays":2,"iconUri":"","billboardUri":"","extendMode":"不可续约","items":[{"id":59,"multiple":6,"profitShareRate":null,"dailyFeeRate":0},{"id":60,"multiple":7,"profitShareRate":null,"dailyFeeRate":0},{"id":61,"multiple":8,"profitShareRate":null,"dailyFeeRate":0},{"id":62,"multiple":9,"profitShareRate":null,"dailyFeeRate":0},{"id":63,"multiple":10,"profitShareRate":null,"dailyFeeRate":0}],"minMutiple":6,"maxMutiple":10,"description":"1000元起，免管理费"}]}
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
    private List<ProductListBean> productList;

    public List<ProductListBean> getProductList() {
      return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
      this.productList = productList;
    }

    public static class ProductListBean {
      /**
       * id : 1
       * name : 天天盈
       * minLoan : 2000000
       * maxLoan : 5000000000
       * warningRate : 500
       * stopRate : 300
       * validTradingDays : 2
       * iconUri : ‘’
       * billboardUri : ‘’
       * extendMode : 自动续约
       * items : [{"id":43,"multiple":3,"profitShareRate":null,"dailyFeeRate":1350},{"id":44,"multiple":4,"profitShareRate":null,"dailyFeeRate":1620},{"id":45,"multiple":5,"profitShareRate":null,"dailyFeeRate":1800},{"id":46,"multiple":6,"profitShareRate":null,"dailyFeeRate":1980},{"id":47,"multiple":7,"profitShareRate":null,"dailyFeeRate":2160},{"id":48,"multiple":8,"profitShareRate":null,"dailyFeeRate":2340}]
       * minMutiple : 3
       * maxMutiple : 8
       * description : 2000元起，按天计费
       */

      private int id;
      private String name;
      private long minLoan;
      private long maxLoan;
      private int warningRate;
      private int stopRate;
      private int validTradingDays;
      private String iconUri;
      private String billboardUri;
      private String extendMode;
      private int minMutiple;
      private int maxMutiple;
      private String description;
      private List<ItemsBean> items;
      private int involvedUsersCount;

      public String getMarginHint() {
        return marginHint;
      }

      public void setMarginHint(String marginHint) {
        this.marginHint = marginHint;
      }

      private String marginHint;

      public int getInvolvedUsersCount() {
        return involvedUsersCount;
      }

      public void setInvolvedUsersCount(int involvedUsersCount) {
        this.involvedUsersCount = involvedUsersCount;
      }

      public String getValidTradingDaysText() {
        return validTradingDaysText;
      }

      public void setValidTradingDaysText(String validTradingDaysText) {
        this.validTradingDaysText = validTradingDaysText;
      }

      private String validTradingDaysText;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public long getMinLoan() {
        return minLoan;
      }

      public void setMinLoan(long minLoan) {
        this.minLoan = minLoan;
      }

      public long getMaxLoan() {
        return maxLoan;
      }

      public void setMaxLoan(long maxLoan) {
        this.maxLoan = maxLoan;
      }

      public int getWarningRate() {
        return warningRate;
      }

      public void setWarningRate(int warningRate) {
        this.warningRate = warningRate;
      }

      public int getStopRate() {
        return stopRate;
      }

      public void setStopRate(int stopRate) {
        this.stopRate = stopRate;
      }

      public int getValidTradingDays() {
        return validTradingDays;
      }

      public void setValidTradingDays(int validTradingDays) {
        this.validTradingDays = validTradingDays;
      }

      public String getIconUri() {
        return iconUri;
      }

      public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
      }

      public String getBillboardUri() {
        return billboardUri;
      }

      public void setBillboardUri(String billboardUri) {
        this.billboardUri = billboardUri;
      }

      public String getExtendMode() {
        return extendMode;
      }

      public void setExtendMode(String extendMode) {
        this.extendMode = extendMode;
      }

      public int getMinMutiple() {
        return minMutiple;
      }

      public void setMinMutiple(int minMutiple) {
        this.minMutiple = minMutiple;
      }

      public int getMaxMutiple() {
        return maxMutiple;
      }

      public void setMaxMutiple(int maxMutiple) {
        this.maxMutiple = maxMutiple;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }

      public List<ItemsBean> getItems() {
        return items;
      }

      public void setItems(List<ItemsBean> items) {
        this.items = items;
      }

      public static class ItemsBean {
        /**
         * id : 43
         * multiple : 3
         * profitShareRate : null
         * dailyFeeRate : 1350
         */

        private int id;
        private int multiple;
        private int dailyFeeRate;

        public int getId() {
          return id;
        }

        public void setId(int id) {
          this.id = id;
        }

        public int getMultiple() {
          return multiple;
        }

        public void setMultiple(int multiple) {
          this.multiple = multiple;
        }

//        public Object getProfitShareRate() {
//          return profitShareRate;
//        }
//
//        public void setProfitShareRate(Object profitShareRate) {
//          this.profitShareRate = profitShareRate;
//        }

        public int getDailyFeeRate() {
          return dailyFeeRate;
        }

        public void setDailyFeeRate(int dailyFeeRate) {
          this.dailyFeeRate = dailyFeeRate;
        }
      }
    }
  }
}
