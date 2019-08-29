package com.rose.android.contract;

/**
 * Created by wenen on 2018/1/4.
 */

public interface PostSelfSelectStockContract {
    interface View extends BaseContract.View {
        void postSelfSelect(String stockExchange, String stockName, String stockSymbol);
    }

    interface Presenter extends BaseContract.Presenter {
        void postSelfSelect(String stockExchange, String stockName, String stockSymbol);
    }
}
