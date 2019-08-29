package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MRegistListModel;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

/**
 * Created by wenen on 2017/11/6.
 */

public class RegistListAdapter extends RecyclerView.Adapter<RegistListAdapter.ViewHolder> {
  private RecyclerView recyclerView;
  private MRegistListModel.DataBean dataBean;
  private ClickResponseListener clickResponseListener;

  public RegistListAdapter(RecyclerView recyclerView, MRegistListModel.DataBean dataBean, ClickResponseListener clickResponseListener, Context context) {
    this.recyclerView = recyclerView;
    this.dataBean = dataBean;
    this.clickResponseListener = clickResponseListener;
    this.context = context;
  }

  public void updateData(MRegistListModel.DataBean dataBean) {
    this.dataBean = dataBean;
    notifyDataSetChanged();
  }

  private Context context;

  @Override
  public RegistListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.regist_list_item, parent, false);
    return new RegistListAdapter.ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(RegistListAdapter.ViewHolder holder, int position) {
    MRegistListModel.DataBean.PartnerListBean data = dataBean.getPartnerList().get(position);
    holder.phone.setText(data.getPhone());
    holder.status.setText(data.getComment());
    holder.date.setText(data.getRegistTime());
  }

  @Override
  public int getItemCount() {
    return dataBean.getPartnerList() == null ? 0 : dataBean.getPartnerList().size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView phone;
    private TextView date;
    private TextView status;

    ClickResponseListener clickResponseListener;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      phone = (TextView) itemView.findViewById(R.id.phone);
      status = itemView.findViewById(R.id.status);
      date = itemView.findViewById(R.id.date);
      this.clickResponseListener = clickResponseListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, RegistListAdapter.ViewHolder.this));
    }
  }
}
