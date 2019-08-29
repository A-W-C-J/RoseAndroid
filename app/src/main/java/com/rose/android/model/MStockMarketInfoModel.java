package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/22.
 */

public class MStockMarketInfoModel extends BaseModel {

    /**
     * code : 200
     * msg : success
     * data : {"currentPrice":7700,"limitUpPrice":8470,"limitDownPrice":6930,"name":"悦达投资","symbol":"000001","status":1,"buyLevelPrice":[{"level":"买一","price":7690,"handAmount":268},{"level":"买二","price":7680,"handAmount":268},{"level":"买三","price":7670,"handAmount":268},{"level":"买四","price":7660,"handAmount":268},{"level":"买五","price":7650,"handAmount":268}],"sellLevelPrice":[{"level":"卖一","price":7690,"handAmount":268},{"level":"卖二","price":7680,"handAmount":268},{"level":"卖三","price":7670,"handAmount":268},{"level":"卖四","price":7660,"handAmount":268},{"level":"卖五","price":7650,"handAmount":268}]}
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * currentPrice : 7700
         * limitUpPrice : 8470
         * limitDownPrice : 6930
         * name : 悦达投资
         * symbol : 000001
         * status : 1
         * buyLevelPrice : [{"level":"买一","price":7690,"handAmount":268},{"level":"买二","price":7680,"handAmount":268},{"level":"买三","price":7670,"handAmount":268},{"level":"买四","price":7660,"handAmount":268},{"level":"买五","price":7650,"handAmount":268}]
         * sellLevelPrice : [{"level":"卖一","price":7690,"handAmount":268},{"level":"卖二","price":7680,"handAmount":268},{"level":"卖三","price":7670,"handAmount":268},{"level":"卖四","price":7660,"handAmount":268},{"level":"卖五","price":7650,"handAmount":268}]
         */

        private long currentPrice;
        private long limitUpPrice;
        private long limitDownPrice;
        private String name;
        private String symbol;
        private long openPrice;
        private long closePrice;
        private long highestPrice;
        private long lowestPrice;
        private long volumn;
        private int changePercent;
        private long changePrice;

        public String getStockStatusText() {
            return stockStatusText;
        }

        public void setStockStatusText(String stockStatusText) {
            this.stockStatusText = stockStatusText;
        }

        private String stockStatusText;

        public int getStockStatus() {
            return stockStatus;
        }

        public void setStockStatus(int stockStatus) {
            this.stockStatus = stockStatus;
        }

        private int stockStatus;

        public long getOpenPrice() {
            return openPrice;
        }

        public void setOpenPrice(long openPrice) {
            this.openPrice = openPrice;
        }

        public long getClosePrice() {
            return closePrice;
        }

        public void setClosePrice(long closePrice) {
            this.closePrice = closePrice;
        }

        public long getHighestPrice() {
            return highestPrice;
        }

        public void setHighestPrice(long highestPrice) {
            this.highestPrice = highestPrice;
        }

        public long getLowestPrice() {
            return lowestPrice;
        }

        public void setLowestPrice(long lowestPrice) {
            this.lowestPrice = lowestPrice;
        }

        public long getVolumn() {
            return volumn;
        }

        public void setVolumn(long volumn) {
            this.volumn = volumn;
        }

        public int getChangePercent() {
            return changePercent;
        }

        public void setChangePercent(int changePercent) {
            this.changePercent = changePercent;
        }

        public long getChangePrice() {
            return changePrice;
        }

        public void setChangePrice(long changePrice) {
            this.changePrice = changePrice;
        }

        private int status;
        private List<BuyLevelPriceBean> buyLevelPrice;
        private List<SellLevelPriceBean> sellLevelPrice;
        private List<StockTransactionList> stockTransactionList;

        public List<StockTransactionList> getStockTransactionList() {
            return stockTransactionList;
        }

        public void setStockTransactionList(List<StockTransactionList> stockTransactionList) {
            this.stockTransactionList = stockTransactionList;
        }

        public int getAvailableBuyVolumn() {
            return availableBuyVolumn;
        }

        public void setAvailableBuyVolumn(int availableBuyVolumn) {
            this.availableBuyVolumn = availableBuyVolumn;
        }

        public int getAvailableSellVolumn() {
            return availableSellVolumn;
        }

        public void setAvailableSellVolumn(int availableSellVolumn) {
            this.availableSellVolumn = availableSellVolumn;
        }

        private int availableBuyVolumn;
        private int availableSellVolumn;

        public long getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(long currentPrice) {
            this.currentPrice = currentPrice;
        }

        public long getLimitUpPrice() {
            return limitUpPrice;
        }

        public void setLimitUpPrice(long limitUpPrice) {
            this.limitUpPrice = limitUpPrice;
        }

        public long getLimitDownPrice() {
            return limitDownPrice;
        }

        public void setLimitDownPrice(long limitDownPrice) {
            this.limitDownPrice = limitDownPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<BuyLevelPriceBean> getBuyLevelPrice() {
            return buyLevelPrice;
        }

        public void setBuyLevelPrice(List<BuyLevelPriceBean> buyLevelPrice) {
            this.buyLevelPrice = buyLevelPrice;
        }

        public List<SellLevelPriceBean> getSellLevelPrice() {
            return sellLevelPrice;
        }

        public void setSellLevelPrice(List<SellLevelPriceBean> sellLevelPrice) {
            this.sellLevelPrice = sellLevelPrice;
        }

        public static class StockTransactionList {

            /**
             * time : 15:00:04
             * price : 3150
             * volume : 76
             * action : S
             * amount : 23996000
             */

            private String time;
            private long price;
            private long volume;
            private String action;
            private long amount;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public long getPrice() {
                return price;
            }

            public void setPrice(long price) {
                this.price = price;
            }

            public long getVolume() {
                return volume;
            }

            public void setVolume(long volume) {
                this.volume = volume;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public long getAmount() {
                return amount;
            }

            public void setAmount(long amount) {
                this.amount = amount;
            }
        }

        public static class BuyLevelPriceBean {
            /**
             * level : 买一
             * price : 7690
             * handAmount : 268
             */

            private String level;
            private long price;
            private long handAmount;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public long getPrice() {
                return price;
            }

            public void setPrice(long price) {
                this.price = price;
            }

            public long getHandAmount() {
                return handAmount;
            }

            public void setHandAmount(long handAmount) {
                this.handAmount = handAmount;
            }
        }

        public static class SellLevelPriceBean {
            /**
             * level : 卖一
             * price : 7690
             * handAmount : 268
             */

            private String level;
            private long price;
            private long handAmount;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public long getPrice() {
                return price;
            }

            public void setPrice(long price) {
                this.price = price;
            }

            public long getHandAmount() {
                return handAmount;
            }

            public void setHandAmount(long handAmount) {
                this.handAmount = handAmount;
            }
        }
    }
}
