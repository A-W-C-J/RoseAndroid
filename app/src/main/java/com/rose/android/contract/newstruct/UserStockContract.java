package com.rose.android.contract.newstruct;


import com.rose.android.contract.BaseContract;

/**
 * 用户自选股 Contract
 */
public interface UserStockContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {
        /**
         * 获取用户自选股列表
         *
         * @param page 页码
         * @param size 每页大小（默认为10）
         */
        void requestUserStocks(Integer page, Integer size);

        /**
         * 新增自选股
         *
         * @param securityCode 证券唯一编号，如:"SH.600250"
         */
        void addUserStock(String securityCode);


        /**
         * 移除用户自选股
         *
         * @param securityCode
         */
        void removeUserStock(String securityCode);
    }
}
