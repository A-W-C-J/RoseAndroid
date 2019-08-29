package com.rose.android.contract;

/**
 * Created by wenen on 2018/1/4.
 */

public interface DeleteSelfSelectStockContract {
    interface View extends BaseContract.View {
        void deleteSelfSelect(String stockExchange, String stockName, String stockSymbol);
    }

    interface Presenter extends BaseContract.Presenter {
        void deleteSelfSelect(String stockExchange, String stockName, String stockSymbol);
    }
}
