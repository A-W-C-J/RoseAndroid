package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/10/9.
 */

public class MAllCouponsModel extends BaseModel {

  /**
   * data : {"couponInMallList":[{"id":1,"name":"管理费抵用券","description":"用于xxxx","needScore":1000,"cashDiscount":10000,"validateDays":30}]}
   */

  private DataBean data;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    private List<CouponInMallListBean> couponInMallList;

    public List<CouponInMallListBean> getCouponInMallList() {
      return couponInMallList;
    }

    public void setCouponInMallList(List<CouponInMallListBean> couponInMallList) {
      this.couponInMallList = couponInMallList;
    }

    public static class CouponInMallListBean {
      /**
       * id : 1
       * name : 管理费抵用券
       * description : 用于xxxx
       * needScore : 1000
       * cashDiscount : 10000
       * validateDays : 30
       */

      private int id;
      private String name;
      private String description;
      private long needScore;
      private long cashDiscount;
      private int validateDays;

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

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }

      public long getNeedScore() {
        return needScore;
      }

      public void setNeedScore(long needScore) {
        this.needScore = needScore;
      }

      public long getCashDiscount() {
        return cashDiscount;
      }

      public void setCashDiscount(long cashDiscount) {
        this.cashDiscount = cashDiscount;
      }

      public int getValidateDays() {
        return validateDays;
      }

      public void setValidateDays(int validateDays) {
        this.validateDays = validateDays;
      }
    }
  }
}
