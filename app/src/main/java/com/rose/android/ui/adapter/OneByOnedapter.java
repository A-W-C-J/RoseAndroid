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
import com.rose.android.model.MStockMarketInfoModel;
import com.rose.android.utils.Utils;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.util.ArrayList;

/**
 * Created by wenen on 2018/1/6.
 */

public class OneByOnedapter extends RecyclerView.Adapter<OneByOnedapter.ViewHolder> {
    private Context context;
    private ClickResponseListener clickResponseListener;
    private ArrayList<MStockMarketInfoModel.DataBean.StockTransactionList> dataBeans;
    private RecyclerView recyclerView;

    public OneByOnedapter(Context context, ClickResponseListener clickResponseListener, ArrayList<MStockMarketInfoModel.DataBean.StockTransactionList> dataBeans, RecyclerView recyclerView) {
        this.context = context;
        this.clickResponseListener = clickResponseListener;
        this.dataBeans = dataBeans;
        this.recyclerView = recyclerView;
    }

    public void updateData(ArrayList<MStockMarketInfoModel.DataBean.StockTransactionList> dataBeans) {
        this.dataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.exchange_one_by_one_item, parent, false);
        return new OneByOnedapter.ViewHolder(view, clickResponseListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setText(dataBeans.get(position).getTime().substring(0,5));
        holder.price.setText(Utils.formatWithScale(Utils.divide1000(dataBeans.get(position).getPrice()), 2));
        holder.action.setText(dataBeans.get(position).getAction());
        holder.volumn.setText(String.valueOf(dataBeans.get(position).getVolume()));
        if ("S".equals(dataBeans.get(position).getAction())){
            holder.action.setTextColor(ContextCompat.getColor(context,R.color.green));
            holder.price.setTextColor(ContextCompat.getColor(context,R.color.green));
        }else {
            holder.action.setTextColor(ContextCompat.getColor(context,R.color.red));
            holder.price.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return dataBeans == null ? 0 : dataBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView time, price, volumn, action;
        ClickResponseListener clickResponseListener;

        public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
            volumn = itemView.findViewById(R.id.volumn);
            action = itemView.findViewById(R.id.action);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, OneByOnedapter.ViewHolder.this));
        }
    }
}
