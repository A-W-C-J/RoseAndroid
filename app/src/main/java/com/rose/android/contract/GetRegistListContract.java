package com.rose.android.contract;

/**
 * Created by wenen on 2017/11/6.
 */

public interface GetRegistListContract {
  interface View extends BaseContract.View {
    void getRegistList(int pageNo,int pageSize);
  }

  interface Presenter extends BaseContract.Presenter {
    void getRegistList(int pageNo,int pageSize);
  }
}
