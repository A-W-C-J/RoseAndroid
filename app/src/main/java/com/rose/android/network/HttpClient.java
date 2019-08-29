package com.rose.android.network;

import android.content.Context;

import com.rose.android.BuildConfig;
import com.rose.android.model.MAddMarginModel;
import com.rose.android.model.MAllCouponsModel;
import com.rose.android.model.MAssetTotalModel;
import com.rose.android.model.MAuthBankCardModel;
import com.rose.android.model.MAuthCodeModel;
import com.rose.android.model.MAuthModel;
import com.rose.android.model.MAuthPhoneModel;
import com.rose.android.model.MAuthRealNameModel;
import com.rose.android.model.MBannersModel;
import com.rose.android.model.MBrokerageWithdrawModel;
import com.rose.android.model.MCouponExchangeModel;
import com.rose.android.model.MCoupons;
import com.rose.android.model.MExchangeCouponModel;
import com.rose.android.model.MFlowsModel;
import com.rose.android.model.MGuaguaModel;
import com.rose.android.model.MHostModel;
import com.rose.android.model.MLimitedBuyListModel;
import com.rose.android.model.MLoginModel;
import com.rose.android.model.MLoginPasswordModel;
import com.rose.android.model.MMinAddMarginModel;
import com.rose.android.model.MNoticeModel;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.model.MOrderModel;
import com.rose.android.model.MOrderPositionsModel;
import com.rose.android.model.MPartnerInfoModel;
import com.rose.android.model.MPostOrderModel;
import com.rose.android.model.MPreOrderModel;
import com.rose.android.model.MProductListModel;
import com.rose.android.model.MProductOrderBrodcastModel;
import com.rose.android.model.MRegiestedModel;
import com.rose.android.model.MRegistListModel;
import com.rose.android.model.MRevokeModel;
import com.rose.android.model.MSelectStockDeleteModel;
import com.rose.android.model.MSelfSelectListModel;
import com.rose.android.model.MSelfSelectStockModel;
import com.rose.android.model.MSettleModel;
import com.rose.android.model.MShareInfoModel;
import com.rose.android.model.MShoumiModel;
import com.rose.android.model.MStockHoldingModel;
import com.rose.android.model.MStockListModel;
import com.rose.android.model.MStockMarketInfoModel;
import com.rose.android.model.MStockOrderListModel;
import com.rose.android.model.MStockOrderModel;
import com.rose.android.model.MTaskModel;
import com.rose.android.model.MTradeContractModel;
import com.rose.android.model.MUpdateUserInfoModel;
import com.rose.android.model.MUploadAvatarModel;
import com.rose.android.model.MUserinfoModel;
import com.rose.android.model.MVersionModel;
import com.rose.android.model.MWalletCashFlowListModel;
import com.rose.android.model.MWithdrawalPwdModel;
import com.rose.android.model.MWithdrawlModel;
import com.rose.android.model.MZoomInfoModel;
import com.rose.android.model.MZoomModel;
import com.rose.android.model.newstruct.UserAccountModel;
import com.rose.android.model.newstruct.UserInfoModel;
import com.rose.android.model.newstruct.UserSigninModel;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by xiaohuabu on 17/8/29.
 */

public interface HttpClient {
    public static String getUserServiceUrl() {
        return HttpClient.Builder.getUserServiceUrl();
    }

    class Builder {
        private static String userServiceUrl;

        public static String getUserServiceUrl() {
            return userServiceUrl;
        }

        public static HttpClient getUserService(Context context) {
            if (context.getSharedPreferences("sp", MODE_PRIVATE).getString("domain", "").length() > 0) {
                String domain = context.getSharedPreferences("sp", MODE_PRIVATE).getString("domain", "");
                String protocal = context.getSharedPreferences("sp", MODE_PRIVATE).getString("protocal", "");
                userServiceUrl = protocal + domain + "/";
            } else {
                userServiceUrl = BuildConfig.USER_SERVER;
            }
            return HttpUtils.getInstance(context)
                    .getUserServer(HttpClient.class, userServiceUrl);
        }

        public static HttpClient getHostService(Context context) {
            return HttpUtils.getInstance(context)
                    .getHostServer(HttpClient.class, BuildConfig.HOST_SERVER);
        }
    }


    // 旧接口

    @POST("api/customer/user")
    Observable<MRegiestedModel> registered(
            @Query("username") String username,
            @Query("password") String password,
            @Query("captcha") String captcha
    );

    @GET("api/user")
    Observable<MUserinfoModel> getUserInfo(

    );

    //用户登录
    @POST("api/user/signin")
    Observable<MLoginModel> login(
            @Query("phone") String username,
            @Query("password") String password
    );

    @POST("api/customer/verify/captcha")
    Observable<MAuthCodeModel> getAuthCode(
            @Query("phone") String phone,
            @Query("type") String type
    );

    @PATCH("api/customer/user")
    Observable<MUpdateUserInfoModel> updateUserInfo(
            @Query("username") String username,
            @Query("password") @Nullable String password,
            @Query("captcha") @Nullable String captcha,
            @Query("nickname") @Nullable String nickname

    );

    @GET("api/customer/products")
    Observable<MProductListModel> getProductList(
            @Query("type") int type
    );

    @GET("api/customer/product/pre-order")
    Observable<MPreOrderModel> getPreOrder(
            @Query("productItemId") int productItemId,
            @Query("loan") long loan
    );

    @GET("api/customer/promotion/banners")
    Observable<MBannersModel> getBanners(
    );

    @POST("api/customer/product/order")
    Observable<MPostOrderModel> postOrder(
            @Query("productItemId") int productItemId,
            @Query("loan") long loan,
            @Query("coupon_id") @Nullable Integer coupon_id
    );

    @GET("api/customer/product/orders")
    Observable<MOrderModel> getOrders(
            @Query("pageNo") Integer pageNo,
            @Query("pageSize") @Nullable Integer pageSize,
            @Query("status") @Nullable String status,
            @Query("type") @Nullable String type,
            @Query("symbol") @Nullable String symbol
    );

    @GET("api/customer/user/coupons")
    Observable<MCoupons> getCoupons(
            @Query("productId") @Nullable Integer productId
    );

    @POST("api/customer/mall/user-coupon")
    Observable<MExchangeCouponModel> postCoupons(
            @Query("couponMallId") int couponId
    );

    @GET("api/customer/mall/coupons")
    Observable<MAllCouponsModel> getAllCoupons(

    );

    @GET("api/customer/product/order/{orderId}")
    Observable<MOrderDetailsModel> getOrderDetails(
            @Path("orderId") int orderId
    );

    @GET("api/customer/stock/limited-stocks")
    Observable<MLimitedBuyListModel> getLimitedList(

    );

    @POST("api/customer/user/coupon")
    Observable<MCouponExchangeModel> exchangeCoupon(
            @Query("couponId") @NonNull Integer couponId
    );

    @POST("api/customer/stock/order")
    Observable<MStockOrderModel> postStockOrder(
            @Query("totalQuantity") int totalQuantity,
            @Query("symbol") String symbole,
            @Query("action") String action,
            @Query("limitPrice") long limitPrice,
            @Query("productOrderId") int productOrderId
    );

    @POST("api/customer/product/positions")
    Observable<MStockHoldingModel> getStockHolding(
            @Query("pageNo") int pageNo,
            @Query("pageSize") @Nullable int pageSize,
            @Query("productOrderId") int productOrderId
    );

    @POST("api/customer/stock//order/revoke")
    Observable<MRevokeModel> postRevoke(
            @Query("stockOrderNo") String stockOrderNo,
            @Query("productOrderId") String productOrderId
    );

    @GET("api/customer/stock/orders")
    Observable<MStockOrderListModel> getStockOrderList(
            @Query("productOrderId") int productOrderId,
            @Query("orderStatus") @Nullable Integer orderStatus,
            @Query("timeStatus") @Nullable Integer timeStatus,
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    @GET("api/customer/stock/market")
    Observable<MStockMarketInfoModel> getStockMarketInfo(
            @Query("symbol") String symbol,
            @Query("productOrderId") @Nullable Integer productOrderId
    );

    @GET("api/customer/product/order/positions")
    Observable<MOrderPositionsModel> postOrderPositions(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize,
            @Query("productOrderId") @Nullable String productOrderId
    );

    @GET("api/customer/user/auth")
    Observable<MAuthModel> getAuth();

    @POST("api/customer/user/auth")
    Observable<MAuthModel> postAuth(
            @Query("realName") String realName,
            @Query("idCardNo") String idCardNo
    );

    @POST("api/customer/product/order/settle")
    Observable<MSettleModel> postSettle(
            @Query("productOrderId") String productOrderId
    );

    @GET("api/customer/product/order/flows")
    Observable<MFlowsModel> getFlows(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize,
            @Query("productOrderId") String productOrderId,
            @Query("timeStatus") @Nullable Integer timeStatus
    );

    @POST("api/customer/user/auth")
    Observable<MAuthRealNameModel> postRealName(
            @Query("realName") String realName,
            @Query("idCardNo") String idCardNo
    );

    @POST("api/customer/user/auth/bankCard")
    Observable<MAuthBankCardModel> postBankCard(
            @Query("bankCardNo") String bankCardNo,
            @Query("name") String name,
            @Query("phone") String phone
    );

    @GET("api/customer/product/agreement")
    Observable<MTradeContractModel> getTradeContract(
            @Query("productItemId") int productItemId,
            @Query("loan") long loan
    );

    @GET("api/customer/user/wallet/cash-flows")
    Observable<MWalletCashFlowListModel> getWalletCashFlowList(
            @Query("walletFlowType") int walletFlowType,
            @Query("productOrderId") Integer productOrderId,
            @Query("pageNo") @Nullable String pageNo
    );

    @GET("api/customer/user/wallet/score-flows")
    Observable<MWalletCashFlowListModel> getWalletScoreFlowList(
            @Query("walletFlowType") int walletFlowType,
            @Query("pageNo") @Nullable String pageNo
    );

    @POST("api/customer/user/wallet/withdrawal")
    Observable<MWithdrawlModel> postWithdrawl(
            @Query("amount") long amount,
            @Query("password") String password
    );

    @PATCH("api/customer/user/auth/withdrawal/password")
    Observable<MWithdrawalPwdModel> pathPassword(
            @Query("password") String password,
            @Query("oldPassword") @Nullable String oldPassword,
            @Query("phone") @Nullable String phone,
            @Query("captcha") @Nullable String captcha
    );

    @PATCH("api/customer/user/auth/phone")
    Observable<MAuthPhoneModel> patchPhone(
            @Query("phone") String phone,
            @Query("password") String password,
            @Query("captcha") String captcha
    );

    @PATCH("api/customer/user/auth/login/password")
    Observable<MLoginPasswordModel> patchLoginPassword(
            @Query("password") String password,
            @Query("oldPassword") String oldPassword
    );

    @POST("api/customer/verify/version")
    Observable<MVersionModel> postVersion(
            @Query("terminalType") int terminalType,
            @Query("versionName") String versionName,
            @Query("packageName") String packageName
    );

    @GET("api/customer/user/asset-total")
    Observable<MAssetTotalModel> getAssetTotal();

    @GET("api/customer/promotion/tasks")
    Observable<MTaskModel> getTaskList();

    @GET("host-list.txt")
    Observable<MHostModel> getHostTxt(

    );

    @GET("api/customer/promotion/share-info")
    Observable<MShareInfoModel> getShareInfo();

    @GET("api/customer/promotion/partner-info")
    Observable<MPartnerInfoModel> getPartnerInfo();

    @GET("api/customer/promotion/partner/regist-list")
    Observable<MRegistListModel> getRegistList(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    @PUT("api/customer/product/order/zoom")
    Observable<MZoomModel> putZoom(
            @Query("addLoan") @NonNull Long addLoan,
            @Query("productOrderId") String productOrderId
    );

    @GET("api/customer/product/order/zoom-info")
    Observable<MZoomInfoModel> getZoomInfo(
            @Query("addLoan") @NonNull Long addLoan,
            @Query("productOrderId") String productOrderId
    );

    @POST("api/customer/product/order/add-margin")
    Observable<MAddMarginModel> postAddMargin(
            @Query("margin") @NonNull Long margin,
            @Query("productOrderId") String productOrderId
    );

    @GET("api/customer/product/order/min-add-margin")
    Observable<MMinAddMarginModel> getMinAddMargin(
            @Query("productOrderId") String productOrderId
    );

    @POST("api/customer/promotion/brokerage/withdraw")
    Observable<MBrokerageWithdrawModel> postBrokerageWithdraw(

    );

    @Multipart
    @POST("api/customer/user/avatar")
    Observable<MUploadAvatarModel> uploadAvatar(
            @Part MultipartBody.Part imageName
    );

    @GET("api/customer/broadcast/product-order")
    Observable<MProductOrderBrodcastModel> getBrodcast();

    @GET("api/customer/news/finance")
    Observable<MNoticeModel> getNotice(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    @POST("api/customer/pay/swift")
    Observable<MGuaguaModel> postGuagua(
            @Query("type") Integer type,
            @Query("amount") Long amount,
            @Query("source") Integer source
    );

    @POST("api/customer/pay/shoumi")
    Observable<MShoumiModel> postShoumiPay(
            @Query("amount") Long amount,
            @Query("source") Integer source
    );

    @GET("api/customer/stock/stock-list")
    Observable<MStockListModel> getStockList(
            @Query("key") String key,
            @Query("pageNo") Integer pageNo,
            @Query("pageSize") Integer pageSize
    );

    @POST("api/customer/stock/self-select-stock")
    Observable<MSelfSelectStockModel> postSelfSelectStock(
            @Query("stockExchange") String stockExchange,
            @Query("stockName") String stockName,
            @Query("stockSymbol") String stockSymbol
    );

    @DELETE("api/customer/stock/self-select-stock")
    Observable<MSelectStockDeleteModel> deleteSelfSelectStock(
            @Query("stockExchange") String stockExchange,
            @Query("stockName") String stockName,
            @Query("stockSymbol") String stockSymbol
    );

    @GET("api/customer/stock/self-select-stocks")
    Observable<MSelfSelectListModel> getSelfSelectList();


    // 新接口

    /**
     * 用户登录
     *
     * @param phone    手机号
     * @param password 密码（md5后的密码）
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/signin")
    Observable<UserSigninModel> signin(
            @Field("phone") String phone,
            @Field("password") String password
    );


    /**
     * 请求用户信息
     *
     * @return
     */
    @GET("api/user")
    Observable<UserInfoModel> requestUserInfo();

    /**
     * 获取用户当前账户信息
     * @return
     */
    @GET("api/account")
    Observable<UserAccountModel> requestUserAccount();
}
