package com.rose.android.model;

/**
 * Created by wenen on 2017/12/9.
 */

public class MGuaguaModel extends BaseModel {

  /**
   * data : {"codeUrl":"weixin://wxpay/bizpayurl?pr=X7x35cf","transType":"SCANCODE","imageCodeUrl":"http://pay.e-mac.com.cn/api/getQRCodeImage?weixin://wxpay/bizpayurl?pr=X7x35cf"}
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
     * codeUrl : weixin://wxpay/bizpayurl?pr=X7x35cf
     * transType : SCANCODE
     * imageCodeUrl : http://pay.e-mac.com.cn/api/getQRCodeImage?weixin://wxpay/bizpayurl?pr=X7x35cf
     */

    private String codeUrl;
    private String transType;
    private String imageCodeUrl;

    public String getCodeUrl() {
      return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
      this.codeUrl = codeUrl;
    }

    public String getTransType() {
      return transType;
    }

    public void setTransType(String transType) {
      this.transType = transType;
    }

    public String getImageCodeUrl() {
      return imageCodeUrl;
    }

    public void setImageCodeUrl(String imageCodeUrl) {
      this.imageCodeUrl = imageCodeUrl;
    }
  }
}
