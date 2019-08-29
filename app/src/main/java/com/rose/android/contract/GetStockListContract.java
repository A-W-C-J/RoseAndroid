package com.rose.android.contract;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by wenen on 2018/1/3.
 */

public interface GetStockListContract {
    interface View extends BaseContract.View {
        void getStockList(String key, @NonNull Integer pageNo, @Nullable Integer pageSize);
    }

    interface Presenter extends BaseContract.Presenter {
        void getStockList(String key, @NonNull Integer pageNo, @Nullable Integer pageSize);
    }
}
