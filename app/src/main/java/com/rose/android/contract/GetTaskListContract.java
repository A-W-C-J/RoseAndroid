package com.rose.android.contract;

/**
 * Created by wenen on 2017/10/26.
 */

public interface GetTaskListContract {
  interface View extends BaseContract.View{
    void getTaskList();
  }
  interface Presenter extends BaseContract.Presenter{
    void getTaskList();
  }
}
