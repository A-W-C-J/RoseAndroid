package com.rose.android.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.rose.android.RoseApplication;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.view.numbereditor.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * Created by xiaohuabu on 17/8/21.
 */

public class Utils {
    public static final String UNIT_K = "k";
    private static final String TAG = "Utils";
    public static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";
    public static final String REGEX_PWD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"WEAK_MESSAGE_DIGEST_MD5", "BAD_HEXA_CONVERSION"})
    public static String parseStrToMd5L32(String str) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes(Charset.defaultCharset()));
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public static int getWidth() {
        WindowManager manager = (WindowManager) RoseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public static int getHeight() {
        WindowManager manager = (WindowManager) RoseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 当数字字符串大于 10,000 或小于 -10,000 时候，添加‘万’单位，并使用‘银行家算法’精确（保留）到小数点后两位
     *
     * @param value
     * @return 处理后的字符串
     */
    public static String addUnitWhenBeyondThousand(BigDecimal value) {
        if (value.abs().doubleValue() >= 1000) {
            return formatWithThousandsSeparatorAndUnit(value, UNIT_K);
        }
        return formatWithThousandsSeparator(value);
    }

    private static String formatWithThousandsSeparatorAndUnit(BigDecimal value, String unit) {
        if (UNIT_K.equals(unit)) {
            value = BigDecimalUtil.divide(value.doubleValue(), 1000.000);
            return formatWithThousandsSeparator(value) + unit;
        }
        return value.toString();
    }

    /**
     * 使用‘银行家算法’精确（保留）到小数点后 scale 位
     *
     * @param value
     * @param scale 小数位数
     * @return
     */
    public static String formatWithScale(Double value, int scale) {
        return formatWithScale(new BigDecimal(value), scale);
    }

    public static String formatWithScale(BigDecimal value, int scale) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("##0");
        String pattern = "";
        for (int i = 1; i <= scale; i++) {
            if (i == 1) {
                sb.append(".0");
            } else {
                sb.append("0");
            }
        }
        pattern = sb.toString();
        decimalFormat.applyPattern(pattern);
        if (scale == 0) {
            decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        } else {
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
        }
        return decimalFormat.format(value);
    }

    public static String formatWithScale(long value, int scale) {
        return formatWithScale(new BigDecimal(value), scale);
    }

    /**
     * 使用千位分隔符分割 Long 数据 ，并使用‘银行家算法’精确（保留）到小数点后两位
     *
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparator(Long value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparator(bigDecimal);
    }

    public static String formatWithThousandsSeparatorWithoutScale(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return formatWithThousandsSeparatorWithoutScale(bigDecimal);
    }

    public static String formatWithThousandsSeparatorWithoutScale(BigDecimal value) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        decimalFormat.applyPattern("#,##0");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        return decimalFormat.format(value.doubleValue());
    }

    public static String formatWithThousandsSeparator(float value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return formatWithThousandsSeparator(bigDecimal);
    }

    /**
     * 使用千位分隔符分割 bigDecimal，并使用‘银行家算法’精确（保留）到小数点后两位
     *
     * @param value
     * @return 处理后的字符串
     */
    public static String formatWithThousandsSeparator(BigDecimal value) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        decimalFormat.applyPattern("#,##0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        return decimalFormat.format(value.doubleValue());
    }

    public static int dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);

    }

    public static String accurateTheSecondDecimalPlace(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).toString();
    }

    public static BigDecimal multiply1000(long num) {
        return new BigDecimal(num).multiply(new BigDecimal(1000));
    }

    public static BigDecimal multiply1000(double num) {
        return BigDecimal.valueOf(num).multiply(new BigDecimal(1000));
    }

    public static BigDecimal divide1000(long num) {
        return BigDecimal.valueOf(num).divide(new BigDecimal(1000));
    }

    public static ActivityOptionsCompat getActivityOptionsCompat(View view) {
        return ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
    }

    /**
     * dp 转 px
     *
     * @param value
     * @return
     */
    public static float convertDp2Px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, RoseApplication.getAppContext().getResources().getDisplayMetrics());
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static boolean isPhoneValid(String phone) {
        boolean b = Pattern.matches(REGEX_MOBILE, phone);
        if (!b) {
            ToastWithIcon.init("手机号码格式错误!").show();
        }
        return b;
    }

    public static boolean isPasswordValid(String password) {
        boolean b = Pattern.matches(REGEX_PWD, password);
        if (!b) {
            ToastWithIcon.init("密码格式错误!").show();
        }
        return b;
    }
}
