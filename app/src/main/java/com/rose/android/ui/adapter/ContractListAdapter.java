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

public class ContractListAdapter extends RecyclerView.Adapter<ContractListAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private MOrderModel.DataBean dataBean;
    private ClickResponseListener clickResponseListener;
    private String type;
    private boolean isFromeSelect = false;
    private boolean isFromSelectContract = false;
    private int selectPosition;
    private ClickResponseListener itemClick;

    public ContractListAdapter(RecyclerView recyclerView, MOrderModel.DataBean dataBean, ClickResponseListener clickResponseListener, Context context, String type) {
        this.recyclerView = recyclerView;
        this.dataBean = dataBean;
        this.clickResponseListener = clickResponseListener;
        this.context = context;
        this.type = type;
    }

    public ContractListAdapter(RecyclerView recyclerView, MOrderModel.DataBean dataBean, ClickResponseListener clickResponseListener, Context context, boolean isFromeSelect) {
        this.recyclerView = recyclerView;
        this.dataBean = dataBean;
        this.clickResponseListener = clickResponseListener;
        this.context = context;
        this.isFromeSelect = isFromeSelect;
    }

    public ContractListAdapter(RecyclerView recyclerView, MOrderModel.DataBean dataBean, ClickResponseListener clickResponseListener, Context context,
                               int selectPosition, boolean isFromSelectContract, ClickResponseListener itemClick) {
        this.recyclerView = recyclerView;
        this.dataBean = dataBean;
        this.clickResponseListener = clickResponseListener;
        this.context = context;
        this.isFromSelectContract = isFromSelectContract;
        this.selectPosition = selectPosition;
        this.itemClick = itemClick;
    }

    public void updateData(MOrderModel.DataBean dataBean) {
        this.dataBean = dataBean;
        notifyDataSetChanged();
    }

    private Context context;

    public String getOrderName(int position) {
        return dataBean == null ? "" : dataBean.getProductOrderList().get(position).getName();
    }

    public int getOrderId(int position) {
        return dataBean == null ? 0 : dataBean.getProductOrderList().get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (isFromeSelect) {
            View view = LayoutInflater.from(context).inflate(R.layout.select_contract_item, parent, false);
            return new ContractListAdapter.ViewHolder(view, clickResponseListener);
        } else {
            if (isFromSelectContract) {
                View view = LayoutInflater.from(context).inflate(R.layout.select_item, parent, false);
                return new ContractListAdapter.ViewHolder(view, clickResponseListener);
            } else {
                if (type != null) {
                    View view = LayoutInflater.from(context).inflate(R.layout.experience_contract_item, parent, false);
                    return new ContractListAdapter.ViewHolder(view, clickResponseListener);
                } else {
                    View view = LayoutInflater.from(context).inflate(R.layout.contract_list_item, parent, false);
                    return new ContractListAdapter.ViewHolder(view, clickResponseListener);
                }
            }

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MOrderModel.DataBean.ProductOrderListBean data = dataBean.getProductOrderList().get(position);
        holder.title.setText(data.getName());
        if (isFromeSelect) {
            holder.contractId.setText(data.getProductOrderNo());
            holder.balance.setText(Utils.formatWithScale(Utils.divide1000(data.getAvailabelCash()), 2));
            holder.date.setText(data.getEndTradingDate().substring(5, 7) + "月"
                    + data.getEndTradingDate().substring(8, 10) + "日到期");
        } else {
            if (isFromSelectContract) {
                switch (data.getRiskType()) {
                    case 0:
                        holder.status.setText("正常");
                        break;
                    case 1:
                        holder.status.setText("警戒中");
                        break;
                    case 2:
                        holder.status.setText("止损中");
                        break;
                    default:
                        holder.status.setText("正常");
                        break;
                }
                if (position == selectPosition) {
                    holder.select.setImageResource(R.mipmap.ic_select);
                } else
                    holder.select.setImageResource(R.mipmap.ic_unselect);
                holder.date.setText(data.getEndTradingDate());
                holder.balance.setText(Utils.formatWithScale(Utils.divide1000(data.getAvailabelCash()), 2));
                holder.zc.setText(Utils.formatWithScale(Utils.divide1000(data.getAssetTotalAmount()), 2));
                holder.orderNo.setText(data.getProductOrderNo());
            } else {
                holder.date.setText(data.getStartTradingDate()
                        + " 至 " + data.getEndTradingDate());
                holder.contractAmount.setText(Utils.formatWithScale(Utils.divide1000(data.getOrderAmount()), 2));
                holder.accumulatedForProfitAndLoss.setText(Utils.formatWithScale(Utils.divide1000(data.getSettleBenefit()), 2));
                holder.totalAssets.setText(Utils.formatWithScale(Utils.divide1000(data.getAssetTotalAmount()), 2));
                if (data.getStatus() != 4) {
                    holder.tag.setText("操盘中");
                } else {
                    holder.tag.setText("已结算");
                }
                if (data.getExpire() == 1) {
                    holder.expire.setVisibility(View.VISIBLE);
                } else if (data.getExpire() == 2) {
                    holder.expire.setImageResource(R.mipmap.ic_expire_outdate);
                    holder.expire.setVisibility(View.VISIBLE);
                } else {
                    holder.expire.setVisibility(View.GONE);
                }
            }

        }

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
        private ImageView expire, select;
        private TextView contractId, balance, zc, status, viewContract, orderNo;
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
            contractId = itemView.findViewById(R.id.tv_contract_id);
            balance = itemView.findViewById(R.id.balance);
            status = itemView.findViewById(R.id.status);
            zc = itemView.findViewById(R.id.zc);
            select = itemView.findViewById(R.id.image);
            viewContract = itemView.findViewById(R.id.view_contract);
            orderNo = itemView.findViewById(R.id.order_no);
            if (viewContract != null)
                viewContract.setOnClickListener(v -> itemClick.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, ViewHolder.this)));
            this.clickResponseListener = clickResponseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, ContractListAdapter.ViewHolder.this));
        }
    }
}
