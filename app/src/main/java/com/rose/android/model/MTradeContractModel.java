package com.rose.android.model;

import java.io.Serializable;

/**
 * Created by xiaohuabu on 17/10/10.
 */

public class MTradeContractModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"firstParty":"XXX","secondParty":{"name":"温梦剑","phone":"17603091688","idCardNo":"360481199310261015","address":""},"thirdParty":"YQZ","totalfee":5400,"feeCycleText":"每天","margin":666666,"loan":2000000,"startDate":"2017-10-11","endDate":"2017-10-13"}
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

  public static class DataBean implements Serializable {
    /**
     * firstParty : XXX
     * secondParty : {"name":"温梦剑","phone":"17603091688","idCardNo":"360481199310261015","address":""}
     * thirdParty : YQZ
     * totalfee : 5400
     * feeCycleText : 每天
     * margin : 666666
     * loan : 2000000
     * startDate : 2017-10-11
     * endDate : 2017-10-13
     * startTradingDate
     */

    private String firstParty;
    private SecondPartyBean secondParty;
    private String thirdParty;
    private long totalfee;
    private String feeCycleText;
    private long margin;
    private long loan;
    private String startDate;
    private String endDate;

    public String getFirstParty() {
      return firstParty;
    }

    public void setFirstParty(String firstParty) {
      this.firstParty = firstParty;
    }

    public SecondPartyBean getSecondParty() {
      return secondParty;
    }

    public void setSecondParty(SecondPartyBean secondParty) {
      this.secondParty = secondParty;
    }

    public String getThirdParty() {
      return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
      this.thirdParty = thirdParty;
    }

    public long getTotalfee() {
      return totalfee;
    }

    public void setTotalfee(long totalfee) {
      this.totalfee = totalfee;
    }

    public String getFeeCycleText() {
      return feeCycleText;
    }

    public void setFeeCycleText(String feeCycleText) {
      this.feeCycleText = feeCycleText;
    }

    public long getMargin() {
      return margin;
    }

    public void setMargin(long margin) {
      this.margin = margin;
    }

    public long getLoan() {
      return loan;
    }

    public void setLoan(long loan) {
      this.loan = loan;
    }

    public String getStartDate() {
      return startDate;
    }

    public void setStartDate(String startDate) {
      this.startDate = startDate;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }

    public static class SecondPartyBean implements Serializable {
      /**
       * name : 温梦剑
       * phone : 17603091688
       * idCardNo : 360481199310261015
       * address :
       */

      private String name;
      private String phone;
      private String idCardNo;
      private String address;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getPhone() {
        return phone;
      }

      public void setPhone(String phone) {
        this.phone = phone;
      }

      public String getIdCardNo() {
        return idCardNo;
      }

      public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
      }

      public String getAddress() {
        return address;
      }

      public void setAddress(String address) {
        this.address = address;
      }
    }

    @Override
    public String toString() {
      return "DataBean{" +
          "firstParty='" + firstParty + '\'' +
          ", secondParty=" + secondParty +
          ", thirdParty='" + thirdParty + '\'' +
          ", totalfee=" + totalfee +
          ", feeCycleText='" + feeCycleText + '\'' +
          ", margin=" + margin +
          ", loan=" + loan +
          ", startDate='" + startDate + '\'' +
          ", endDate='" + endDate + '\'' +
          '}';
    }

  }

  @Override
  public String toString() {
    return "MTradeContractModel{" +
        "code=" + code +
        ", msg='" + msg + '\'' +
        ", data=" + data +
        '}';
  }
}
