package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MTaskModel;


/**
 * Created by wenen on 2017/10/26.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
  private ClickResponseListener clickResponseListener;
  private Context context;
  private MTaskModel taskModel;
  private static final String TAG = "TaskListAdapter";

  public TaskListAdapter(ClickResponseListener clickResponseListener, Context context, MTaskModel taskModel) {
    this.clickResponseListener = clickResponseListener;
    this.context = context;
    if (taskModel != null) {
      this.taskModel = taskModel;
    }
  }

  public void updateData(MTaskModel taskModel) {
    if (taskModel != null) {
      this.taskModel = taskModel;
    }
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.vertical_stepper_item_view_layout, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if (position == 0) {
      holder.title.setText(taskModel.getData().getTaskList().get(position).getTitle());
      holder.stepperLine.setVisibility(View.GONE);
      holder.taskImage.setVisibility(View.GONE);
      holder.summary.setVisibility(View.GONE);
      holder.right.setVisibility(View.GONE);
    } else if (position == taskModel.getData().getTaskList().get(0).getList().size() + 1) {
      holder.title.setText(taskModel.getData().getTaskList().get(1).getTitle());
      holder.stepperLine.setVisibility(View.GONE);
      holder.taskImage.setVisibility(View.GONE);
      holder.summary.setVisibility(View.GONE);
      holder.right.setVisibility(View.GONE);
    } else if (position > 0 && position <= taskModel.getData().getTaskList().get(0).getList().size()) {
      holder.title.setText(taskModel.getData().getTaskList().get(0).getList().get(position - 1).getName());
      holder.summary.setText(taskModel.getData().getTaskList().get(0).getList().get(position - 1).getDescription());
      holder.right.setVisibility(taskModel.getData().getTaskList().get(0).getList().get(position - 1).getStatus() == 1 ? View.VISIBLE : View.INVISIBLE);
      holder.taskImage.setImageResource(taskModel.getData().getTaskList().get(0).getList().get(position - 1).getStatus() == 3 ? R.mipmap.ic_task_done : R.mipmap.ic_task_no_done);
      if (taskModel.getData().getTaskList().get(0).getList().get(position - 1).getHint().length() > 0
          && !("".equals(taskModel.getData().getTaskList().get(0).getList().get(position - 1).getHint()))) {
        holder.hint.setText("(" + taskModel.getData().getTaskList().get(0).getList().get(position - 1).getHint() + ")");
      }
      if (position == taskModel.getData().getTaskList().get(0).getList().size()) {
        holder.stepperLine.setVisibility(View.GONE);
      }
    } else if (position > taskModel.getData().getTaskList().get(0).getList().size()) {
      holder.title.setText(taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getName());
      holder.summary.setText(taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getDescription());
      holder.right.setVisibility(taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getStatus() == 1 ? View.VISIBLE : View.INVISIBLE);
      holder.taskImage.setImageResource(taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getStatus() == 3 ? R.mipmap.ic_task_done : R.mipmap.ic_task_no_done);
      if (taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getHint().length() > 0
          && !("".equals(taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getHint()))) {
        holder.hint.setText("(" + taskModel.getData().getTaskList().get(1).getList().get(position - 2 - taskModel.getData().getTaskList().get(0).getList().size()).getHint() + ")");
      }
      if (position == getItemCount() - 1) {
        holder.stepperLine.setVisibility(View.GONE);
      }
    }
  }

  @Override
  public int getItemCount() {
    int count = 0;
    if (taskModel != null) {
      for (int i = 0; i < taskModel.getData().getTaskList().size(); i++) {
        count += taskModel.getData().getTaskList().get(i).getList().size();
      }
      count = count + taskModel.getData().getTaskList().size();
    }
    return count;
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView taskImage;
    private TextView title, summary;
    private View right;
    private View stepperLine;
    private TextView hint;
    ClickResponseListener clickResponseListener;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      taskImage = (ImageView) itemView.findViewById(R.id.task_image);
      title = (TextView) itemView.findViewById(R.id.stepper_title);
      summary = (TextView) itemView.findViewById(R.id.stepper_summary);
      right = itemView.findViewById(R.id.arrow_right);
      stepperLine = itemView.findViewById(R.id.stepper_line);
      hint = (TextView) itemView.findViewById(R.id.hint);
      this.clickResponseListener = clickResponseListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(getAdapterPosition());
    }
  }
}
