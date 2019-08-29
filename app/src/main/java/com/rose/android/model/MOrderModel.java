package com.rose.android.model;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public class MOrderModel extends BaseModel {

    /**
     * code : 200
     * msg : 成功
     * data : {"productOrderList":[{"id":21,"name":"天天赚","orderAmount":2500000,"assetTotalAmount":2500000,"settleBenefit":0,"status":"操盘中","startTradingDate":"2017-09-12","endTradingDate":"2017-09-13","createTime":"2017-09-12 12:21:48","updateTime":"2017-09-12 12:21:48","expire":false},{"id":20,"name":"免费体验","orderAmount":2100000,"assetTotalAmount":2100000,"settleBenefit":0,"status":"操盘中","startTradingDate":"2017-09-12","endTradingDate":"2017-09-13","createTime":"2017-09-12 11:27:37","updateTime":"2017-09-12 11:27:37","expire":false},{"id":19,"name":"互惠盈","orderAmount":1125000,"assetTotalAmount":1125000,"settleBenefit":0,"status":"操盘中","startTradingDate":"2017-09-12","endTradingDate":"2017-09-13","createTime":"2017-09-11 20:59:02","updateTime":"2017-09-11 20:59:02","expire":false}]}
     * page : {"pageNo":1,"pageSize":3}
     * total : 19
     */
    private DataBean data;
    private PageBean page;
    private int total;

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
        private List<ProductOrderListBean> productOrderList;

        public List<ProductOrderListBean> getProductOrderList() {
            return productOrderList;
        }

        public void setProductOrderList(List<ProductOrderListBean> productOrderList) {
            this.productOrderList = productOrderList;
        }

        public static class ProductOrderListBean {
            /**
             * id : 21
             * name : 天天赚
             * orderAmount : 2500000
             * assetTotalAmount : 2500000
             * settleBenefit : 0
             * status : 操盘中
             * startTradingDate : 2017-09-12
             * endTradingDate : 2017-09-13
             * createTime : 2017-09-12 12:21:48
             * updateTime : 2017-09-12 12:21:48
             * expire : false
             */

            private int id;
            private String name;
            private long orderAmount;
            private long assetTotalAmount;
            private long settleBenefit;
            private int status;
            private String startTradingDate;
            private String endTradingDate;
            private String createTime;
            private String updateTime;
            private int expire;
            private int type;
            private int productItemId;
            private int riskType;

            public int getRiskType() {
                return riskType;
            }

            public void setRiskType(int riskType) {
                this.riskType = riskType;
            }

            public String getProductOrderNo() {
                return productOrderNo;
            }

            public void setProductOrderNo(String productOrderNo) {
                this.productOrderNo = productOrderNo;
            }

            private long availabelCash;

            public long getAvailabelCash() {
                return availabelCash;
            }

            public void setAvailabelCash(long availabelCash) {
                this.availabelCash = availabelCash;
            }

            private String productOrderNo;

            public long getSettleMargin() {
                return settleMargin;
            }

            public void setSettleMargin(long settleMargin) {
                this.settleMargin = settleMargin;
            }

            private long settleMargin;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getProductItemId() {
                return productItemId;
            }

            public void setProductItemId(int productItemId) {
                this.productItemId = productItemId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(long orderAmount) {
                this.orderAmount = orderAmount;
            }

            public long getAssetTotalAmount() {
                return assetTotalAmount;
            }

            public void setAssetTotalAmount(long assetTotalAmount) {
                this.assetTotalAmount = assetTotalAmount;
            }

            public long getSettleBenefit() {
                return settleBenefit;
            }

            public void setSettleBenefit(long settleBenefit) {
                this.settleBenefit = settleBenefit;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getExpire() {
                return expire;
            }

            public void setExpire(int expire) {
                this.expire = expire;
            }
        }
    }

    public static class PageBean {
        /**
         * pageNo : 1
         * pageSize : 3
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
