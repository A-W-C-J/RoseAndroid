package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/21.
 */

public class MStockOrderListModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"stockOrderList":[{"id":17,"orderNo":"1505815596596056","symbol":"002102","securitiesAccountNo":"1505700583394596","orderType":"LMT","action":"BUY","limitPrice":3980,"volume":0,"status":20,"tradeTime":"2017-09-19 18:06:36"},{"id":19,"orderNo":"1505816693481097","symbol":"002102","securitiesAccountNo":"1505700583394596","orderType":"LMT","action":"BUY","limitPrice":3980,"volume":200,"status":30,"tradeTime":"2017-09-19 18:24:53"}]}
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
    private List<StockOrderListBean> stockOrderList;

    public List<StockOrderListBean> getStockOrderList() {
      return stockOrderList;
    }

    public void setStockOrderList(List<StockOrderListBean> stockOrderList) {
      this.stockOrderList = stockOrderList;
    }

    public static class StockOrderListBean {
      /**
       * id : 17
       * orderNo : 1505815596596056
       * symbol : 002102
       * securitiesAccountNo : 1505700583394596
       * orderType : LMT
       * action : BUY
       * limitPrice : 3980
       * volume : 0
       * status : 20
       * tradeTime : 2017-09-19 18:06:36
       */

      private int id;
      private String orderNo;
      private String symbol;
      private String securitiesAccountNo;
      private String orderType;
      private int action;
      private long limitPrice;
      private int volume;
      private int totalQuantity;
      private int status;
      private String tradeTime;
      private boolean hasSelect;
      private String name;

      public long getAveragePrice() {
        return averagePrice;
      }

      public void setAveragePrice(long averagePrice) {
        this.averagePrice = averagePrice;
      }

      private long averagePrice;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public String getOrderNo() {
        return orderNo;
      }

      public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
      }

      public String getSymbol() {
        return symbol;
      }

      public void setSymbol(String symbol) {
        this.symbol = symbol;
      }

      public String getSecuritiesAccountNo() {
        return securitiesAccountNo;
      }

      public void setSecuritiesAccountNo(String securitiesAccountNo) {
        this.securitiesAccountNo = securitiesAccountNo;
      }

      public String getOrderType() {
        return orderType;
      }

      public void setOrderType(String orderType) {
        this.orderType = orderType;
      }

      public int getAction() {
        return action;
      }

      public void setAction(int action) {
        this.action = action;
      }

      public long getLimitPrice() {
        return limitPrice;
      }

      public void setLimitPrice(long limitPrice) {
        this.limitPrice = limitPrice;
      }

      public int getVolume() {
        return volume;
      }

      public void setVolume(int volume) {
        this.volume = volume;
      }

      public int getStatus() {
        return status;
      }

      public void setStatus(int status) {
        this.status = status;
      }

      public String getTradeTime() {
        return tradeTime;
      }

      public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
      }

      public boolean isHasSelect() {
        return hasSelect;
      }

      public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public int getTotalQuantity() {
        return totalQuantity;
      }

      public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
      }
    }
  }
}
