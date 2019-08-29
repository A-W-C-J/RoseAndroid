package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/10/9.
 */

public class MWalletModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"cashAsset":0,"scoreAsset":0,"coinAsset":0}
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
     * cashAsset : 0
     * scoreAsset : 0
     * coinAsset : 0
     */

    private long cashAsset;
    private long scoreAsset;
    private long coinAsset;

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
  }
}
