package com.rose.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.peng.one.push.OnePush;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.rose.android.model.MVersionModel;
import com.rose.android.network.HttpClient;
import com.rose.android.utils.AllDialogShowStrategy;
import com.rose.android.utils.DynamicTimeFormat;
import com.rose.android.utils.GlideApp;
import com.rose.android.utils.OkhttpCheckWorker;
import com.rose.android.utils.OkhttpDownloadWorker;
import com.rose.android.utils.Utils;
import com.rose.android.viewmodel.UserVM;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


/**
 * Created by xiaohuabu on 17/9/6.
 */

@SuppressFBWarnings({"ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", "DM_DEFAULT_ENCODING"})
public class RoseApplication extends MultiDexApplication {
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            layout.setPrimaryColorsId(R.color.bg_color_1, R.color.color_gold);
            return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> new ClassicsFooter(context).setDrawableSize(20).setAccentColorId(R.color.color_gold));
    }

    {

        PlatformConfig.setWeixin(BuildConfig.WX_APPID, BuildConfig.WX_KEY);
        PlatformConfig.setQQZone(BuildConfig.QQ_APPID, BuildConfig.QQ_KEY);
        Config.DEBUG = BuildConfig.DEBUG;
    }

    private static RoseApplication instance;
    private Context mContext;

    @SuppressFBWarnings({"NP_NULL_ON_SOME_PATH", "EC_UNRELATED_TYPES"})
    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        UMShareAPI.get(this);
        TCAgent.LOG_ON = BuildConfig.DEBUG;
        mContext = this;
        instance = this;
        TCAgent.init(mContext);
        TCAgent.setReportUncaughtExceptions(true);
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        ViewTarget.setTagId(R.id.glide_tag);
        Context context = getApplicationContext();
        String packageName = context.getPackageName();
        String processName = getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(context, BuildConfig.BUGLY_APPID, BuildConfig.DEBUG, strategy);
        @SuppressWarnings("unchecked")
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("terminalType", "1");
        hashMap.put("versionName", Utils.getVersionName(context));
        hashMap.put("packageName", getPackageName());
        UpdateConfig.getConfig().checkEntity(new CheckEntity().setMethod("POST").setUrl(HttpClient.getUserServiceUrl() + "api/customer/verify/version")
                .setParams(hashMap)).jsonParser(httpResponse -> {
            MVersionModel mVersionModel = new Gson().fromJson(httpResponse, MVersionModel.class);
            Update update = new Update();
            update.setUpdateUrl(mVersionModel.getData().getUrl());
            update.setUpdateContent(mVersionModel.getData().getContent());
            update.setForced(mVersionModel.getData().getStatus() < 0);
            update.setIgnore(false);
            update.setStatus(mVersionModel.getData().getStatus());
            return update;
        }).updateChecker(update -> {
            if (update.getStatus() != 0) {
                return true;
            }
            return false;
        }).checkWorker(new OkhttpCheckWorker()).downloadWorker(new OkhttpDownloadWorker())
                .strategy(new AllDialogShowStrategy());
        if (BuildConfig.APPLICATION_ID.equals(processName.equals(packageName)) || BuildConfig.APPLICATION_ID.concat(":channel").equals(processName.equals(packageName))) {
            OnePush.init(this, ((platformCode, platformName) -> platformCode == 104));
            OnePush.register();
        }
        Unicorn.init(this, BuildConfig.QIYU_APPID, options(), new GlideImageLoader(mContext));
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public class GlideImageLoader implements UnicornImageLoader {
        private Context context;

        public GlideImageLoader(Context context) {
            this.context = context.getApplicationContext();
        }

        @Nullable
        @Override
        public Bitmap loadImageSync(String uri, int width, int height) {
            return null;
        }

        @Override
        public void loadImage(String uri, int width, int height, final ImageLoaderListener listener) {
            GlideApp.with(context).load(uri).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    if (listener != null) {
                        listener.onLoadComplete(drawableToBitmap(resource));
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    if (listener != null) {
                        listener.onLoadFailed(null);
                    }
                }
            });
        }
    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        UICustomization uiCustomization = new UICustomization();
        uiCustomization.msgBackgroundColor = ContextCompat.getColor(getAppContext(), R.color.bg_color_1);
        uiCustomization.tipsTextColor = ContextCompat.getColor(getAppContext(), R.color.title_color_3);
        uiCustomization.msgItemBackgroundLeft = ContextCompat.getColor(getAppContext(), R.color.title_color_1);
        uiCustomization.msgItemBackgroundRight = ContextCompat.getColor(getAppContext(), R.color.color_gold);
        uiCustomization.textMsgColorLeft = ContextCompat.getColor(getAppContext(), R.color.title_color_3);
        uiCustomization.textMsgColorRight = ContextCompat.getColor(getAppContext(), R.color.title_color_1);
        uiCustomization.titleBackgroundColor = ContextCompat.getColor(getAppContext(), R.color.title_bar_color);
        uiCustomization.titleBarStyle = 1;
        uiCustomization.rightAvatar = UserVM.getInstance().getHeadUrl();
        uiCustomization.titleCenter = true;
        options.uiCustomization = uiCustomization;
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        return options;
    }
    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
    public static Context getAppContext() {
        if (instance != null)
            return instance.mContext;
        else
            return null;

    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
