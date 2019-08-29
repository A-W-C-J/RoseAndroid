package com.rose.android.model;

/**
 * Created by wenen on 2017/11/1.
 */

public class MHostModel extends BaseModel {
  private String protocal;
  private String domain;

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getProtocal() {
    return protocal;
  }

  public void setProtocal(String protocal) {
    this.protocal = protocal;
  }
}
