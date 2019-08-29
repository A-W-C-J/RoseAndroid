package com.rose.android.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.DialogClick;
import com.rose.android.model.MVersionModel;
import com.rose.android.utils.Utils;
import com.rose.android.view.ContractDetailItemView;
import com.rose.android.view.RoseDialog;
import com.rose.android.viewmodel.VersionVm;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;

@Route(path = "/ui/aboutActivity")
public class AboutActivity extends BaseActivity {
    private ContractDetailItemView  ci2, ci3;
    private UpdateCheckCB updateCheckCB;
    private UpdateBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_about);
        setContentView(getRootView());
        setTitle("关于我们");
        initViews();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCheckCB = new UpdateCheckCB() {
            @Override
            public void onCheckStart() {
                showLoadDialog();
            }

            @Override
            public void hasUpdate(Update update) {
                dismissLoadDialog();
            }

            @Override
            public void noUpdate() {
                dismissLoadDialog();
                RoseDialog.newInstance(new DialogClick() {
                    @Override
                    public void doPositiveClick() {

                    }

                    @Override
                    public void doNegativeClick() {

                    }
                }, getString(R.string.sure), null, getString(R.string.hint), "版本已经是最新，不需要更新", null)
                        .show(getSupportFragmentManager(), "dialog");
            }

            @Override
            public void onCheckError(Throwable t) {
                dismissLoadDialog();
            }

            @Override
            public void onUserCancel() {
                dismissLoadDialog();
            }

            @Override
            public void onCheckIgnore(Update update) {
                dismissLoadDialog();
            }
        };
    }

    @Override
    public void initViews() {
        super.initViews();
        ci2 = findViewById(R.id.ci_contract);
        ci3 = findViewById(R.id.ci_version);
        ci2.setRightIconVisiable(true);
        ci3.setRightIconVisiable(true);
        ci3.setRightText(Utils.getVersionName(context));
        if (VersionVm.getInstance().getVersionModel() != null && VersionVm.getInstance().getVersionModel().getData() != null) {
            MVersionModel.DataBean dataBean = VersionVm.getInstance().getVersionModel().getData();
            if (dataBean.getStatus() != 0) {
                ci3.setIconVisiable(true);
                ci3.setTagText("new");
            }
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        ci2.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.webActivity).withBoolean("hasHost", false).withString("url", RouterConfig.UrlConfig.userContract)
                .navigation());
        ci3.setOnClickListener(v -> {
            builder = UpdateBuilder.create();
            builder.checkCB(updateCheckCB);
            builder.check();
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        updateCheckCB = null;
        builder = null;
    }
}
