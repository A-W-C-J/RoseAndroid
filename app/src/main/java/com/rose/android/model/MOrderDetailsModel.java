package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/14.
 */

public class MOrderDetailsModel extends BaseModel {

    /**
     * code : 200
     * msg : success
     * data : {"id":42,"orderNo":"1505279666772","name":"天天赚","margin":500000,"loan":2000000,"amount":2500000,"balance":2500000,"warningLine":2250000,"stopLine":2150000,"totalFeeText":"3.24元/交易日","availabelCash":2500000,"tradedDays":1,"settleBenefitPercent":400,"status":1,"createTime":"2017-09-13 13:14:26","updateTime":"2017-09-13 13:14:26","startTradingDate":"2017-09-13","endTradingDate":"2017-09-13","assetAmount":2600000,"settleBenefit":100000,"settleProfit":0,"settleFee":0}
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
         * id : 42
         * orderNo : 1505279666772
         * name : 天天赚
         * margin : 500000
         * loan : 2000000
         * amount : 2500000
         * balance : 2500000
         * warningLine : 2250000
         * stopLine : 2150000
         * totalFeeText : 3.24元/交易日
         * availabelCash : 2500000
         * tradedDays : 1
         * settleBenefitPercent : 400
         * status : 1
         * createTime : 2017-09-13 13:14:26
         * updateTime : 2017-09-13 13:14:26
         * startTradingDate : 2017-09-13
         * endTradingDate : 2017-09-13
         * assetAmount : 2600000
         * settleBenefit : 100000
         * settleProfit : 0
         * settleFee : 0
         */

        private int id;
        private String orderNo;
        private String name;
        private long margin;
        private long loan;
        private long amount;
        private long balance;
        private long warningLine;
        private long stopLine;
        private String totalFeeText;
        private long availabelCash;
        private int tradedDays;
        private long settleBenefitPercent;
        private int status;
        private String createTime;
        private String updateTime;
        private String startTradingDate;
        private String endTradingDate;
        private long assetAmount;
        private long settleBenefit;
        private int settleProfit;
        private int settleFee;
        private long loanUpLimit;

        public int getRiskType() {
            return riskType;
        }

        public void setRiskType(int riskType) {
            this.riskType = riskType;
        }

        private int riskType;

        public long getLoanUpLimit() {
            return loanUpLimit;
        }

        public void setLoanUpLimit(long loanUpLimit) {
            this.loanUpLimit = loanUpLimit;
        }

        public long getSettleMargin() {
            return settleMargin;
        }

        public void setSettleMargin(long settleMargin) {
            this.settleMargin = settleMargin;
        }

        private long settleMargin;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getMargin() {
            return margin;
        }

        public void setMargin(long margin) {
            this.margin = margin;
        }

        public long getLoan() {
            return loan;
        }

        public void setLoan(long loan) {
            this.loan = loan;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }

        public long getWarningLine() {
            return warningLine;
        }

        public void setWarningLine(long warningLine) {
            this.warningLine = warningLine;
        }

        public long getStopLine() {
            return stopLine;
        }

        public void setStopLine(long stopLine) {
            this.stopLine = stopLine;
        }

        public String getTotalFeeText() {
            return totalFeeText;
        }

        public void setTotalFeeText(String totalFeeText) {
            this.totalFeeText = totalFeeText;
        }

        public long getAvailabelCash() {
            return availabelCash;
        }

        public void setAvailabelCash(long availabelCash) {
            this.availabelCash = availabelCash;
        }

        public int getTradedDays() {
            return tradedDays;
        }

        public void setTradedDays(int tradedDays) {
            this.tradedDays = tradedDays;
        }

        public long getSettleBenefitPercent() {
            return settleBenefitPercent;
        }

        public void setSettleBenefitPercent(long settleBenefitPercent) {
            this.settleBenefitPercent = settleBenefitPercent;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getStartTradingDate() {
            return startTradingDate;
        }

        public void setStartTradingDate(String startTradingDate) {
            this.startTradingDate = startTradingDate;
        }

        public String getEndTradingDate() {
            return endTradingDate;
        }

        public void setEndTradingDate(String endTradingDate) {
            this.endTradingDate = endTradingDate;
        }

        public long getAssetAmount() {
            return assetAmount;
        }

        public void setAssetAmount(long assetAmount) {
            this.assetAmount = assetAmount;
        }

        public long getSettleBenefit() {
            return settleBenefit;
        }

        public void setSettleBenefit(long settleBenefit) {
            this.settleBenefit = settleBenefit;
        }

        public int getSettleProfit() {
            return settleProfit;
        }

        public void setSettleProfit(int settleProfit) {
            this.settleProfit = settleProfit;
        }

        public int getSettleFee() {
            return settleFee;
        }

        public void setSettleFee(int settleFee) {
            this.settleFee = settleFee;
        }

        private ProductPositionLimitBean productPositionLimit;

        public ProductPositionLimitBean getProductPositionLimit() {
            return productPositionLimit;
        }

        public void setProductPositionLimit(ProductPositionLimitBean productPositionLimit) {
            this.productPositionLimit = productPositionLimit;
        }

        public static class ProductPositionLimitBean {
            private long minCapital;
            private long maxCapital;
            private int mainBoard;
            private int smallMediumBoard;
            private int growthEnterpriseBoard;
            private String description;

            public long getMinCapital() {
                return minCapital;
            }

            public void setMinCapital(long minCapital) {
                this.minCapital = minCapital;
            }

            public long getMaxCapital() {
                return maxCapital;
            }

            public void setMaxCapital(long maxCapital) {
                this.maxCapital = maxCapital;
            }

            public int getMainBoard() {
                return mainBoard;
            }

            public void setMainBoard(int mainBoard) {
                this.mainBoard = mainBoard;
            }

            public int getSmallMediumBoard() {
                return smallMediumBoard;
            }

            public void setSmallMediumBoard(int smallMediumBoard) {
                this.smallMediumBoard = smallMediumBoard;
            }

            public int getGrowthEnterpriseBoard() {
                return growthEnterpriseBoard;
            }

            public void setGrowthEnterpriseBoard(int growthEnterpriseBoard) {
                this.growthEnterpriseBoard = growthEnterpriseBoard;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
