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
import com.rose.android.model.MOrderModel;
import com.rose.android.utils.Utils;
import com.rose.android.view.recyclerview.RecyclerViewUtils;


/**
 * Created by xiaohuabu on 17/9/12.
 */

public class ContractHistoryListAdapter extends RecyclerView.Adapter<ContractHistoryListAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private MOrderModel.DataBean dataBean;
    private ClickResponseListener clickResponseListener;

    public ContractHistoryListAdapter(RecyclerView recyclerView, MOrderModel.DataBean dataBean, ClickResponseListener clickResponseListener, Context context) {
        this.recyclerView = recyclerView;
        this.dataBean = dataBean;
        this.clickResponseListener = clickResponseListener;
        this.context = context;
    }

    public void updateData(MOrderModel.DataBean dataBean) {
        this.dataBean = dataBean;
        notifyDataSetChanged();
    }

    public String getOrderName(int position) {
        return dataBean == null ? "" : dataBean.getProductOrderList().get(position).getName();
    }

    public int getOrderId(int position) {
        return dataBean == null ? 0 : dataBean.getProductOrderList().get(position).getId();
    }

    public int getProductItemId(int position) {
        return dataBean == null ? 0 : dataBean.getProductOrderList().get(position).getProductItemId();
    }

    private Context context;

    public boolean isActivity(int position) {
        if (dataBean == null) {
            return false;
        } else if (dataBean.getProductOrderList().get(position).getType() == 2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contract_list_item, parent, false);
        return new ContractHistoryListAdapter.ViewHolder(view, clickResponseListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MOrderModel.DataBean.ProductOrderListBean data = dataBean.getProductOrderList().get(position);
        holder.title.setText(data.getName());
        holder.date.setText(data.getStartTradingDate()
                + " 至 " + data.getEndTradingDate());
        holder.contractAmount.setText(Utils.formatWithScale(Utils.divide1000(data.getOrderAmount()), 2));
        holder.accumulatedForProfitAndLoss.setText(Utils.formatWithScale(Utils.divide1000(data.getSettleBenefit()), 2));
        holder.totalAssets.setText(Utils.formatWithScale(Utils.divide1000(data.getAssetTotalAmount()), 2));
        holder.tag.setBackgroundResource(R.drawable.shape_grey_btn);
        holder.tag.setText("已结束");
        holder.expire.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dataBean.getProductOrderList() == null ? 0 : dataBean.getProductOrderList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView tag;
        private TextView date;
        private TextView totalAssets;
        private TextView accumulatedForProfitAndLoss;
        private TextView contractAmount;
        private ImageView expire;
        ClickResponseListener clickResponseListener;

        public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            tag = (TextView) itemView.findViewById(R.id.tv_tag);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            totalAssets = (TextView) itemView.findViewById(R.id.tv_total_assets);
            accumulatedForProfitAndLoss = (TextView) itemView.findViewById(R.id.tv_accumulated_profit_loss);
            contractAmount = (TextView) itemView.findViewById(R.id.tv_contract_amount);
            expire = (ImageView) itemView.findViewById(R.id.iv_expire);
            this.clickResponseListener = clickResponseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, ContractHistoryListAdapter.ViewHolder.this));
        }
    }
}
