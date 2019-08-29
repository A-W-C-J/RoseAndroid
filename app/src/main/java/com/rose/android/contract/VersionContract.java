package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/14.
 */

public interface VersionContract {
  interface View extends BaseContract.View {
    void postVersion(int terminalType, String versionName, String packageName);
  }

  interface Presenter extends BaseContract.Presenter {
    void postVersion(int terminalType, String versionName, String packageName);
  }
}
