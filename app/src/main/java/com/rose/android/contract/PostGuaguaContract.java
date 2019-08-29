package com.rose.android.contract;

/**
 * Created by wenen on 2017/12/9.
 */

public interface PostGuaguaContract {
  interface View extends BaseContract.View {
    void postGuagua(Integer type, Long amount, Integer source);
  }

  interface Presenter extends BaseContract.Presenter {
    void postGuagua(Integer type, Long amount, Integer source);
  }
}
