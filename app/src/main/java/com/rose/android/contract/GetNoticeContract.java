package com.rose.android.contract;

/**
 * Created by wenen on 2017/12/6.
 */

public interface GetNoticeContract {
  interface View extends BaseContract.View {
    void getNotice(int pageNo,int pageSize);
  }

  interface Presenter extends BaseContract.Presenter {
    void getNotice(int pageNo,int pageSize);
  }
}
