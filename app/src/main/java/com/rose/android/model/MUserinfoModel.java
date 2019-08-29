package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/20.
 */

public class MUserinfoModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"phone":"18681490507","headUrl":"http://res.yibeidiao.com/user/userInfo_head.png","cashAsset":46264409,"scoreAsset":99000,"coinAsset":0,"realName":null,"bankCardNo":"46002814445","hasUserAuthFreeze":false,"hasSetLoginPwd":false,"hasSetWithdrawPwd":true}
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
     * phone : 18681490507
     * headUrl : http://res.yibeidiao.com/user/userInfo_head.png
     * cashAsset : 46264409
     * scoreAsset : 99000
     * coinAsset : 0
     * realName : null
     * bankCardNo : 46002814445
     * hasUserAuthFreeze : false
     * hasSetLoginPwd : false
     * hasSetWithdrawPwd : true
     */

    private String phone;
    private String headUrl;
    private long cashAsset;
    private long scoreAsset;
    private long coinAsset;
    private String realName;
    private String  bankCardNo;
    private boolean hasUserAuthFreeze;
    private boolean hasSetLoginPwd;
    private boolean hasSetWithdrawPwd;

    public String getBankCardName() {
      return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
      this.bankCardName = bankCardName;
    }

    private String bankCardName;
    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getHeadUrl() {
      return headUrl;
    }

    public void setHeadUrl(String headUrl) {
      this.headUrl = headUrl;
    }

    public long getCashAsset() {
      return cashAsset;
    }

    public void setCashAsset(long cashAsset) {
      this.cashAsset = cashAsset;
    }

    public long getScoreAsset() {
      return scoreAsset;
    }

    public void setScoreAsset(long scoreAsset) {
      this.scoreAsset = scoreAsset;
    }

    public long getCoinAsset() {
      return coinAsset;
    }

    public void setCoinAsset(long coinAsset) {
      this.coinAsset = coinAsset;
    }

    public Object getRealName() {
      return realName;
    }

    public void setRealName(String realName) {
      this.realName = realName;
    }

    public String getBankCardNo() {
      return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
      this.bankCardNo = bankCardNo;
    }

    public boolean isHasUserAuthFreeze() {
      return hasUserAuthFreeze;
    }

    public void setHasUserAuthFreeze(boolean hasUserAuthFreeze) {
      this.hasUserAuthFreeze = hasUserAuthFreeze;
    }

    public boolean isHasSetLoginPwd() {
      return hasSetLoginPwd;
    }

    public void setHasSetLoginPwd(boolean hasSetLoginPwd) {
      this.hasSetLoginPwd = hasSetLoginPwd;
    }

    public boolean isHasSetWithdrawPwd() {
      return hasSetWithdrawPwd;
    }

    public void setHasSetWithdrawPwd(boolean hasSetWithdrawPwd) {
      this.hasSetWithdrawPwd = hasSetWithdrawPwd;
    }
  }
}
