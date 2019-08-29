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
import com.rose.android.model.MOrderPositionsModel;
import com.rose.android.utils.Utils;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by xiaohuabu on 17/9/22.
 */

public class OrderPositionAdapter extends RecyclerView.Adapter<OrderPositionAdapter.ViewHolder> {
  private RecyclerView recyclerView;
  private ArrayList<MOrderPositionsModel.DataBean.ProductOrderPositionListBean> productOrderPositionListBeans;
  private ClickResponseListener clickResponseListener;
  private Context context;
  private static final String TAG = "OrderPositionAdapter";

  public OrderPositionAdapter(RecyclerView recyclerView, ArrayList<MOrderPositionsModel.DataBean.ProductOrderPositionListBean> productOrderPositionListBeans, ClickResponseListener clickResponseListener) {
    this.recyclerView = recyclerView;
    this.productOrderPositionListBeans = productOrderPositionListBeans;
    this.clickResponseListener = clickResponseListener;
  }

  public void updateData(ArrayList<MOrderPositionsModel.DataBean.ProductOrderPositionListBean> productOrderPositionListBeans) {
    this.productOrderPositionListBeans = productOrderPositionListBeans;
    notifyDataSetChanged();
  }

  public String getSymbol(int position) {
    return productOrderPositionListBeans.get(position).getSymbol();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.holding_item_layout, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    MOrderPositionsModel.DataBean.ProductOrderPositionListBean orderPositionListBean = productOrderPositionListBeans.get(position);
    holder.title.setText(orderPositionListBean.getName());
    holder.stockNum.setText(orderPositionListBean.getSymbol());
    holder.type.setText(orderPositionListBean.getAvailablePosition() + "");
    holder.status.setText(orderPositionListBean.getTotalPosition() + "");
    BigDecimal bigDecimal = new BigDecimal(orderPositionListBean.getCurrentPrice());
    BigDecimal curTotalPrice = Utils.divide1000(orderPositionListBean.getMarketValue());
    holder.price.setText(Utils.formatWithScale(curTotalPrice, 2));
    double totalPrice = orderPositionListBean.getTotalPosition() * orderPositionListBean.getCurrentPrice();
    double totalAveragePrice = orderPositionListBean.getTotalPosition() * orderPositionListBean.getAveragePrice();
    double profitAndLoss = (totalPrice - totalAveragePrice) / totalAveragePrice;
    if (profitAndLoss >= 0) {
      holder.count.setTextColor(ContextCompat.getColor(context,R.color.red));
      holder.count.setText("+" + Utils.formatWithScale(orderPositionListBean.getProfit() / 1000, 2)
          + "(" + "+" + Utils.formatWithScale(orderPositionListBean.getProfit() / totalAveragePrice * 100, 2) + "%" + ")");
    } else {
      holder.count.setTextColor(ContextCompat.getColor(context,R.color.green));
      holder.count.setText(Utils.formatWithScale(orderPositionListBean.getProfit() / 1000, 2)
          + "(" + Utils.formatWithScale(orderPositionListBean.getProfit() / totalAveragePrice * 100, 2) + "%" + ")");
    }
    holder.date.setText(Utils.formatWithScale(Utils.divide1000(orderPositionListBean.getAveragePrice()), 2));
    holder.time.setText(Utils.formatWithScale(bigDecimal.divide(new BigDecimal(1000)), 2));
    holder.imageView.setVisibility(View.GONE);
  }

  @Override
  public int getItemCount() {
    return productOrderPositionListBeans == null ? 0 : productOrderPositionListBeans.size();
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
