package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/13.
 */

public class MCoupons extends BaseModel{

  /**
   * code : 200
   * msg : success
   * data : {"userCouponList":[{"id":5,"name":"管理费抵用券","scoreDiscount":1000,"cashDiscount":10000,"startTime":"2017-09-07 17:42:00","endTime":"2017-09-07 18:42:00","type":1,"validateDays":30}]}
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
    private List<UserCouponListBean> userCouponList;

    public List<UserCouponListBean> getUserCouponList() {
      return userCouponList;
    }

    public void setUserCouponList(List<UserCouponListBean> userCouponList) {
      this.userCouponList = userCouponList;
    }

    public static class UserCouponListBean {
      /**
       * id : 5
       * name : 管理费抵用券
       * scoreDiscount : 1000
       * cashDiscount : 10000
       * startTime : 2017-09-07 17:42:00
       * endTime : 2017-09-07 18:42:00
       * type : 1
       * validateDays : 30
       */

      private int id;
      private String name;
      private long scoreDiscount;
      private long cashDiscount;
      private String startTime;
      private String endTime;
      private int type;
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

      public long getScoreDiscount() {
        return scoreDiscount;
      }

      public void setScoreDiscount(long scoreDiscount) {
        this.scoreDiscount = scoreDiscount;
      }

      public long getCashDiscount() {
        return cashDiscount;
      }

      public void setCashDiscount(long cashDiscount) {
        this.cashDiscount = cashDiscount;
      }

      public String getStartTime() {
        return startTime;
      }

      public void setStartTime(String startTime) {
        this.startTime = startTime;
      }

      public String getEndTime() {
        return endTime;
      }

      public void setEndTime(String endTime) {
        this.endTime = endTime;
      }

      public int getType() {
        return type;
      }

      public void setType(int type) {
        this.type = type;
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
