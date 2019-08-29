package com.rose.android.model;

import java.util.List;

/**
 * Created by wenen on 2018/1/3.
 */

public class MStockListModel extends BaseModel {

    /**
     * data : {"stockList":[{"stockExchange":"SZ","stockName":"东方市场","stockSymbol":"600857","selfSelect":true}]}
     * page : {"pageNo":1,"pageSize":10}
     * total : 15
     */

    private DataBean data;
    private PageBean page;
    private int total;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
             * stockExchange : SZ
             * stockName : 东方市场
             * stockSymbol : 600857
             * selfSelect : true
             */

            private String stockExchange;
            private String stockName;
            private String stockSymbol;
            private boolean selfSelect;

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
        }
    }

    public static class PageBean {
        /**
         * pageNo : 1
         * pageSize : 10
         */

        private int pageNo;
        private int pageSize;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }
}
