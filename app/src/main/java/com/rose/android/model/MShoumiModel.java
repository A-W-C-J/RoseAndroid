package com.rose.android.model;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by wenen on 2017/12/12.
 */

public class MShoumiModel extends BaseModel {

  /**
   * data : {"flag":"1","data":[{"type":"__quickpay","req_url":"http://api.chafengche.cn/","code_url":"code_url","p_OrderId":"NMTIIEcMEOMmEc","p_Commend":"1","p_ChannelId":"2","p_UserId":"","p_Name":"%E5%BF%AB%E6%8D%B7"}],"rgba":"","p_Subject":"补邮费","p_Amount":"1.00","p_PayId":"NMTIIEcMEOMmEc","p_SDKVersion":"1.0.0"}
   */

  private JsonObject data;

  public JsonObject getData() {
    return data;
  }

  public void setData(JsonObject data) {
    this.data = data;
  }

  public static class DataBeanX {
    /**
     * flag : 1
     * data : [{"type":"__quickpay","req_url":"http://api.chafengche.cn/","code_url":"code_url","p_OrderId":"NMTIIEcMEOMmEc","p_Commend":"1","p_ChannelId":"2","p_UserId":"","p_Name":"%E5%BF%AB%E6%8D%B7"}]
     * rgba :
     * p_Subject : 补邮费
     * p_Amount : 1.00
     * p_PayId : NMTIIEcMEOMmEc
     * p_SDKVersion : 1.0.0
     */

    private String flag;
    private String rgba;
    private String p_Subject;
    private String p_Amount;
    private String p_PayId;
    private String p_SDKVersion;
    private List<DataBean> data;

    public String getFlag() {
      return flag;
    }

    public void setFlag(String flag) {
      this.flag = flag;
    }

    public String getRgba() {
      return rgba;
    }

    public void setRgba(String rgba) {
      this.rgba = rgba;
    }

    public String getP_Subject() {
      return p_Subject;
    }

    public void setP_Subject(String p_Subject) {
      this.p_Subject = p_Subject;
    }

    public String getP_Amount() {
      return p_Amount;
    }

    public void setP_Amount(String p_Amount) {
      this.p_Amount = p_Amount;
    }

    public String getP_PayId() {
      return p_PayId;
    }

    public void setP_PayId(String p_PayId) {
      this.p_PayId = p_PayId;
    }

    public String getP_SDKVersion() {
      return p_SDKVersion;
    }

    public void setP_SDKVersion(String p_SDKVersion) {
      this.p_SDKVersion = p_SDKVersion;
    }

    public List<DataBean> getData() {
      return data;
    }

    public void setData(List<DataBean> data) {
      this.data = data;
    }

    public static class DataBean {
      /**
       * type : __quickpay
       * req_url : http://api.chafengche.cn/
       * code_url : code_url
       * p_OrderId : NMTIIEcMEOMmEc
       * p_Commend : 1
       * p_ChannelId : 2
       * p_UserId :
       * p_Name : %E5%BF%AB%E6%8D%B7
       */

      private String type;
      private String req_url;
      private String code_url;
      private String p_OrderId;
      private String p_Commend;
      private String p_ChannelId;
      private String p_UserId;
      private String p_Name;

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public String getReq_url() {
        return req_url;
      }

      public void setReq_url(String req_url) {
        this.req_url = req_url;
      }

      public String getCode_url() {
        return code_url;
      }

      public void setCode_url(String code_url) {
        this.code_url = code_url;
      }

      public String getP_OrderId() {
        return p_OrderId;
      }

      public void setP_OrderId(String p_OrderId) {
        this.p_OrderId = p_OrderId;
      }

      public String getP_Commend() {
        return p_Commend;
      }

      public void setP_Commend(String p_Commend) {
        this.p_Commend = p_Commend;
      }

      public String getP_ChannelId() {
        return p_ChannelId;
      }

      public void setP_ChannelId(String p_ChannelId) {
        this.p_ChannelId = p_ChannelId;
      }

      public String getP_UserId() {
        return p_UserId;
      }

      public void setP_UserId(String p_UserId) {
        this.p_UserId = p_UserId;
      }

      public String getP_Name() {
        return p_Name;
      }

      public void setP_Name(String p_Name) {
        this.p_Name = p_Name;
      }
    }
  }
}
