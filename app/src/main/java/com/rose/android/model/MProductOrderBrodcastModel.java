package com.rose.android.model;

import java.util.List;

/**
 * Created by wenen on 2017/12/6.
 */

public class MProductOrderBrodcastModel extends BaseModel {

  /**
   * data : {"productOrderBrodcastList":[{"broadcastText":"用户176xxxxx655申请了天天赚合约2666元","timeText":"60秒前"},{"broadcastText":"用户136xxxxx606申请了月月升合约2666元","timeText":"60秒前"},{"broadcastText":"用户136xxxxx606申请了月月升合约2500元","timeText":"60秒前"},{"broadcastText":"用户135xxxxx724申请了天天赚合约2666元","timeText":"60秒前"}]}
   */

  private DataBean data;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    private List<ProductOrderBrodcastListBean> productOrderBrodcastList;

    public List<ProductOrderBrodcastListBean> getProductOrderBrodcastList() {
      return productOrderBrodcastList;
    }

    public void setProductOrderBrodcastList(List<ProductOrderBrodcastListBean> productOrderBrodcastList) {
      this.productOrderBrodcastList = productOrderBrodcastList;
    }

    public static class ProductOrderBrodcastListBean {
      /**
       * broadcastText : 用户176xxxxx655申请了天天赚合约2666元
       * timeText : 60秒前
       */

      private String broadcastText;
      private String timeText;

      public String getBroadcastText() {
        return broadcastText;
      }

      public void setBroadcastText(String broadcastText) {
        this.broadcastText = broadcastText;
      }

      public String getTimeText() {
        return timeText;
      }

      public void setTimeText(String timeText) {
        this.timeText = timeText;
      }
    }
  }
}
