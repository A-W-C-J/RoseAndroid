package com.rose.android.model;

import java.util.List;

/**
 * Created by wenen on 2017/11/6.
 */

public class MRegistListModel extends BaseModel {

  /**
   * data : {"partnerList":[{"phone":"18681490507","registTime":"2017-11-01 14:01:18","comment":"注册成功"}]}
   * page : {"pageNo":1,"pageSize":10}
   * total : 1
   */

  private DataBean data;
  private PageBean page;
  private int total;

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
    private List<PartnerListBean> partnerList;

    public List<PartnerListBean> getPartnerList() {
      return partnerList;
    }

    public void setPartnerList(List<PartnerListBean> partnerList) {
      this.partnerList = partnerList;
    }

    public static class PartnerListBean {
      /**
       * phone : 18681490507
       * registTime : 2017-11-01 14:01:18
       * comment : 注册成功
       */

      private String phone;
      private String registTime;
      private String comment;

      public String getPhone() {
        return phone;
      }

      public void setPhone(String phone) {
        this.phone = phone;
      }

      public String getRegistTime() {
        return registTime;
      }

      public void setRegistTime(String registTime) {
        this.registTime = registTime;
      }

      public String getComment() {
        return comment;
      }

      public void setComment(String comment) {
        this.comment = comment;
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
