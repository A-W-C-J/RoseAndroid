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
import com.rose.android.model.MCoupons;
import com.rose.android.utils.Utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/9/13.
 */

@SuppressFBWarnings("URF_UNREAD_FIELD")
public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.ViewHolder> {
  private RecyclerView recyclerView;
  private MCoupons coupons;
  private ClickResponseListener clickResponseListener;
  private Context context;
  private Integer selectPosition;

  public CouponListAdapter(RecyclerView recyclerView, MCoupons coupons, ClickResponseListener clickResponseListener, Context context, @Nullable Integer selectPosition) {
    this.recyclerView = recyclerView;
    this.coupons = coupons;
    this.clickResponseListener = clickResponseListener;
    this.context = context;
    this.selectPosition = selectPosition;
  }

  public void updateData(MCoupons coupons) {
    if (coupons != null) {
      this.coupons = coupons;
    }
    notifyDataSetChanged();
  }

  public int getCouponId(int position) {
    if (coupons != null && coupons.getData() != null && coupons.getData().getUserCouponList() != null && coupons.getData().getUserCouponList()
        .get(position) != null) {
      return coupons.getData().getUserCouponList().get(position).getId();
    } else {
      return 0;
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.coupons_list_item, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.price.setText(Utils.divide1000(coupons.getData().getUserCouponList().get(position).getCashDiscount()).toString());
    holder.couponName.setText(coupons.getData().getUserCouponList().get(position).getName());
    holder.date.setText("有效期至：" + coupons.getData().getUserCouponList().get(position).getEndTime().split(" ")[0]);
    if (selectPosition != null && selectPosition == position) {
      holder.select.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public int getItemCount() {
    if (coupons != null && coupons.getData() != null && coupons.getData().getUserCouponList() != null) {
      return coupons.getData().getUserCouponList().size();
    } else {
      return 0;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ClickResponseListener clickResponseListener;
    private TextView price;
    private TextView couponName;
    private TextView date;
    private ImageView select;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      this.clickResponseListener = clickResponseListener;
      price = (TextView) itemView.findViewById(R.id.price);
      couponName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
      date = (TextView) itemView.findViewById(R.id.tv_date);
      select = (ImageView) itemView.findViewById(R.id.coupon_selected);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(getAdapterPosition());
    }
  }
}
