package com.rose.android.model;

/**
 * Created by wenen on 2017/11/6.
 */

public class MShareInfoModel extends BaseModel {

  /**
   * data : {"title":"推广赚钱","content":"推广赚钱","url":"www.jinniaobao.com?promotionCode=asdaff"}
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
     * title : 推广赚钱
     * content : 推广赚钱
     * url : www.jinniaobao.com?promotionCode=asdaff
     */

    private String title;
    private String content;
    private String url;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }
}
