package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MLimitedBuyListModel;


/**
 * Created by xiaohuabu on 17/9/19.
 */

public class LimitedBuyAdapter extends RecyclerView.Adapter<LimitedBuyAdapter.ViewHolder> {
  private MLimitedBuyListModel.DataBean dataBean;
  private Context context;
  private ClickResponseListener clickResponseListener;

  public LimitedBuyAdapter(MLimitedBuyListModel.DataBean dataBean, Context context, ClickResponseListener clickResponseListener) {
    this.dataBean = dataBean;
    this.context = context;
    this.clickResponseListener = clickResponseListener;
  }

  public void updateData(MLimitedBuyListModel.DataBean dataBean) {
    if (dataBean != null) {
      this.dataBean = dataBean;
    }
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.limited_item, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.title.setText(dataBean.getLimitedStockList().get(position).getName());
    if (dataBean.getLimitedStockList().get(position).getName().length()>4){
      holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
    }
    holder.num.setText(dataBean.getLimitedStockList().get(position).getCode());
  }

  @Override
  public int getItemCount() {
    if (dataBean == null || dataBean.getLimitedStockList() == null) {
      return 0;
    }
    return dataBean.getLimitedStockList().size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ClickResponseListener clickResponseListener;
    private TextView title;
    private TextView num;

    public ViewHolder(View view, ClickResponseListener listener) {
      super(view);
      title = (TextView) view.findViewById(R.id.tv_title);
      num = (TextView) view.findViewById(R.id.stock_num);
      clickResponseListener = listener;
      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(getAdapterPosition());
    }
  }
}
