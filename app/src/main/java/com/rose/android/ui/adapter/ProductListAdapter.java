package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MProductListModel;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.util.List;

/**
 * Created by xiaohuabu on 17/9/7.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
  private RecyclerView recyclerView;
  private List<MProductListModel.DataBean.ProductListBean> productListModel;
  private ClickResponseListener clickResponseListener;
  private Context context;

  public ProductListAdapter(RecyclerView recyclerView, ClickResponseListener clickResponseListener, MProductListModel productListModel) {
    this.recyclerView = recyclerView;
    this.clickResponseListener = clickResponseListener;
    if (productListModel != null) {
      this.productListModel = productListModel.getData().getProductList();
    }
  }

  public void updateData(MProductListModel productListModel) {
    if (productListModel != null&&productListModel.getData()!=null) {
      this.productListModel = productListModel.getData().getProductList();
    }
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.productName.setText(productListModel.get(position).getName());
    holder.productRate.setText(String.valueOf(productListModel.get(position).getMaxMutiple()));
    holder.involvedUsersCount.setText(String.valueOf(productListModel.get(position).getInvolvedUsersCount()));
    if (!productListModel.get(position).getMarginHint().isEmpty()) {
      holder.marginHint.setText(productListModel.get(position).getMarginHint());
    } else {
      holder.marginHint.setVisibility(View.INVISIBLE);
    }
  }

  @Override
  public int getItemCount() {
    return productListModel == null ? 0 : productListModel.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView productName;
    private TextView productRate;
    private TextView marginHint;
    private TextView involvedUsersCount;
    ClickResponseListener clickResponseListener;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      productName = itemView.findViewById(R.id.tv_product_name);
      productRate = itemView.findViewById(R.id.max_mutiple);
      marginHint = itemView.findViewById(R.id.margin_hint);
      involvedUsersCount = itemView.findViewById(R.id.involved_users_count);
      this.clickResponseListener = clickResponseListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, ViewHolder.this));
    }
  }
}
