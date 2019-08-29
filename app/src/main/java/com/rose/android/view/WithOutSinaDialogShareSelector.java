package com.rose.android.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.reflect.Field;

/**
 * Created by wenen on 2017/11/7.
 */

public class WithOutSinaDialogShareSelector extends BaseSharePlatformSelector {
    private static final String TAG = "WithOutSinaDialogShareS";
    private static ShareTarget[] shareTargets = {
            new ShareTarget(SHARE_MEDIA.WEIXIN),
            new ShareTarget(SHARE_MEDIA.WEIXIN_CIRCLE),
            new ShareTarget(SHARE_MEDIA.QQ),
            new ShareTarget(SHARE_MEDIA.QZONE)
    };
    private final String mShareDialogTag;
    private WithOutSinaDialogShareSelector.ShareDialogFragment mShareDialog;

    public WithOutSinaDialogShareSelector(FragmentActivity context, OnShareSelectorDismissListener dismissListener, AdapterView.OnItemClickListener itemClickListener) {
        super(context, dismissListener, itemClickListener);
        mShareDialogTag = "share.dialog" + context.getComponentName().getShortClassName();
    }

    @Override
    public void show() {
        FragmentActivity context = getContext();
        if (mShareDialog == null && (mShareDialog = (WithOutSinaDialogShareSelector.ShareDialogFragment) context.getSupportFragmentManager().findFragmentByTag(mShareDialogTag)) == null) {
            mShareDialog = new WithOutSinaDialogShareSelector.ShareDialogFragment();
        }
        mShareDialog.setOnDismissListener(dialog -> {
            if (getDismissListener() != null) {
                getDismissListener().onDismiss();
            }
        });
        mShareDialog.setOnItemClickListener(getItemClickListener());
        mShareDialog.show(context.getSupportFragmentManager(), mShareDialogTag);
    }

    @Override
    public void dismiss() {
        if (mShareDialog != null) {
            mShareDialog.dismissAllowingStateLoss();
            if (mShareDialog.mDismiss != null) {
                mShareDialog.mDismiss = null;
            }
        }
    }

    @Override
    public void release() {
        if (mShareDialog != null && mShareDialog.getActivity() == null) {
            return;
        }
        dismiss();
        mShareDialog = null;
        super.release();
    }

    public static class ShareDialogFragment extends DialogFragment {
        private AdapterView.OnItemClickListener mShareItemClick;
        private DialogInterface.OnDismissListener mDismiss;

        @Override
        public void show(FragmentManager manager, String tag) {
            Field field = null;
            try {
                field = DialogFragment.class.getDeclaredField("mShownByMe");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (field != null) {
                field.setAccessible(true);
                Boolean show = null;
                try {
                    show = field.getBoolean(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (show != null && show) {
                    return;
                }
            }

            super.show(manager, tag);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final TypedValue outValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(android.R.attr.alertDialogTheme, outValue, true);
            int theme = outValue.resourceId;
            setStyle(STYLE_NORMAL, theme);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            GridView grid = createShareGridView(inflater.getContext(), mShareItemClick);
            return grid;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setOnDismissListener(mDismiss);
            return dialog;
        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener click) {
            mShareItemClick = click;
        }

        public void setOnDismissListener(DialogInterface.OnDismissListener dismiss) {
            mDismiss = dismiss;
        }
    }

    protected static GridView createShareGridView(final Context context, AdapterView.OnItemClickListener onItemClickListener) {
        Log.e(TAG, "createShareGridView: " + shareTargets.length);
        GridView grid = new GridView(context);
        ListAdapter adapter = new ArrayAdapter<ShareTarget>(context, 0, shareTargets) {
            // no need scroll
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(com.bilibili.socialize.share.utils.R.layout.bili_socialize_shareboard_item, parent, false);
                view.setBackground(null);
                ImageView image = (ImageView) view.findViewById(com.bilibili.socialize.share.utils.R.id.bili_socialize_shareboard_image);
                TextView platform = (TextView) view.findViewById(com.bilibili.socialize.share.utils.R.id.bili_socialize_shareboard_pltform_name);
                ShareTarget target = getItem(position);
                image.setImageResource(target.iconId);
                platform.setText(target.titleId);
                return view;
            }
        };
        grid.setNumColumns(2);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setColumnWidth(context.getResources().getDimensionPixelSize(com.bilibili.socialize.share.utils.R.dimen.bili_socialize_shareboard_size));
        grid.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        grid.setSelector(com.bilibili.socialize.share.utils.R.drawable.bili_socialize_selector_item_background);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(onItemClickListener);
        return grid;
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
}
