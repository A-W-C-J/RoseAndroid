package com.rose.android.contract.newstruct;


import com.rose.android.contract.BaseContract;

/**
 * 用户注册登录 Contract
 */
public interface UserSigninContract {
    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {

        /**
         * 用户注册
         *
         * @param phone         手机号
         * @param password      md5散列之后密码
         * @param captcha       验证码
         * @param promotionCode 推广码
         */
        void signup(String phone, String password, String captcha, String promotionCode);

        /**
         * 用户登录
         *
         * @param phone    手机号
         * @param password md5散列之后密码
         */
        void signin(String phone, String password);

        /**
         * 用户登出
         */
        void signout();


        /**
         * 请求验证码 （注册，重置密码使用相同类型的验证码）
         *
         * @param phone 手机号
         */
        void requestCaptcha(String phone);


        /**
         * 登录密码重置
         *
         * @param phone    手机号
         * @param password （md5散列之后的）新密码
         * @param captcha  验证码
         */
        void resetSigninPassword(String phone, String password, String captcha);
    }
}
