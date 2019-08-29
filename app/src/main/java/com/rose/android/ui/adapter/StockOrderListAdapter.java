package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MStockOrderListModel;
import com.rose.android.ui.activity.DealOrExchangeActivity;
import com.rose.android.utils.Utils;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.util.ArrayList;

/**
 * Created by xiaohuabu on 17/9/21.
 */

public class StockOrderListAdapter extends RecyclerView.Adapter<StockOrderListAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<MStockOrderListModel.DataBean.StockOrderListBean> stockOrderListModel;
    private ClickResponseListener clickResponseListener;
    private Context context;
    private int orderStatus;

    public StockOrderListAdapter(RecyclerView recyclerView, ArrayList<MStockOrderListModel.DataBean.StockOrderListBean> stockOrderListModel, ClickResponseListener clickResponseListener, int orderStatus) {
        this.recyclerView = recyclerView;
        this.stockOrderListModel = stockOrderListModel;
        this.clickResponseListener = clickResponseListener;
        this.orderStatus = orderStatus;
    }

    public void updateData(ArrayList<MStockOrderListModel.DataBean.StockOrderListBean> stockOrderListModel) {
        this.stockOrderListModel = stockOrderListModel;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.holding_item_layout, parent, false);
        return new ViewHolder(view, clickResponseListener);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stockNum.setText(stockOrderListModel.get(position).getSymbol());
        holder.title.setText(stockOrderListModel.get(position).getName());
        switch (stockOrderListModel.get(position).getAction()) {
            case 0:
                holder.type.setText("买入");
                break;
            case 1:
                holder.type.setText("卖出");
                break;
            case 2:
                holder.type.setText("回购");
                break;
            default:
                holder.type.setText("买入");
                break;
        }
        holder.type.setText(stockOrderListModel.get(position).getAction() == 0 ? "买入" : "卖出");
        holder.type.setTextColor(stockOrderListModel.get(position).getAction() == 0
                ? ContextCompat.getColor(context, R.color.red) : ContextCompat.getColor(context, R.color.green));
        switch (stockOrderListModel.get(position).getStatus()) {
            case 10:
                holder.status.setText("委托中");
                holder.count.setText(String.valueOf(stockOrderListModel.get(position).getTotalQuantity()));
                break;
            case 20:
                holder.status.setText("已取消");
                holder.count.setText(String.valueOf(stockOrderListModel.get(position).getTotalQuantity()));
                break;
            case 30:
                if (orderStatus == 30) {
                    holder.status.setText("已成交");
                    holder.count.setText(String.valueOf(stockOrderListModel.get(position).getVolume()));
                } else {
                    holder.status.setText("全成交");
                    holder.count.setText(String.valueOf(stockOrderListModel.get(position).getTotalQuantity()));
                }
                holder.count.setText(String.valueOf(stockOrderListModel.get(position).getVolume()));
                break;
            case 15:
                holder.status.setText("撤单中");
                holder.count.setText(String.valueOf(stockOrderListModel.get(position).getTotalQuantity()));
                break;
            case 25:
                if (orderStatus == 30) {
                    holder.status.setText("已成交");
                    holder.count.setText(String.valueOf(stockOrderListModel.get(position).getVolume()));
                } else {
                    holder.status.setText("部分成交");
                    holder.count.setText(String.valueOf(stockOrderListModel.get(position).getTotalQuantity()));
                }
                break;
            default:
                break;
        }
        if (clickResponseListener instanceof DealOrExchangeActivity) {
            holder.imageView.setVisibility(View.GONE);
        }
        if ("MKT".equals(stockOrderListModel.get(position).getOrderType())) {
            if (orderStatus == 10) {
                holder.price.setText("市价");
            } else {
                holder.price.setText(Utils.formatWithScale(Utils.divide1000(stockOrderListModel.get(position).getAveragePrice()), 2));
            }
        } else if (orderStatus == 30) {
            holder.price.setText(Utils.formatWithScale(Utils.divide1000(stockOrderListModel.get(position).getAveragePrice()), 2));
        } else {
            holder.price.setText(Utils.formatWithScale(Utils.divide1000(stockOrderListModel.get(position).getLimitPrice()), 2));
        }
        holder.date.setText(stockOrderListModel.get(position).getTradeTime().split(" ")[0]);
        holder.time.setText(stockOrderListModel.get(position).getTradeTime().split(" ")[1]);
        if (stockOrderListModel.get(position).isHasSelect()) {
            holder.imageView.setImageResource(R.mipmap.ic_select);
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_unselect);
        }
    }

    @Override
    public int getItemCount() {
        return stockOrderListModel == null ? 0 : stockOrderListModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, stockNum, price, count, status, type, date, time;
        ClickResponseListener clickResponseListener;
        private ImageView imageView;

        public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            stockNum = (TextView) itemView.findViewById(R.id.stock_num);
            price = (TextView) itemView.findViewById(R.id.price);
            count = (TextView) itemView.findViewById(R.id.count);
            status = (TextView) itemView.findViewById(R.id.status);
            type = (TextView) itemView.findViewById(R.id.type);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            this.clickResponseListener = clickResponseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, ViewHolder.this));
        }
    }
}
