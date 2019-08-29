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
import com.rose.android.model.MStockListModel;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.util.ArrayList;

/**
 * Created by wenen on 2018/1/4.
 */

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.ViewHolder> {
    private Context context;
    private RecyclerView recyclerView;
    private ClickResponseListener clickResponseListener;
    private ArrayList<MStockListModel.DataBean.StockListBean> stockListBeans;
    private ClickResponseListener onItemClick;

    public StockListAdapter(RecyclerView recyclerView, ArrayList<MStockListModel.DataBean.StockListBean> stockListBeans, ClickResponseListener clickResponseListener
            , ClickResponseListener onItemClick) {
        this.recyclerView = recyclerView;
        this.stockListBeans = stockListBeans;
        this.clickResponseListener = clickResponseListener;
        this.onItemClick = onItemClick;
    }

    public void updateData(ArrayList<MStockListModel.DataBean.StockListBean> stockListBeans) {
        this.stockListBeans = stockListBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.stock_list_item, parent, false);
        return new ViewHolder(view, clickResponseListener, onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (stockListBeans.get(position).getStockExchange()) {
            case "SH":
                holder.image.setImageResource(R.mipmap.ic_sh);
                break;
            case "SZ":
                holder.image.setImageResource(R.mipmap.ic_sz);
                break;
            default:
                holder.image.setImageResource(R.mipmap.ic_sh);
                break;
        }
        holder.add.setImageResource(stockListBeans.get(position).isSelfSelect() ? R.mipmap.ic_delete_self_selection : R.mipmap.ic_add_self_selection);
        holder.stockName.setText(stockListBeans.get(position).getStockName());
        holder.stockNum.setText(stockListBeans.get(position).getStockSymbol());
    }

    @Override
    public int getItemCount() {
        return stockListBeans == null ? 0 : stockListBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image, add;
        private TextView stockName, stockNum;
        private ClickResponseListener clickResponseListener;
        private ClickResponseListener onItemClick;

        public ViewHolder(View itemView, ClickResponseListener clickResponseListener, ClickResponseListener onItemClick) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            add = itemView.findViewById(R.id.iv_add);
            stockName = itemView.findViewById(R.id.tv_stock_name);
            stockNum = itemView.findViewById(R.id.tv_stock_num);
            this.clickResponseListener = clickResponseListener;
            this.onItemClick = onItemClick;
            add.setOnClickListener(v -> onItemClick.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, ViewHolder.this)));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, StockListAdapter.ViewHolder.this));
        }
    }
}
