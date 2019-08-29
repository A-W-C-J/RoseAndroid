package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/5.
 */
public class MRegiestedModel extends BaseModel {


  /**
   * code : 200
   * msg : success
   * data : {"token":"b47fee5f621c42c0b77a3d7476da16ac","headUrl":"http://res.yibeidiao.com/user/userInfo_head.png","phone":"18683496757","cashAsset":0}
   */

  private MLoginModel.DataBean data;

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

  public MLoginModel.DataBean getData() {
    return data;
  }

  public void setData(MLoginModel.DataBean data) {
    this.data = data;
  }

}
