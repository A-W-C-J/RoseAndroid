package com.rose.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.model.MAllCouponsModel;
import com.rose.android.utils.Utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/10/9.
 */

@SuppressFBWarnings({"URF_UNREAD_FIELD", "URF_UNREAD_FIELD"})
public class AllCouponListAdapter extends RecyclerView.Adapter<AllCouponListAdapter.ViewHolder> {
  private RecyclerView recyclerView;
  private MAllCouponsModel coupons;
  private ClickResponseListener clickResponseListener;
  private Context context;
  private Integer selectPosition;

  public AllCouponListAdapter(RecyclerView recyclerView, MAllCouponsModel coupons, ClickResponseListener clickResponseListener, Context context, @Nullable Integer selectPosition) {
    this.recyclerView = recyclerView;
    this.coupons = coupons;
    this.clickResponseListener = clickResponseListener;
    this.context = context;
    this.selectPosition = selectPosition;
  }

  public void updateData(MAllCouponsModel coupons) {
    if (coupons != null) {
      this.coupons = coupons;
    }
    notifyDataSetChanged();
  }

  public int getCouponId(int position) {
    if (coupons != null) {
      return coupons.getData().getCouponInMallList().get(position).getId();
    } else {
      return 0;
    }
  }

  public long getCouponScore(int position) {
    if (coupons != null) {
      return coupons.getData().getCouponInMallList().get(position).getNeedScore();
    } else {
      return 0;
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.all_coupons_list_item, parent, false);
    return new ViewHolder(view, clickResponseListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.price.setText(Utils.formatWithScale(Utils.divide1000(coupons.getData().getCouponInMallList().get(position).getCashDiscount()), 2));
    holder.couponName.setText(coupons.getData().getCouponInMallList().get(position).getName());
    holder.score.setText(String.format("%s积分", Utils.formatWithScale(Utils.divide1000(coupons.getData().getCouponInMallList()
        .get(position).getNeedScore()), 0)));
  }

  @Override
  public int getItemCount() {
    return coupons == null ? 0 : coupons.getData().getCouponInMallList().size();
  }

  @SuppressFBWarnings("URF_UNREAD_FIELD")
  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ClickResponseListener clickResponseListener;
    private TextView price;
    private TextView couponName;
    private TextView score;
    private TextView select;

    public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
      super(itemView);
      this.clickResponseListener = clickResponseListener;
      price = (TextView) itemView.findViewById(R.id.price);
      couponName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
      score = (TextView) itemView.findViewById(R.id.tv_score);
      select = (TextView) itemView.findViewById(R.id.coupon_selected);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      clickResponseListener.onWholeClick(getAdapterPosition());
    }
  }
}
