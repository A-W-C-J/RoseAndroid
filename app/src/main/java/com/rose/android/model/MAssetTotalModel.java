package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/10/14.
 */

public class MAssetTotalModel extends BaseModel {

  /**
   * data : {"totalStock":0,"totalCash":1000000}
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
     * totalStock : 0
     * totalCash : 1000000
     */

    private long totalStock;
    private long totalCash;

    public long getTotalStock() {
      return totalStock;
    }

    public void setTotalStock(long totalStock) {
      this.totalStock = totalStock;
    }

    public long getTotalCash() {
      return totalCash;
    }

    public void setTotalCash(long totalCash) {
      this.totalCash = totalCash;
    }
  }
}
