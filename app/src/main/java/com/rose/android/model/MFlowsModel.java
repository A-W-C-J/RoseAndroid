package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/27.
 */

public class MFlowsModel extends BaseModel {

  /**
   * code : 200
   * msg : 成功
   * data : {"productOrderFlowList":[{"id":2,"userId":3,"productOrderId":10,"type":1,"flow":800000,"name":"新增合约","balance":800000,"createTime":"2017-09-21 14:25:10","updateTime":"2017-09-21 14:25:10"}]}
   * page : {"pageNo":1,"pageSize":10}
   * total : 1
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
    private List<ProductOrderFlowListBean> productOrderFlowList;

    public List<ProductOrderFlowListBean> getProductOrderFlowList() {
      return productOrderFlowList;
    }

    public void setProductOrderFlowList(List<ProductOrderFlowListBean> productOrderFlowList) {
      this.productOrderFlowList = productOrderFlowList;
    }

    public static class ProductOrderFlowListBean {
      /**
       * id : 2
       * userId : 3
       * productOrderId : 10
       * type : 1
       * flow : 800000
       * name : 新增合约
       * balance : 800000
       * createTime : 2017-09-21 14:25:10
       * updateTime : 2017-09-21 14:25:10
       */

      private int id;
      private int userId;
      private int productOrderId;
      private int type;
      private long flow;
      private String name;
      private long balance;
      private String createTime;
      private String updateTime;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public int getUserId() {
        return userId;
      }

      public void setUserId(int userId) {
        this.userId = userId;
      }

      public int getProductOrderId() {
        return productOrderId;
      }

      public void setProductOrderId(int productOrderId) {
        this.productOrderId = productOrderId;
      }

      public int getType() {
        return type;
      }

      public void setType(int type) {
        this.type = type;
      }

      public long getFlow() {
        return flow;
      }

      public void setFlow(long flow) {
        this.flow = flow;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public long getBalance() {
        return balance;
      }

      public void setBalance(long balance) {
        this.balance = balance;
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
    }
  }

  public static class PageBean {
    /**
     * pageNo : 1
     * pageSize : 10
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
