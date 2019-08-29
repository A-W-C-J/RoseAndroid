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
import com.rose.android.model.MWalletCashFlowListModel;
import com.rose.android.ui.activity.ScoreActivity;
import com.rose.android.utils.Utils;

import java.util.List;


/**
 * Created by xiaohuabu on 17/10/11.
 */

public class WalletCashFlowListAdapter extends RecyclerView.Adapter<WalletCashFlowListAdapter.ViewHolder> {
  private ClickResponseListener clickResponseListener;
  private Context context;
  private List<MWalletCashFlowListModel.DataBean.UserWalletFlowListBean> userWalletCashFlowListBeans;

  public WalletCashFlowListAdapter(ClickResponseListener clickResponseListener, Context context, MWalletCashFlowListModel walletCashFlowListModel) {
    this.clickResponseListener = clickResponseListener;
    this.context = context;
    if (walletCashFlowListModel != null) {
      this.userWalletCashFlowListBeans = walletCashFlowListModel.getData().getUserWalletCashFlowList();
    }
  }

  public void updateData(MWalletCashFlowListModel walletCashFlowListModel) {
    if (walletCashFlowListModel != null) {
      this.userWalletCashFlowListBeans = walletCashFlowListModel.getData().getUserWalletCashFlowList();
    }
    notifyDataSetChanged();
  }

  @Override
  public WalletCashFlowListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.wallet_cash_flow_list_item, parent, false);
    return new WalletCashFlowListAdapter.ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(WalletCashFlowListAdapter.ViewHolder holder, int position) {
    holder.name.setText(userWalletCashFlowListBeans.get(position).getName());
    if (userWalletCashFlowListBeans.get(position).getFlow() < 0) {
      if (clickResponseListener instanceof ScoreActivity) {
        holder.profit.setText(String.format("%s",Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
            .getFlow()), 0)));
        holder.balance.setText(Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
            .getBalance()), 0));
      } else {
        holder.profit.setText(String.format("%s",Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
            .getFlow()), 2)));
        holder.balance.setText(Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
            .getBalance()), 2));
      }
      holder.profit.setTextColor(ContextCompat.getColor(context, R.color.green));
    } else {
      if (clickResponseListener instanceof ScoreActivity) {
        holder.profit.setText(String.format("+%s",Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
            .getFlow()), 0)));
        holder.balance.setText(String.format(Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
            .getBalance()), 0)));
        holder.profit.setTextColor(ContextCompat.getColor(context, R.color.red));
      } else {
        if (userWalletCashFlowListBeans.get(position)
            .getType() == 1 || userWalletCashFlowListBeans.get(position)
            .getType() == 3 && userWalletCashFlowListBeans.get(position)
            .getFlow() == 0) {
          holder.profit.setText(String.format("-%s", Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
              .getFlow()), 2)));
          holder.profit.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
          holder.profit.setText(String.format("+%s", Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
              .getFlow()), 2)));
          holder.profit.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.balance.setText(Utils.formatWithScale(Utils.divide1000(userWalletCashFlowListBeans.get(position)
            .getBalance()), 2));
      }

    }
    holder.date.setText(userWalletCashFlowListBeans.get(position)
        .getCreateTime());
  }

  @Override
  public int getItemCount() {
    return userWalletCashFlowListBeans == null ? 0 : userWalletCashFlowListBeans.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView name, balance, profit, date;
    ClickResponseListener clickResponseListener;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      name = (TextView) itemView.findViewById(R.id.name);
      balance = (TextView) itemView.findViewById(R.id.balance);
      profit = (TextView) itemView.findViewById(R.id.profit);
      date = (TextView) itemView.findViewById(R.id.date);
      this.clickResponseListener = clickResponseListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(getAdapterPosition());
    }
  }
}
