package com.rose.android.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rose.android.R;

/**
 * Created by xiaohuabu on 17/9/2.
 */

public class MineItemView extends LinearLayout {
    private ColorStateList mItemTextColor;
    private Drawable mLeftIcon;
    private Drawable mRightIcon;
    private CharSequence mTagIcon;
    private CharSequence mTitle;
    private TextView title;
    private ImageView leftIcon;
    private ImageView rightIcon;
    private TextView tagIcon;

    public MineItemView(Context context) {
        super(context);
        init(context);
    }

    public MineItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
        init(context);
    }

    public MineItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributeSet(context, attrs);
        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MineItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributeSet(context, attrs);
        init(context);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MineItemView);
        mItemTextColor = typedArray.getColorStateList(R.styleable.MineItemView_item_text_color);
        mLeftIcon = typedArray.getDrawable(R.styleable.MineItemView_icon_left);
        mRightIcon = typedArray.getDrawable(R.styleable.MineItemView_icon_right);
        mTagIcon = typedArray.getText(R.styleable.MineItemView_tag_icon);
        mTitle = typedArray.getText(R.styleable.MineItemView_item_title);
        typedArray.recycle();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mine_item_layout, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        title = (TextView) findViewById(R.id.tv_title);
        leftIcon = (ImageView) findViewById(R.id.iv_left_icon);
        rightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        tagIcon = (TextView) findViewById(R.id.tv_tag_icon);
        if (mTagIcon != null)
            tagIcon.setText(mTagIcon);
        if (mTitle != null)
            title.setText(mTitle);
        if (mLeftIcon != null)
            leftIcon.setImageDrawable(mLeftIcon);
        if (mRightIcon != null)
            rightIcon.setImageDrawable(mRightIcon);
        if (mItemTextColor != null)
            title.setTextColor(mItemTextColor);
    }

    public void setRightIconShow(int visibility) {
        rightIcon.setVisibility(visibility);
    }

    public void setTagIconShow(int visibility) {
        tagIcon.setVisibility(visibility);
    }

    public void setTagTxt(CharSequence sequence) {
        tagIcon.setText(sequence);
    }
}
