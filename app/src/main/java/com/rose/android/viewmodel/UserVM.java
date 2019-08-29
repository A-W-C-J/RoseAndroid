package com.rose.android.viewmodel;

import com.rose.android.model.newstruct.UserAccountModel;
import com.rose.android.model.newstruct.UserInfoModel;
import com.rose.android.utils.Utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Created by xiaohuabu on 17/9/5.
 */

public class UserVM {
    private static UserVM userVM;
    private String productOrderId;

    private UserVM() {
    }

    public static UserVM getInstance() {
        if (userVM == null) {
            userVM = new UserVM();
        }
        return userVM;
    }

    private UserInfoModel userInfoModel;

    private UserAccountModel userAccountModel;

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }

    public void setUserInfoModel(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public UserAccountModel getUserAccountModel() {
        return userAccountModel;
    }

    public void setUserAccountModel(UserAccountModel userAccountModel) {
        this.userAccountModel = userAccountModel;
    }

    public String getHeadUrl() {
        if (null == userInfoModel
                || null == userInfoModel.getData()
                || null == userInfoModel.getData().getUser()
                || null == userInfoModel.getData().getUser().getAvatar_uri()) {
            return "";
        }
        return userInfoModel.getData().getUser().getAvatar_uri();
    }

    public void setHeadUrl(String url) {
        if (null != userInfoModel
                && null != userInfoModel.getData()
                && null != userInfoModel.getData().getUser()) {
            userInfoModel.getData().getUser().setAvatar_uri(url);
        }
    }

    public String getPhone() {
        if (null == userInfoModel
                || null == userInfoModel.getData()
                || null == userInfoModel.getData().getUser()) {
            return "";
        }

        if (userInfoModel != null) {
            if (userInfoModel.getData() != null && userInfoModel.getData().getUser().getPhone() != null && userInfoModel.getData().getUser().getPhone().length() == 11) {
                char[] chars = userInfoModel.getData().getUser().getPhone().toCharArray();
                for (int i = 3; i < 7; i++) {
                    chars[i] = '*';
                }
                StringBuilder phone = new StringBuilder();
                for (char ch : chars) {
                    phone.append(ch);
                }
                return phone.toString();
            }
        }
        return "";
    }

    public String getBankNo() {
        if (null == userInfoModel
                || null == userInfoModel.getData()
                || null == userInfoModel.getData().getUser_bank()) {
            return "";
        }
        String userBankCardNo = userInfoModel.getData().getUser_bank().getBank_card_no();
        if (StringUtils.isEmpty(userBankCardNo)) {
            return "";
        }

        if (userBankCardNo.length() == 16 || userBankCardNo.length() == 19) {
            char[] chars = userBankCardNo.toCharArray();
            for (int i = 0; i < userBankCardNo.length() - 4; i++) {
                chars[i] = '*';
            }
            StringBuilder bankNo = new StringBuilder();
            for (int j = 0; j < chars.length; j++) {
                if (j != 0 && j % 4 == 0) {
                    bankNo.append(" ");
                }
                bankNo.append(chars[j]);
            }
            return bankNo.toString();
        }

        return "";
    }

    public String getCashAsset() {
        if (null == userAccountModel || null == userAccountModel.getData()) {
            return "";
        }

        BigDecimal bigDecimal = new BigDecimal(userAccountModel.getData().getCash_asset());
        return Utils.formatWithThousandsSeparator(bigDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_EVEN));
    }

    public String getRealName() {
        if (null == userInfoModel
                || null == userInfoModel.getData()
                || null == userInfoModel.getData().getUser()
                || null == userInfoModel.getData().getUser().getName()) {
            return "未实名";
        }
        return userInfoModel.getData().getUser().getName();
    }

    public String getBankCard() {
        String bankCardNo = getBankNo();
        if (StringUtils.isEmpty(bankCardNo)) {
            return "未绑定";
        } else {
            return "已绑定";
        }
    }

    public String getLoginPwd() {
        return "已设置";
        // TODO: 20/11/2018 已经登录的用户，密码都是已经设置过的
        /**
         if (userInfoModel != null) {
         if (userInfoModel.getData().isHasSetLoginPwd()) {
         return "已设置";
         } else {
         return "未设置";
         }
         }
         return "未设置";
         */
    }

    public String getWithdrawPwd() {
        return "已设置";
        // TODO: 20/11/2018 稍后补上
        /*
        if (userInfoModel != null) {
            if (userInfoModel.getData().isHasSetWithdrawPwd()) {
                return "已设置";
            } else {
                return "未设置";
            }
        }
        */
    }

    public long getScoreAsset() {
        if (null == userAccountModel || null == userAccountModel.getData()) {
            return 0L;
        }
        return userAccountModel.getData().getCash_asset();
    }


    public String getSelectContractId() {
        return productOrderId;
    }

    public void setProductOrderId(String productOrderId) {
        this.productOrderId = productOrderId;
    }

    public boolean isHasSetWithdrawPwd() {
        // TODO: 20/11/2018 暂时都标记为已经设置取款密码
        return true;
    }

    public boolean isHasUserAuthFreeze() {
        if (null == userInfoModel
                || null == userInfoModel.getData()
                || null == userInfoModel.getData().getUser()) {
            return true;
        }
        // 用户状态： 0: 初始 10: 有效  -10: 无效
        return 10 != userInfoModel.getData().getUser().getStatus();
    }

    public String getBankName() {
        if (null == userInfoModel
                || null == userInfoModel.getData()
                || null == userInfoModel.getData().getUser_bank()) {
            return "";
        }
        return userInfoModel.getData().getUser_bank().getBank_name();
    }
}
