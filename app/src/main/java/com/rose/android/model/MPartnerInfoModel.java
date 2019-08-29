package com.rose.android.model;

/**
 * Created by wenen on 2017/11/6.
 */

public class MPartnerInfoModel extends BaseModel {

  /**
   * data : {"level":1,"inviteUserCount":12,"brokerageAmount":250000}
   */

  private DataBean data;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    /**
     * level : 1
     * inviteUserCount : 12
     * brokerageAmount : 250000
     */

    private int level;
    private int inviteUserCount;
    private int brokerageAmount;
    private long availabeBrokerageAmount;

    public long getAvailabeBrokerageAmount() {
      return availabeBrokerageAmount;
    }

    public void setAvailabeBrokerageAmount(long availabeBrokerageAmount) {
      this.availabeBrokerageAmount = availabeBrokerageAmount;
    }

    public long getFrozenBrokerageAmount() {
      return frozenBrokerageAmount;
    }

    public void setFrozenBrokerageAmount(long frozenBrokerageAmount) {
      this.frozenBrokerageAmount = frozenBrokerageAmount;
    }

    private long frozenBrokerageAmount;

    public int getLevel() {
      return level;
    }

    public void setLevel(int level) {
      this.level = level;
    }

    public int getInviteUserCount() {
      return inviteUserCount;
    }

    public void setInviteUserCount(int inviteUserCount) {
      this.inviteUserCount = inviteUserCount;
    }

    public int getBrokerageAmount() {
      return brokerageAmount;
    }

    public void setBrokerageAmount(int brokerageAmount) {
      this.brokerageAmount = brokerageAmount;
    }
  }
}
