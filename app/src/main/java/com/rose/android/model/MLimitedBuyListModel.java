package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/19.
 */

public class MLimitedBuyListModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"limitedStockList":[{"name":"蓝焰控股","code":"000968"},{"name":"神火股份","code":"000933"},{"name":"藏格控股","code":"000408"},{"name":"民生银行","code":"600016"},{"name":"第一创业002797","code":"002797"},{"name":"九典制药","code":"300705"},{"name":"北京科锐","code":"002350"},{"name":"西南证券","code":"600369"},{"name":"万家文化","code":"600576"},{"name":"珠海港","code":"000507"},{"name":"方大碳素","code":"600516"},{"name":"亚士创能","code":"603378"},{"name":"金鸿顺","code":"603922"},{"name":"天目湖","code":"603136"},{"name":"石化机械","code":"00852"}]}
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
    private List<LimitedStockListBean> limitedStockList;

    public List<LimitedStockListBean> getLimitedStockList() {
      return limitedStockList;
    }

    public void setLimitedStockList(List<LimitedStockListBean> limitedStockList) {
      this.limitedStockList = limitedStockList;
    }

    public static class LimitedStockListBean {
      /**
       * name : 蓝焰控股
       * code : 000968
       */

      private String name;
      private String code;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getCode() {
        return code;
      }

      public void setCode(String code) {
        this.code = code;
      }
    }
  }
}
