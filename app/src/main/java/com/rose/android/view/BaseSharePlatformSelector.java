package com.rose.android.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by wenen on 2017/12/14.
 */

public abstract class BaseSharePlatformSelector {

    private FragmentActivity mContext;
    private  BaseSharePlatformSelector.OnShareSelectorDismissListener mDismissListener;
    private AdapterView.OnItemClickListener mItemClickListener;

    private static ShareTarget[] shareTargets = {
            new ShareTarget(SHARE_MEDIA.WEIXIN),
            new ShareTarget(SHARE_MEDIA.WEIXIN_CIRCLE),
            new ShareTarget(SHARE_MEDIA.QQ),
            new ShareTarget(SHARE_MEDIA.QZONE)
    };

    public BaseSharePlatformSelector(FragmentActivity context, BaseSharePlatformSelector.OnShareSelectorDismissListener dismissListener, AdapterView.OnItemClickListener itemClickListener) {
        mContext = context;
        mDismissListener = dismissListener;
        mItemClickListener = itemClickListener;
    }

    public abstract void show();

    public abstract void dismiss();

    public void release() {
        mContext = null;
        mDismissListener = null;
        mItemClickListener = null;
    }

    protected static GridView createShareGridView(final Context context, AdapterView.OnItemClickListener onItemClickListener) {
        GridView grid = new GridView(context);
        ListAdapter adapter = new ArrayAdapter< BaseSharePlatformSelector.ShareTarget>(context, 0, shareTargets) {
            // no need scroll
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(com.bilibili.socialize.share.utils.R.layout.bili_socialize_shareboard_item, parent, false);
                view.setBackground(null);
                ImageView image = (ImageView) view.findViewById(com.bilibili.socialize.share.utils.R.id.bili_socialize_shareboard_image);
                TextView platform = (TextView) view.findViewById(com.bilibili.socialize.share.utils.R.id.bili_socialize_shareboard_pltform_name);

                 BaseSharePlatformSelector.ShareTarget target = getItem(position);
                image.setImageResource(target.iconId);
                platform.setText(target.titleId);
                return view;
            }
        };
        grid.setNumColumns(-1);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setColumnWidth(context.getResources().getDimensionPixelSize(com.bilibili.socialize.share.utils.R.dimen.bili_socialize_shareboard_size));
        grid.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        grid.setSelector(com.bilibili.socialize.share.utils.R.drawable.bili_socialize_selector_item_background);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(onItemClickListener);
        return grid;
    }

    public FragmentActivity getContext() {
        return mContext;
    }

    public AdapterView.OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public  BaseSharePlatformSelector.OnShareSelectorDismissListener getDismissListener() {
        return mDismissListener;
    }

    public static class ShareTarget {
        public int titleId;
        public int iconId;
        public SHARE_MEDIA media;

        public ShareTarget(SHARE_MEDIA media) {
            this.media = media;
            switch (this.media) {
                case WEIXIN:
                    init(com.bilibili.socialize.share.utils.R.string.bili_socialize_text_weixin_key, com.bilibili.socialize.share.utils.R.drawable.bili_socialize_wechat);
                    break;
                case WEIXIN_CIRCLE:
                    init(com.bilibili.socialize.share.utils.R.string.bili_socialize_text_weixin_circle_key, com.bilibili.socialize.share.utils.R.drawable.bili_socialize_wxcircle);
                    break;
                case QQ:
                    init(com.bilibili.socialize.share.utils.R.string.bili_socialize_text_qq_key, com.bilibili.socialize.share.utils.R.drawable.bili_socialize_qq_on);
                    break;
                case QZONE:
                    init(com.bilibili.socialize.share.utils.R.string.bili_socialize_text_qq_zone_key, com.bilibili.socialize.share.utils.R.drawable.bili_socialize_qzone_on);
                    break;
                default:
                    break;
            }
        }

        private void init(int titleId, int iconId) {
            this.titleId = titleId;
            this.iconId = iconId;
        }
    }

    public interface OnShareSelectorDismissListener {
        void onDismiss();
    }

}
