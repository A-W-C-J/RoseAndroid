package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MFlowsModel;
import com.rose.android.utils.Utils;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.util.ArrayList;

/**
 * Created by xiaohuabu on 17/9/27.
 */

public class FlowsAdapter extends RecyclerView.Adapter<FlowsAdapter.ViewHolder> {
  private Context context;
  private RecyclerView recyclerView;
  private ArrayList<MFlowsModel.DataBean.ProductOrderFlowListBean> productOrderFlowListBeans;
  private ClickResponseListener clickResponseListener;
  private boolean isFromDetails;

  public FlowsAdapter(RecyclerView recyclerView, ArrayList<MFlowsModel.DataBean.ProductOrderFlowListBean> productOrderFlowListBeans, ClickResponseListener clickResponseListener, boolean isFromDetails) {
    this.recyclerView = recyclerView;
    this.productOrderFlowListBeans = productOrderFlowListBeans;
    this.clickResponseListener = clickResponseListener;
    this.isFromDetails = isFromDetails;
  }

  public void updateData(ArrayList<MFlowsModel.DataBean.ProductOrderFlowListBean> productOrderFlowListBeans) {
    this.productOrderFlowListBeans = productOrderFlowListBeans;
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.flows_item_layout, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if (productOrderFlowListBeans.get(position).getFlow() >= 0) {
      holder.profit.setTextColor(ContextCompat.getColor(context, R.color.red));
      holder.profit.setText(String.format("+%s", Utils.formatWithThousandsSeparator(Utils.divide1000(productOrderFlowListBeans.get(position).getFlow()))));
    } else {
      holder.profit.setTextColor(ContextCompat.getColor(context, R.color.green));
      holder.profit.setText(Utils.formatWithThousandsSeparator(Utils.divide1000(productOrderFlowListBeans.get(position).getFlow())));
    }
    holder.totalAssets.setText(Utils.formatWithThousandsSeparator(Utils.divide1000(productOrderFlowListBeans.get(position).getBalance())));
    if (isFromDetails) {
      holder.validAssets.setText("操盘金额：");
    } else {
      holder.validAssets.setText("余额：");
    }
    holder.newContract.setText(productOrderFlowListBeans.get(position).getName());
//    switch (productOrderFlowListBeans.get(position).getType()) {
//      case 1:
//        holder.newContract.setText("新增合约");
//        break;
//      default:
//        break;
//    }
    holder.date.setText(productOrderFlowListBeans.get(position).getCreateTime());
  }

  @Override
  public int getItemCount() {
    return productOrderFlowListBeans == null ? 0 : productOrderFlowListBeans.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView newContract, totalAssets, profit, date, validAssets;
    private ClickResponseListener clickResponseListener;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      newContract = (TextView) itemView.findViewById(R.id.new_contract);
      totalAssets = (TextView) itemView.findViewById(R.id.tv_total_assets);
      profit = (TextView) itemView.findViewById(R.id.profit);
      date = (TextView) itemView.findViewById(R.id.date);
      validAssets = itemView.findViewById(R.id.valid_assets);
      this.clickResponseListener = clickResponseListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, FlowsAdapter.ViewHolder.this));
    }
  }
}
