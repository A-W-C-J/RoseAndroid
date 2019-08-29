package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MProductListModel;
import com.rose.android.utils.GlideApp;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

/**
 * Created by xiaohuabu on 17/9/8.
 */

public class TraderListAdapter extends RecyclerView.Adapter<TraderListAdapter.ViewHolder> {
  private ClickResponseListener clickResponseListener;
  private Context context;
  private List<MProductListModel.DataBean.ProductListBean> productListModel;

  public TraderListAdapter(ClickResponseListener clickResponseListener, Context context, MProductListModel productListModel) {
    this.clickResponseListener = clickResponseListener;
    this.context = context;
    if (productListModel != null) {
      this.productListModel = productListModel.getData().getProductList();
    }
  }

  public void updateData(MProductListModel productListModel) {
    if (productListModel != null) {
      this.productListModel = productListModel.getData().getProductList();
    }
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.trader_list_item, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    RequestOptions options = new RequestOptions();
    options.centerCrop();
    GlideApp.with(context).load(productListModel.get(position).getBillboardUri()).apply(fitCenterTransform()).
        into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return productListModel == null ? 0 : productListModel.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView imageView;
    ClickResponseListener clickResponseListener;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.image);
//      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      this.clickResponseListener = clickResponseListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(getAdapterPosition());
    }
  }
}
