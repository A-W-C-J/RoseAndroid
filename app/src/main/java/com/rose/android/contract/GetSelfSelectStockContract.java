package com.rose.android.contract;

/**
 * Created by wenen on 2018/1/4.
 */

public interface GetSelfSelectStockContract {
    interface View extends BaseContract.View {
        void getSelfSelectStock();
    }

    interface Presenter extends BaseContract.Presenter {
        void getSelfSelectStock();
    }
}
