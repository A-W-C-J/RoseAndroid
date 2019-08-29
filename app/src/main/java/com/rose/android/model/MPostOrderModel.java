package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public class MPostOrderModel extends BaseModel {

    /**
     * data : {"id":40,"orderNo":"1505279251034","name":"天天赚","margin":400000,"loan":2000000,"amount":2400000,"balance":2400000,"warningLine":2200000,"stopLine":2120000,"totalFeeText":"3.6元/交易日","availabelCash":2400000,"tradedDays":1,"settleBenefitPercent":0,"status":1,"createTime":"2017-09-13 13:07:31","updateTime":"2017-09-13 13:07:31","startTradingDate":"2017-09-13","endTradingDate":"2017-09-13","assetAmount":2400000,"settleBenefit":2400000,"settleProfit":0,"settleFee":0,"type":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 40
         * orderNo : 1505279251034
         * name : 天天赚
         * margin : 400000
         * loan : 2000000
         * amount : 2400000
         * balance : 2400000
         * warningLine : 2200000
         * stopLine : 2120000
         * totalFeeText : 3.6元/交易日
         * availabelCash : 2400000
         * tradedDays : 1
         * settleBenefitPercent : 0
         * status : 1
         * createTime : 2017-09-13 13:07:31
         * updateTime : 2017-09-13 13:07:31
         * startTradingDate : 2017-09-13
         * endTradingDate : 2017-09-13
         * assetAmount : 2400000
         * settleBenefit : 2400000
         * settleProfit : 0
         * settleFee : 0
         * type : 1
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
        private int settleBenefitPercent;
        private int status;
        private String createTime;
        private String updateTime;
        private String startTradingDate;
        private String endTradingDate;
        private long assetAmount;
        private long settleBenefit;
        private int settleProfit;
        private int settleFee;
        private int type;

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

        public int getSettleBenefitPercent() {
            return settleBenefitPercent;
        }

        public void setSettleBenefitPercent(int settleBenefitPercent) {
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
