package com.rose.android.model;

import java.util.List;

/**
 * Created by wenen on 2018/1/4.
 */

public class MSelfSelectListModel extends BaseModel {

    /**
     * data : {"stockList":[{"stockExchange":"SH","stockName":"华北制药","stockSymbol":"600812","selfSelect":true,"currentPrice":5010,"changePercent":14}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<StockListBean> stockList;

        public List<StockListBean> getStockList() {
            return stockList;
        }

        public void setStockList(List<StockListBean> stockList) {
            this.stockList = stockList;
        }

        public static class StockListBean {
            /**
             * stockExchange : SH
             * stockName : 华北制药
             * stockSymbol : 600812
             * selfSelect : true
             * currentPrice : 5010
             * changePercent : 14
             */

            private String stockExchange;
            private String stockName;
            private String stockSymbol;
            private boolean selfSelect;
            private long currentPrice;
            private int changePercent;
            private int stockStatus;
            private String stockStatusText;
            public String getStockExchange() {
                return stockExchange;
            }

            public void setStockExchange(String stockExchange) {
                this.stockExchange = stockExchange;
            }

            public String getStockName() {
                return stockName;
            }

            public void setStockName(String stockName) {
                this.stockName = stockName;
            }

            public String getStockSymbol() {
                return stockSymbol;
            }

            public void setStockSymbol(String stockSymbol) {
                this.stockSymbol = stockSymbol;
            }

            public boolean isSelfSelect() {
                return selfSelect;
            }

            public void setSelfSelect(boolean selfSelect) {
                this.selfSelect = selfSelect;
            }

            public long getCurrentPrice() {
                return currentPrice;
            }

            public void setCurrentPrice(long currentPrice) {
                this.currentPrice = currentPrice;
            }

            public int getChangePercent() {
                return changePercent;
            }

            public void setChangePercent(int changePercent) {
                this.changePercent = changePercent;
            }

            public String getStockStatusText() {
                return stockStatusText;
            }

            public void setStockStatusText(String stockStatusText) {
                this.stockStatusText = stockStatusText;
            }
            public int getStockStatus() {
                return stockStatus;
            }

            public void setStockStatus(int stockStatus) {
                this.stockStatus = stockStatus;
            }
        }
    }
}
