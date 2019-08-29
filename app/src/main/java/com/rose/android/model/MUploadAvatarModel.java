package com.rose.android.model;

/**
 * Created by wenen on 2017/12/5.
 */

public class MUploadAvatarModel extends BaseModel {

  /**
   * data : {"avatarLink":"http://jinniubao.oss-cn-shenzhen.aliyuncs.com/userAvatar/12670_1512616654350"}
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
     * avatarLink : http://jinniubao.oss-cn-shenzhen.aliyuncs.com/userAvatar/12670_1512616654350
     */

    private String avatarLink;

    public String getAvatarLink() {
      return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
      this.avatarLink = avatarLink;
    }
  }
}
