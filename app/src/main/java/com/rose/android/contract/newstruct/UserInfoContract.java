package com.rose.android.contract.newstruct;

import com.rose.android.contract.BaseContract;


/**
 * 用户信息管理 Contract
 */
public interface UserInfoContract {
    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {

        /**
         * 用户实名认证
         *
         * @param name     （身份证上的）真实姓名
         * @param idCardNo 身份证号
         */
        void userAuthenticate(String name, String idCardNo);

        /**
         * 修改用户信息
         *
         * @param nickname 昵称
         * @param name     （身份证上的）真实姓名
         * @param idCardNo 身份证号
         * @param gender   性别：0-女; 1-男
         */
        void updateUserInfo(String nickname, String name, String idCardNo, Integer gender);


        /**
         * 获取当前用户信息
         */
        void requestUserInfo();

        /**
         * 获取用户银行卡信息
         */
        void requestUserBank();

        /**
         * 绑定用户银行卡信息
         *
         * @param bankName   银行名称
         * @param bankCardNo 银行卡卡号
         */
        void bindUserBank(String bankName, String bankCardNo);

        /**
         * 获取用户账户信息
         */
        void requestUserAccount();
    }
}
