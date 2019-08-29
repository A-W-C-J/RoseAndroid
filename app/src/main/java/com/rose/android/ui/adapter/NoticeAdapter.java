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
import com.rose.android.model.MNoticeModel;
import com.rose.android.utils.GlideApp;
import com.rose.android.view.recyclerview.RecyclerViewUtils;

import java.util.List;

/**
 * Created by wenen on 2017/12/6.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private List<MNoticeModel.DataBean.NewsListBean> noticeModel;
    private ClickResponseListener clickResponseListener;
    private Context context;

    public NoticeAdapter(RecyclerView recyclerView, ClickResponseListener clickResponseListener, MNoticeModel mNoticeModel) {
        this.recyclerView = recyclerView;
        this.clickResponseListener = clickResponseListener;
        if (mNoticeModel != null) {
            this.noticeModel = mNoticeModel.getData().getNewsList();
        }
    }

    public void updateData(MNoticeModel mNoticeModel) {
        if (mNoticeModel != null && mNoticeModel.getData() != null) {
            this.noticeModel = mNoticeModel.getData().getNewsList();
        }
        notifyDataSetChanged();
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false);
        return new NoticeAdapter.ViewHolder(view, clickResponseListener);
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
        GlideApp.with(context).load(noticeModel.get(position).getImageUrl()).
                into(holder.imageView);
        holder.title.setText(noticeModel.get(position).getTitle());
        holder.time.setText(noticeModel.get(position).getTimeText());
    }

    public String getUrl(int position) {
        return noticeModel.get(position).getLink();
    }

    @Override
    public int getItemCount() {
        return noticeModel == null ? 0 : noticeModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView time;
        private ImageView imageView;
        ClickResponseListener clickResponseListener;

        public ViewHolder(View itemView, ClickResponseListener clickResponseListener) {
            super(itemView);
            this.clickResponseListener = clickResponseListener;
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            time = itemView.findViewById(R.id.time);
        }

        @Override
        public void onClick(View v) {
            clickResponseListener.onWholeClick(RecyclerViewUtils.getAdapterPosition(recyclerView, NoticeAdapter.ViewHolder.this));
        }
    }
}
