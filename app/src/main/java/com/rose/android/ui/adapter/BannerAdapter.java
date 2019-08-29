package com.rose.android.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.rose.android.model.MBannersModel;
import com.rose.android.utils.GlideApp;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public class BannerAdapter extends StaticPagerAdapter {
    private MBannersModel.DataBean listBean;
    private Context context;

    public BannerAdapter(MBannersModel.DataBean listBean, Context context) {
        this.listBean = listBean;
        this.context = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        String imageUrl = listBean.getBannerList().get(position).getBannerUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            GlideApp.with(context).load(imageUrl).apply(centerCropTransform()).into(view);
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        if (listBean != null && listBean.getBannerList() != null) {
            return listBean.getBannerList().size();
        } else
            return 0;
    }
}
