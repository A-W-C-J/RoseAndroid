package com.rose.android.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.GetTaskListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MTaskModel;
import com.rose.android.network.HttpClient;
import com.rose.android.presenter.GetTaskListPresenter;
import com.rose.android.ui.adapter.TaskListAdapter;
import com.rose.android.view.ToastWithIcon;

@Route(path = "/ui/taskCenterActivity", extras = 1)
public class TaskCenterActivity extends BaseActivity implements GetTaskListContract.View {
    private MTaskModel taskModel;
    private GetTaskListPresenter getTaskListPresenter;
    private RecyclerView recyclerView;
    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(R.layout.activity_task_center);
        setContentView(getRootView());
        setTitle("任务中心");
        getTaskListPresenter = new GetTaskListPresenter(userHttpClient, this);
        initViews();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getTaskListPresenter != null) {
            getTaskListPresenter.dispose();
            getTaskListPresenter = null;
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        recyclerView = (RecyclerView) findViewById(R.id.rcl_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        taskListAdapter = new TaskListAdapter(position -> {
            if (position != 0 && position != taskModel.getData().getTaskList().get(0).getList().size() + 1) {
                if (position > 0 && position <= taskModel.getData().getTaskList().get(0).getList().size()) {
                    if (taskModel.getData().getTaskList().get(0).getList().get(position - 1).getStatus() == 0) {
                        if ("goto-native".equals(taskModel.getData().getTaskList().get(0).getList().get(position - 1).getToProtocol())) {
                            try {
                                if (taskModel.getData().getTaskList().get(0).getList().get(position - 1).getToUrl().split("/").length > 3) {
                                    String[] strings = taskModel.getData().getTaskList().get(0).getList().get(position - 1).getToUrl().split("/");
                                    ARouter.getInstance().build(strings[0] + "/" + strings[1] + "/" + strings[2]).withInt("position", Integer.parseInt(strings[3])).navigation();
                                } else {
                                    ARouter.getInstance().build(taskModel.getData().getTaskList().get(0).getList().get(position - 1).getToUrl()).navigation(this, new NavigationCallback() {
                                        @Override
                                        public void onFound(Postcard postcard) {

                                        }

                                        @Override
                                        public void onLost(Postcard postcard) {

                                        }

                                        @Override
                                        public void onArrival(Postcard postcard) {

                                        }

                                        @Override
                                        public void onInterrupt(Postcard postcard) {

                                        }
                                    });
                                }
                            } catch (Exception e) {
                                ToastWithIcon.init(String.format("路径%s跳转失败", taskModel.getData().getTaskList().get(0).getList().get(position - 1).getToUrl())).show();
                            }
                        } else {
                            String toUrl = HttpClient.getUserServiceUrl() + taskModel.getData().getTaskList().get(0).getList().get(position - 1).getToUrl();
                            ARouter.getInstance().build(RouterConfig.webActivity).withString("url", toUrl).withBoolean("hasHost", true).navigation();
                        }
                    }
                } else {
                    if (taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getStatus() == 0) {
                        if ("goto-native".equals(taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getToProtocol())) {
                            try {
                                if (taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getToUrl().split("/").length > 3) {

                                    String[] strings = taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getToUrl().split("/");
                                    ARouter.getInstance().build(strings[0] + "/" + strings[1] + "/" + strings[2]).withInt("position", Integer.parseInt(strings[3])).navigation();

                                } else {
                                    ARouter.getInstance().build(taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getToUrl()).navigation();
                                }
                            } catch (Exception e) {
                                ToastWithIcon.init(String.format("路径%s跳转失败", taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getToUrl())).show();
                            }
                        } else {
                            String toUrl = HttpClient.getUserServiceUrl() + taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getToUrl();
                            ARouter.getInstance().build(RouterConfig.webActivity).withString("url", toUrl).withBoolean("hasHost", true).navigation();
                        }
                    }
                }
            }
        }, context, taskModel);
        recyclerView.setAdapter(taskListAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void getTaskList() {
        showLoadDialog();
        getTaskListPresenter.getTaskList();
    }

    @Override
    public BaseModel requestCallBack(BaseModel baseModel) {
        dismissLoadDialog();
        if (baseModel instanceof MTaskModel) {
            taskModel = (MTaskModel) baseModel;
            taskListAdapter.updateData(taskModel);
        }
        return super.requestCallBack(baseModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTaskList();
    }
}
