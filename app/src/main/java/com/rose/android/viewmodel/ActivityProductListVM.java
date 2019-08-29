package com.rose.android.viewmodel;

import com.rose.android.model.MProductListModel;

/**
 * Created by xiaohuabu on 17/9/15.
 */

public class ActivityProductListVM {
  private static ActivityProductListVM productListVM;
  private MProductListModel productListModel;

  private ActivityProductListVM() {
  }

  public static ActivityProductListVM getInstance() {
    if (productListVM == null)
      productListVM = new ActivityProductListVM();
    return productListVM;
  }

  public void setProductListModel(MProductListModel productListModel) {
    this.productListModel = productListModel;
  }

  public MProductListModel getProductListModel() {
    return productListModel;
  }
}
