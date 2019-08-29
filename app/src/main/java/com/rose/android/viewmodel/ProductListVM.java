package com.rose.android.viewmodel;

import com.rose.android.model.MProductListModel;

/**
 * Created by xiaohuabu on 17/9/7.
 */

public class ProductListVM {
    private static ProductListVM productListVM;
    private MProductListModel productListModel;

    private ProductListVM() {
    }

    public static ProductListVM getInstance() {
        if (productListVM == null)
            productListVM = new ProductListVM();
        return productListVM;
    }

    public void setProductListModel(MProductListModel productListModel) {
        this.productListModel = productListModel;
    }

    public MProductListModel getProductListModel() {
        return productListModel;
    }
}
