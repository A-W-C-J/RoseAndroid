package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public class MBannersModel extends BaseModel {

  /**
   * code : 200
   * msg : success
   * data : {"bannerList":[{"bannerUrl":"http://rose-oss.oss-cn-shenzhen.aliyuncs.com/banners/banner-mxxt.png","pageUrl":"BasicKnowledge","protocal":"goto-native"},{"bannerUrl":"http://rose-oss.oss-cn-shenzhen.aliyuncs.com/banners/banner-tyj.png","pageUrl":"http://customer.testhost.com/app/help/info.html","protocal":"goto-web"}]}
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
    private List<BannerListBean> bannerList;

    public List<BannerListBean> getBannerList() {
      return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
      this.bannerList = bannerList;
    }

    public static class BannerListBean {
      /**
       * bannerUrl : http://rose-oss.oss-cn-shenzhen.aliyuncs.com/banners/banner-mxxt.png
       * pageUrl : BasicKnowledge
       * protocal : goto-native
       */

      private String bannerUrl;
      private String pageUrl;
      private String protocal;

      public String getBannerUrl() {
        return bannerUrl;
      }

      public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
      }

      public String getPageUrl() {
        return pageUrl;
      }

      public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
      }

      public String getProtocal() {
        return protocal;
      }

      public void setProtocal(String protocal) {
        this.protocal = protocal;
      }
    }
  }
}
