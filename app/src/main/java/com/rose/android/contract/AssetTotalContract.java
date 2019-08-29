package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/14.
 */

public interface AssetTotalContract {
    interface View extends BaseContract.View {
        void getAssetTotal();
    }

    interface Presenter extends BaseContract.Presenter {
        void getAssetTotal();
    }
}
