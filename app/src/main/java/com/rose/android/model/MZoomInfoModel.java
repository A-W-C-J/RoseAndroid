package com.rose.android.model;

/**
 * Created by wenen on 2017/11/15.
 */

public class MZoomInfoModel extends BaseModel {

  /**
   * data : {"margin":520000,"loan":5500000,"warningLine":4625000,"stopLine":4575000,"fee":7970,"feeText":"6.46元/交易日","neededAddFee":500,"neededZoomMargin":300000,"neededSupplyMargin":0}
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
     * margin : 520000
     * loan : 5500000
     * warningLine : 4625000
     * stopLine : 4575000
     * fee : 7970
     * feeText : 6.46元/交易日
     * neededAddFee : 500
     * neededZoomMargin : 300000
     * neededSupplyMargin : 0
     */

    private long margin;
    private long loan;
    private long warningLine;
    private long stopLine;
    private long fee;
    private String feeText;
    private long neededAddFee;
    private long neededZoomMargin;
    private long neededSupplyMargin;

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

    public long getWarningLine() {
      return warningLine;
    }

    public void setWarningLine(long warningLine) {
      this.warningLine = warningLine;
    }

    public long getStopLine() {
      return stopLine;
    }

    public void setStopLine(long stopLine) {
      this.stopLine = stopLine;
    }

    public long getFee() {
      return fee;
    }

    public void setFee(long fee) {
      this.fee = fee;
    }

    public String getFeeText() {
      return feeText;
    }

    public void setFeeText(String feeText) {
      this.feeText = feeText;
    }

    public long getNeededAddFee() {
      return neededAddFee;
    }

    public void setNeededAddFee(long neededAddFee) {
      this.neededAddFee = neededAddFee;
    }

    public long getNeededZoomMargin() {
      return neededZoomMargin;
    }

    public void setNeededZoomMargin(long neededZoomMargin) {
      this.neededZoomMargin = neededZoomMargin;
    }

    public long getNeededSupplyMargin() {
      return neededSupplyMargin;
    }

    public void setNeededSupplyMargin(long neededSupplyMargin) {
      this.neededSupplyMargin = neededSupplyMargin;
    }
  }
}
