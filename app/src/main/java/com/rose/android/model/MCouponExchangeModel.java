package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/20.
 */

public class MCouponExchangeModel  extends BaseModel{

  /**
   * code : 200
   * msg : success
   * data : {"userCouponList":[{"id":null,"name":"管理费抵用券","scoreDiscount":1000,"cashDiscount":10000,"startTime":"2017-09-07 17:42:00","endTime":"2017-09-07 18:42:00","type":"COUPON_TYPE_INTEREST","validateDays":null}],"score":99000}
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
    /**
     * userCouponList : [{"id":null,"name":"管理费抵用券","scoreDiscount":1000,"cashDiscount":10000,"startTime":"2017-09-07 17:42:00","endTime":"2017-09-07 18:42:00","type":"COUPON_TYPE_INTEREST","validateDays":null}]
     * score : 99000
     */

    private int score;
    private List<UserCouponListBean> userCouponList;

    public int getScore() {
      return score;
    }

    public void setScore(int score) {
      this.score = score;
    }

    public List<UserCouponListBean> getUserCouponList() {
      return userCouponList;
    }

    public void setUserCouponList(List<UserCouponListBean> userCouponList) {
      this.userCouponList = userCouponList;
    }

    public static class UserCouponListBean {
      /**
       * id : null
       * name : 管理费抵用券
       * scoreDiscount : 1000
       * cashDiscount : 10000
       * startTime : 2017-09-07 17:42:00
       * endTime : 2017-09-07 18:42:00
       * type : COUPON_TYPE_INTEREST
       * validateDays : null
       */

      private Object id;
      private String name;
      private int scoreDiscount;
      private int cashDiscount;
      private String startTime;
      private String endTime;
      private String type;
      private Object validateDays;

      public Object getId() {
        return id;
      }

      public void setId(Object id) {
        this.id = id;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public int getScoreDiscount() {
        return scoreDiscount;
      }

      public void setScoreDiscount(int scoreDiscount) {
        this.scoreDiscount = scoreDiscount;
      }

      public int getCashDiscount() {
        return cashDiscount;
      }

      public void setCashDiscount(int cashDiscount) {
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

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public Object getValidateDays() {
        return validateDays;
      }

      public void setValidateDays(Object validateDays) {
        this.validateDays = validateDays;
      }
    }
  }
}
