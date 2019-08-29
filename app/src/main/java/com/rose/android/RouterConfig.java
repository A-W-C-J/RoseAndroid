package com.rose.android;

/**
 * Created by xiaohuabu on 17/9/12.
 */

public class RouterConfig {
    public static final String userSignin = "/ui/userSigninActivity";//登录界面
    public static final String main = "/ui/mainActivity";//主界面
    public static final String contractDetails = "/ui/ContractDetailActivity";//预下单详情
    //  public static String applicationContract = "/ui/applicationContractActivity";//预下单
    public static final String userInfoSettingActivity = "/ui/userInfoSettingActivity";//个人设置
    public static final String couponsListActivity = "/ui/couponsListActivity";//下单界面优惠券列表
    public static final String orderDetailsActivity = "/ui/orderDetailsActivity";//订单详情（小人界面）
    public static final String experienceActivity = "/ui/experienceActivity";//新手体验
    public static final String activityOrderDetailsActivity = "/ui/ActivityContractDetailsActivity";//活动合约详情（历史合约中）
    public static final String historyContractDetatisActivity = "/ui/historyContractDetatisActivity";//历史合约详情（非活动合约）
    public static final String limitedBuyActivity = "/ui/limitedBuyActivity";//限买
    public static final String commissionedTransactionActivity = "/ui/commissionedTransactionActivity";
    public static final String exchangeCouponActivity = "/ui/exchangeCouponActivity";
    public static final String webActivity = "/ui/webActivity";//网页
    public static final String dealOrExchange = "/ui/dealOrExchange";//当日成交，当日委托，历史成交，历史委托
    public static final String flowsActivity = "/ui/flowsActivity";//当日资金流水，历史资金流水
    public static final String authRealNameActivity = "/ui/authRealNameActivity";//实名认证
    public static final String guideActivity = "/ui/guideActivity";//引导
    public static final String contractFlowsActivity = "/ui/contractFlowsActivity";//合约流水
    public static final String authBankCardActivity = "/ui/authBankCardActivity";//银行卡认证
    public static final String authPhoneActivity = "/ui/authPhoneActivity";//电话认证
    public static final String cashActivity = "/ui/cashActivity";//现金账号
    public static final String scoreActivity = "/ui/scoreActivity";//积分帐户
    public static final String allCouponListActivity = "/ui/allCouponListActivity";//优惠券（我的页面）
    public static final String withdrawalActivity = "/ui/withdrawalActivity";//提现
    public static final String setWithdrawalPwaActivity = "/ui/setWithdrawalPwaActivity";//设置提现密码
    public static final String updateLoginPwdActivity = "/ui/updateLoginPwdActivity";//修改登录密码
    public static final String forgetLoginPassword = "/ui/forgetLoginPassword";//忘记登录密码
    public static final String helpCenterActivity = "/ui/helpCenterActivity";//帮助中心
    public static final String aboutActivity = "/ui/aboutActivity";//关于我们
    public static final String assetTotalActivity = "/ui/assetTotalActivity";//资产统计
    public static final String rechargeActivity = "/ui/rechargeActivity";//充值
    public static final String GuaguazhifuActivity = "/ui/guaguaRechargeActivity";
    public static final String ShoumiPayActivity = "/ui/shoumiActivity";
    public static final String taskCenterActivity = "/ui/taskCenterActivity";
    public static final String sharePartnerActivity = "/ui/sharePartnerActivity";
    public static final String shareDetailsActivity = "/ui/shareDetailsActivity";
    public static final String qrcodeActivity = "/ui/qrcodeActivity";
    public static final String zoomoutActivity = "/ui/zoomoutActivity";
    public static final String addmarginActivity = "/ui/addmarginActivity";
    public static final String stockDetailsActivity = "/ui/StockDetailActivity";
    public static final String selectContractToBuyActivity = "/ui/SelectContractToBuyActivity";
    public static final String selectContractActivity = "/ui/select_contract_activity";
    public static final String historyContractActivity = "/ui/historyContractActivity";

    public static class UrlConfig {
        public static final String userContract = "app/prot/service.html";
        public static final String experience = "app/prot/experience.html";
        public static final String tradeContract = "app/prot/trade.html";
        public static final String basicKnowledge = "app/help/info.html";
        public static final String drawout = "app/help/drawout.html";
        public static final String stockTrade = "app/help/trade.html";
        public static final String shareGuide = "app/share/explain.html";
        public static final String recharge = "app/pay/banks.html";
        public static final String fs = "app/chart/fs.html";
        public static final String kx = "app/chart/ad_kx.html";
    }
}
