package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/22.
 */

public class MOrderPositionsModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"productOrderPositionList":[{"name":"悦达投资","symbol":"600805","marketValue":1540000,"profit":0,"totalPosition":null,"availablePosition":100,"averagePrice":7700,"currentPrice":7700}]}
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
    private List<ProductOrderPositionListBean> productOrderPositionList;

    public List<ProductOrderPositionListBean> getProductOrderPositionList() {
      return productOrderPositionList;
    }

    public void setProductOrderPositionList(List<ProductOrderPositionListBean> productOrderPositionList) {
      this.productOrderPositionList = productOrderPositionList;
    }

    public static class ProductOrderPositionListBean {
      /**
       * name : 悦达投资
       * symbol : 600805
       * marketValue : 1540000
       * profit : 0
       * totalPosition : null
       * availablePosition : 100
       * averagePrice : 7700
       * currentPrice : 7700
       */

      private String name;
      private String symbol;
      private long marketValue;
      private double profit;
      private Integer totalPosition;
      private long availablePosition;
      private long averagePrice;
      private long currentPrice;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getSymbol() {
        return symbol;
      }

      public void setSymbol(String symbol) {
        this.symbol = symbol;
      }

      public long getMarketValue() {
        return marketValue;
      }

      public void setMarketValue(long marketValue) {
        this.marketValue = marketValue;
      }

      public double getProfit() {
        return profit;
      }

      public void setProfit(double profit) {
        this.profit = profit;
      }

      public Integer getTotalPosition() {
        return totalPosition;
      }

      public void setTotalPosition(Integer totalPosition) {
        this.totalPosition = totalPosition;
      }

      public long getAvailablePosition() {
        return availablePosition;
      }

      public void setAvailablePosition(long availablePosition) {
        this.availablePosition = availablePosition;
      }

      public long getAveragePrice() {
        return averagePrice;
      }

      public void setAveragePrice(long averagePrice) {
        this.averagePrice = averagePrice;
      }

      public long getCurrentPrice() {
        return currentPrice;
      }

      public void setCurrentPrice(long currentPrice) {
        this.currentPrice = currentPrice;
      }
    }
  }
}
