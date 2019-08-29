package com.rose.android.viewmodel;

import com.rose.android.model.MVersionModel;

/**
 * Created by xiaohuabu on 17/10/14.
 */

public class VersionVm {
  private static VersionVm versionVm;

  public MVersionModel getVersionModel() {
    return versionModel;
  }

  public void setVersionModel(MVersionModel versionModel) {
    this.versionModel = versionModel;
  }

  private MVersionModel versionModel;

  private VersionVm() {
  }

  public static VersionVm getInstance() {
    if (versionVm == null) {
      versionVm = new VersionVm();
    }
    return versionVm;
  }

}
