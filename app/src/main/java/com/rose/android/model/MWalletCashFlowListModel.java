package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/10/11.
 */

public class MWalletCashFlowListModel extends BaseModel {

  /**
   * code : 200
   * msg : 成功
   * data : {"userWalletCashFlowList":[{"type":3,"flow":-750000,"name":"保证金收支","balance":37022764,"createTime":"2017-10-10 16:31:25"},{"type":1,"flow":-19440,"name":"利息支出","balance":37003324,"createTime":"2017-10-10 16:31:25"},{"type":3,"flow":-600000,"name":"保证金收支","balance":37783564,"createTime":"2017-10-10 15:20:55"},{"type":1,"flow":-10800,"name":"利息支出","balance":37772764,"createTime":"2017-10-10 15:20:55"},{"type":3,"flow":0,"name":"保证金收支","balance":38383564,"createTime":"2017-10-09 16:38:42"},{"type":3,"flow":-285714,"name":"保证金收支","balance":38392204,"createTime":"2017-10-09 16:37:07"},{"type":1,"flow":-8640,"name":"利息支出","balance":38383564,"createTime":"2017-10-09 16:37:07"}]}
   * page : {"pageNo":0,"pageSize":10}
   * total : 7
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
    private List<UserWalletFlowListBean> userWalletFlowList;

    public List<UserWalletFlowListBean> getUserWalletCashFlowList() {
      return userWalletFlowList;
    }

    public void setUserWalletCashFlowList(List<UserWalletFlowListBean> userWalletCashFlowList) {
      this.userWalletFlowList = userWalletCashFlowList;
    }

    public static class UserWalletFlowListBean {
      /**
       * type : 3
       * flow : -750000
       * name : 保证金收支
       * balance : 37022764
       * createTime : 2017-10-10 16:31:25
       */

      private int type;
      private long flow;
      private String name;
      private long balance;
      private String createTime;

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
    }
  }
  public static class PageBean {
    /**
     * pageNo : 0
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
