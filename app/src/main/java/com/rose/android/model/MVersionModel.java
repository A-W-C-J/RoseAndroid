package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/10/14.
 */

public class MVersionModel extends BaseModel {

  /**
   * data : {"title":"提示","content":"发现新版本，更新体验新功能","status":1,"url":"http://download"}
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
     * title : 提示
     * content : 发现新版本，更新体验新功能
     * status : 1
     * url : http://download
     */

    private String title;
    private String content;
    private int status;
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

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }
}
