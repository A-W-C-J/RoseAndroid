package com.rose.android.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.afollestad.materialcamera.MaterialCamera;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.rose.android.BroadcastAction;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.UserInfoManager;
import com.rose.android.contract.DialogClick;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MUploadAvatarModel;
import com.rose.android.model.MUserinfoModel;
import com.rose.android.presenter.newstruct.UserInfoPresenter;
import com.rose.android.utils.GlideApp;
import com.rose.android.utils.Utils;
import com.rose.android.view.CircleImageView;
import com.rose.android.view.ContractDetailItemView;
import com.rose.android.view.RoseDialog;
import com.rose.android.view.SelectPicPopupWindow;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.viewmodel.LoginVM;
import com.rose.android.viewmodel.UserVM;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by xiaohuabu on 17/9/6.
 */
@SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
@Route(path = "/ui/userInfoSettingActivity")
public class UserInfoSettingActivity extends BaseActivity implements UserInfoContract.View {
    private CircleImageView imageView;
    private ContractDetailItemView logout, realName, bankCard, phoneNum, loginPwd, withdrawPwd;
    private UserInfoPresenter userInfoPresenter;
    private View updateHead;
    private SelectPicPopupWindow mSelectPicPopupWindow;
    private static final int PHOTO_CODE = 10001;
    private static final int CAMERA_CODE = 10000;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = UUID.randomUUID().toString();
    //private UploadAvatarPresenter uploadAvatarPresenter;
    private File file;
    private UCrop.Options options;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_user_info_setting);
        userInfoPresenter = new UserInfoPresenter(userHttpClient, this);
        //uploadAvatarPresenter = new UploadAvatarPresenter(userHttpClient, this);
        setContentView(getRootView());
        setTitle("个人设置");
        options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(context, R.color.title_bar_color));
        options.setStatusBarColor(ContextCompat.getColor(context, R.color.title_bar_color));
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setToolbarTitle("编辑图片");
        options.setFreeStyleCropEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userInfoPresenter != null) {
            userInfoPresenter.dispose();
            userInfoPresenter = null;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        logout.setOnClickListener(v -> {
            sp.edit().remove("password").commit();
            sp.edit().remove("token").commit();
            UserInfoManager.getInstance().setToken(null);
            UserInfoManager.getInstance().setLoginStatus(false);
            LoginVM.getInstance().setLoginModel(null);
            sendBroadcast(new Intent(BroadcastAction.CLEAR_SELF_SELECT));
            finish();
        });
        if (("未实名").equals(UserVM.getInstance().getRealName())) {
            realName.setRightIconVisiable(true);
            realName.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.authRealNameActivity)
                    .withBoolean("hasUserAuthFreeze", UserVM.getInstance().isHasUserAuthFreeze())
                    .withOptionsCompat(Utils.getActivityOptionsCompat(realName)).navigation());
        } else {
            realName.setRightIconInVisiable();
        }
        bankCard.setOnClickListener(v -> {
            if (("未实名").equals(UserVM.getInstance().getRealName())) {
                RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        ARouter.getInstance().build(RouterConfig.authRealNameActivity).navigation();
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, "去认证", getString(R.string.cancel), "提示", "亲，请先完成实名认证", null).show(getSupportFragmentManager(), "authBankCard");
            } else {
                ARouter.getInstance().build(RouterConfig.authBankCardActivity)
                        .withString("hasBankCard", UserVM.getInstance().getBankCard())
                        .withBoolean("hasUserAuthFreeze", UserVM.getInstance().isHasUserAuthFreeze())
                        .withOptionsCompat(Utils.getActivityOptionsCompat(bankCard)).navigation();
            }
        });
        phoneNum.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterConfig.authPhoneActivity)
                    .withOptionsCompat(Utils.getActivityOptionsCompat(phoneNum)).navigation();

        });
        updateHead.setOnClickListener(v -> {
            mSelectPicPopupWindow.showAtLocation(findViewById(R.id.ll_update_head), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            mSelectPicPopupWindow.setOutsideTouchable(false);
        });
        withdrawPwd.setOnClickListener(v -> {
            if (("未实名").equals(UserVM.getInstance().getRealName())) {
                RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        ARouter.getInstance().build(RouterConfig.authRealNameActivity).navigation();
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, "去认证", getString(R.string.cancel), "提示", "亲，请先完成实名认证", null).show(getSupportFragmentManager(), "authBankCard");
            } else if (("未绑定").equals(UserVM.getInstance().getBankCard())) {
                RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {
                        ARouter.getInstance().build(RouterConfig.authBankCardActivity).navigation();
                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, "去认证", getString(R.string.cancel), getString(R.string.hint), "亲，请先绑定银行卡", null)
                        .show(getSupportFragmentManager(), "dialog");
            } else {
                ARouter.getInstance().build(RouterConfig.setWithdrawalPwaActivity).withBoolean("hasBind", UserVM.getInstance().isHasSetWithdrawPwd())
                        .navigation();
            }
        });
        loginPwd.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.updateLoginPwdActivity).navigation());
    }

    @Override
    public void initViews() {
        super.initViews();
        updateHead = findViewById(R.id.ll_update_head);
        imageView = findViewById(R.id.iv_head);
        logout = findViewById(R.id.ci_logout);
        realName = findViewById(R.id.ci_real_name);
        bankCard = findViewById(R.id.ci_bank_card);
        phoneNum = findViewById(R.id.ci_phone_num);
        loginPwd = findViewById(R.id.ci_login_pwd);
        withdrawPwd = findViewById(R.id.ci_withdraw_pwd);
        realName.setRightIconVisiable(true);
        bankCard.setRightIconVisiable(true);
        phoneNum.setRightIconVisiable(true);
        loginPwd.setRightIconVisiable(true);
        loginPwd.setRihtTextColor(ContextCompat.getColor(context, R.color.title_color_2));
        withdrawPwd.setRightIconVisiable(true);
        if (UserVM.getInstance().getUserInfoModel() != null) {
            GlideApp.with(context).load(UserVM.getInstance().getHeadUrl()).into(imageView);
        }
        realName.setRightText(UserVM.getInstance().getRealName());
        if (("未实名").equals(UserVM.getInstance().getRealName())) {
            realName.setRihtTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            realName.setRihtTextColor(ContextCompat.getColor(context, R.color.title_color_2));
        }
        bankCard.setRightText(UserVM.getInstance().getBankCard());
        if (("未绑定").equals(UserVM.getInstance().getBankCard())) {
            bankCard.setRihtTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            bankCard.setRihtTextColor(ContextCompat.getColor(context, R.color.title_color_2));
        }
        phoneNum.setRihtTextColor(ContextCompat.getColor(context, R.color.title_color_2));
        phoneNum.setRightText(UserVM.getInstance().getPhone());
        loginPwd.setRightText(UserVM.getInstance().getLoginPwd());
        withdrawPwd.setRightText(UserVM.getInstance().getWithdrawPwd());
        if (("未设置").equals(UserVM.getInstance().getWithdrawPwd())) {
            withdrawPwd.setRihtTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            withdrawPwd.setRihtTextColor(ContextCompat.getColor(context, R.color.title_color_2));
        }
        mSelectPicPopupWindow = new SelectPicPopupWindow(context);
        mSelectPicPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        setListener();
    }

    private void setListener() {
        mSelectPicPopupWindow.setCameraSelectListener(() -> takeCameraPhoto());

        mSelectPicPopupWindow.setPhotoSelectListener(() -> ImagePicker.with(this).setCameraOnly(false).setMultipleMode(true).setFolderMode(false).setShowCamera(false)
                .setImageTitle("照片").setDoneTitle("完成").setMaxSize(1).setSavePath("YQZ").setKeepScreenOn(false).start());
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("ANDROID_EXTERNAL_FILE_ACCESS")
    public void takeCameraPhoto() {
        File saveDir = null;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            saveDir = new File(Environment.getExternalStorageDirectory(), "YqzCamera");
            saveDir.mkdirs();
        }
        MaterialCamera materialCamera = new MaterialCamera(this).labelConfirm(R.string.sure).labelRetry(R.string.retry)
                .saveDir(saveDir)
                .showPortraitWarning(true)
                .allowRetry(true)
                .defaultToFrontFacing(false);
        materialCamera.stillShot().start(CAMERA_CODE);
    }


    public void requestUserInfo() {
        showLoadDialog();
        userInfoPresenter.requestUserInfo();
        userInfoPresenter.requestUserAccount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestUserInfo();
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("ANDROID_BROADCAST")
    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        if (baseModel instanceof MUserinfoModel) {
            initViews();
            initListener();
        } else if (baseModel instanceof MUploadAvatarModel) {
            ToastWithIcon.init("修改头像成功").show();
            LoginVM.getInstance().setHeadUrl(((MUploadAvatarModel) baseModel).getData().getAvatarLink());
            UserVM.getInstance().setHeadUrl(((MUploadAvatarModel) baseModel).getData().getAvatarLink());
            GlideApp.with(context).load(((MUploadAvatarModel) baseModel).getData().getAvatarLink()).into(imageView);
            Intent intent = new Intent(BroadcastAction.UPDATE_AVATAR);
            sendBroadcast(intent);
        }
        return super.requestCallBack(baseModel);
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("PATH_TRAVERSAL_IN")
    private void pictureCrop(Uri uri, Activity activity) {
        if (uri != null) {
            String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
            destinationFileName += ".jpg";
            UCrop.of(uri, Uri.fromFile(new File(activity.getCacheDir(), destinationFileName)))
                    .withAspectRatio(3, 2)
                    .withMaxResultSize(720, 240).withOptions(options)
                    .start(activity);
        }
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE", "PATH_TRAVERSAL_IN"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Config.RC_PICK_IMAGES) { // 相册
                ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                if (images != null && !images.isEmpty()) {
                    Uri uri = Uri.parse("file://" + images.get(0).getPath());
                    pictureCrop(uri, UserInfoSettingActivity.this);
                } else {
                    ToastWithIcon.init("图片选取失败").show();
                }
            } else if (CAMERA_CODE == requestCode) { // 相机
                if (data.getData() != null) {
                    pictureCrop(data.getData(), UserInfoSettingActivity.this);
                } else {
                    ToastWithIcon.init("图片拍摄失败").show();
                }
            } else if (UCrop.REQUEST_CROP == requestCode) { // 裁剪
                Uri uri = UCrop.getOutput(data);
                String path = null;
                if (uri != null && (path = uri.getPath()) != null) {
                    file = new File(path);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("imageName", file.getName(), requestBody);

                    // TODO: 20/11/2018 暂时不处理头像上传 
                    //upload(body);
                }
            }
        }
    }


    /**
     * 新接口 UserInfoContrace
     */


    // TODO: 20/11/2018 暂时不支持
    /**
     @Override public void upload(MultipartBody.Part image) {
     showLoadDialog();
     //uploadAvatarPresenter.upload(image);
     }
     */
}
