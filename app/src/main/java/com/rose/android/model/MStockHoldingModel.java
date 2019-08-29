package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/21.
 */

public class MStockHoldingModel extends BaseModel{

  /**
   * code : 200
   * msg : 成功
   * data : {"productOrderList":[{"id":3,"productItemId":65,"name":"免息体验","orderAmount":6000000,"assetTotalAmount":6000000,"settleBenefit":0,"status":1,"startTradingDate":"2017-09-15","endTradingDate":"2017-10-19","createTime":"2017-09-15 15:42:19","updateTime":"2017-09-15 15:42:19","expire":0,"type":2},{"id":2,"productItemId":64,"name":"免费体验","orderAmount":2100000,"assetTotalAmount":2100000,"settleBenefit":0,"status":1,"startTradingDate":"2017-09-15","endTradingDate":"2017-09-18","createTime":"2017-09-15 15:31:25","updateTime":"2017-09-15 15:31:25","expire":0,"type":2},{"id":1,"productItemId":43,"name":"天天赚","orderAmount":4000000,"assetTotalAmount":4000000,"settleBenefit":0,"status":1,"startTradingDate":"2017-09-15","endTradingDate":"2017-09-18","createTime":"2017-09-15 15:11:58","updateTime":"2017-09-15 15:11:58","expire":0,"type":1}]}
   * page : {"pageNo":1,"pageSize":3}
   * total : 3
   */

  private DataBean data;
  private PageBean page;
  private int total;

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

  public PageBean getPage() {
    return page;
  }

  public void setPage(PageBean page) {
    this.page = page;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public static class DataBean {
    private List<ProductOrderListBean> productOrderList;

    public List<ProductOrderListBean> getProductOrderList() {
      return productOrderList;
    }

    public void setProductOrderList(List<ProductOrderListBean> productOrderList) {
      this.productOrderList = productOrderList;
    }

    public static class ProductOrderListBean {
      /**
       * id : 3
       * productItemId : 65
       * name : 免息体验
       * orderAmount : 6000000
       * assetTotalAmount : 6000000
       * settleBenefit : 0
       * status : 1
       * startTradingDate : 2017-09-15
       * endTradingDate : 2017-10-19
       * createTime : 2017-09-15 15:42:19
       * updateTime : 2017-09-15 15:42:19
       * expire : 0
       * type : 2
       */

      private int id;
      private int productItemId;
      private String name;
      private long orderAmount;
      private long assetTotalAmount;
      private long settleBenefit;
      private int status;
      private String startTradingDate;
      private String endTradingDate;
      private String createTime;
      private String updateTime;
      private int expire;
      private int type;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public int getProductItemId() {
        return productItemId;
      }

      public void setProductItemId(int productItemId) {
        this.productItemId = productItemId;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public long getOrderAmount() {
        return orderAmount;
      }

      public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
      }

      public long getAssetTotalAmount() {
        return assetTotalAmount;
      }

      public void setAssetTotalAmount(long assetTotalAmount) {
        this.assetTotalAmount = assetTotalAmount;
      }

      public long getSettleBenefit() {
        return settleBenefit;
      }

      public void setSettleBenefit(long settleBenefit) {
        this.settleBenefit = settleBenefit;
      }

      public int getStatus() {
        return status;
      }

      public void setStatus(int status) {
        this.status = status;
      }

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

      public String getCreateTime() {
        return createTime;
      }

      public void setCreateTime(String createTime) {
        this.createTime = createTime;
      }

      public String getUpdateTime() {
        return updateTime;
      }

      public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
      }

      public int getExpire() {
        return expire;
      }

      public void setExpire(int expire) {
        this.expire = expire;
      }

      public int getType() {
        return type;
      }

      public void setType(int type) {
        this.type = type;
      }
    }
  }

  public static class PageBean {
    /**
     * pageNo : 1
     * pageSize : 3
     */

    private int pageNo;
    private int pageSize;

    public int getPageNo() {
      return pageNo;
    }

    public void setPageNo(int pageNo) {
      this.pageNo = pageNo;
    }

    public int getPageSize() {
      return pageSize;
    }

    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }
  }
}
