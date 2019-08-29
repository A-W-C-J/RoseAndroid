package com.rose.android.viewmodel;

import com.rose.android.model.MCoupons;

/**
 * Created by xiaohuabu on 17/9/27.
 */

public class CouponListVM {
  static CouponListVM couponListVM;
  private MCoupons coupons;

  private CouponListVM() {
  }

  public static CouponListVM getInstance() {
    if (couponListVM == null) {
      couponListVM = new CouponListVM();
    }
    return couponListVM;
  }

  public MCoupons getCoupons() {
    return coupons;
  }

  public void setCoupons(MCoupons coupons) {
    this.coupons = coupons;
  }
}
