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
import com.rose.android.model.MSelfSelectListModel;
import com.rose.android.utils.Utils;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by wenen on 2018/1/4.
 */

public class SelfSelectAdapter extends RecyclerView.Adapter<SelfSelectAdapter.ViewHolder> {
    private Context context;
    private RecyclerView recyclerView;
    private ClickResponseListener clickResponseListener;
    private ArrayList<MSelfSelectListModel.DataBean.StockListBean> stockListBeans;

    public SelfSelectAdapter(RecyclerView recyclerView, ArrayList<MSelfSelectListModel.DataBean.StockListBean> stockListBeans, ClickResponseListener clickResponseListener) {
        this.recyclerView = recyclerView;
        this.stockListBeans = stockListBeans;
        this.clickResponseListener = clickResponseListener;
    }

    public void updateData(ArrayList<MSelfSelectListModel.DataBean.StockListBean> stockListBeans) {
        this.stockListBeans = stockListBeans;
        notifyDataSetChanged();
    }

    @Override
    public SelfSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.self_select_list, parent, false);
        return new SelfSelectAdapter.ViewHolder(view, clickResponseListener);
    }

    @Override
    public void onBindViewHolder(SelfSelectAdapter.ViewHolder holder, int position) {
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
        if (stockListBeans.get(position).getChangePercent() >= 0) {
            holder.priceChange.setText(String.format("+%s%%", Utils.formatWithScale(new BigDecimal(stockListBeans.get(position).getChangePercent()).divide(new BigDecimal(100)), 2)));
            holder.priceChange.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.priceChange.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.priceChange.setText(String.format("%s%%", Utils.formatWithScale(new BigDecimal(stockListBeans.get(position).getChangePercent()).divide(new BigDecimal(100)), 2)));
        }
        if (stockListBeans.get(position).getStockStatus() == 3) {
            holder.priceChange.setTextColor(ContextCompat.getColor(context, R.color.title_color_3));
            holder.priceChange.setText(stockListBeans.get(position).getStockStatusText());
        }
        holder.stockName.setText(stockListBeans.get(position).getStockName());
        holder.stockNum.setText(stockListBeans.get(position).getStockSymbol());
        holder.price.setText(Utils.formatWithScale(Utils.divide1000(stockListBeans.get(position).getCurrentPrice()), 2));
    }

    @Override
    public int getItemCount() {
        return stockListBeans == null ? 0 : stockListBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView stockName, stockNum;
        private ClickResponseListener clickResponseListener;
        private TextView price, priceChange;

        public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            stockName = itemView.findViewById(R.id.tv_stock_name);
            stockNum = itemView.findViewById(R.id.tv_stock_num);
            price = itemView.findViewById(R.id.tv_price);
            priceChange = itemView.findViewById(R.id.tv_price_change);
            this.clickResponseListener = clickResponseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, SelfSelectAdapter.ViewHolder.this));
        }
    }
}
