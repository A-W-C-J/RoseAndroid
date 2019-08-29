package com.rose.android.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rose.android.R;

/**
 * Created by xiaohuabu on 17/9/11.
 */

public class ContractDetailItemView extends LinearLayout {
  private CharSequence title;
  private CharSequence subTitle;
  private Integer tagIsVisiable;
  private Integer rightIconVisiable;
  private ColorStateList rightColor;
  private CharSequence rightText;
  private CharSequence iconTxt;

  public ContractDetailItemView(Context context) {
    super(context);
    init(context);
  }

  public ContractDetailItemView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    parseAttributeSet(context, attrs);
    init(context);
  }

  public ContractDetailItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    parseAttributeSet(context, attrs);
    init(context);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public ContractDetailItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    parseAttributeSet(context, attrs);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.contract_detail_item_layout, this, true);
  }

  private void parseAttributeSet(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContractDetailItemView);
    title = typedArray.getText(R.styleable.ContractDetailItemView_ct_item_title);
    subTitle = typedArray.getText(R.styleable.ContractDetailItemView_ct_sub_title);
    tagIsVisiable = typedArray.getInteger(R.styleable.ContractDetailItemView_ct_icon_visiable, View.GONE);
    rightColor = typedArray.getColorStateList(R.styleable.ContractDetailItemView_ct_right_color);
    rightText = typedArray.getText(R.styleable.ContractDetailItemView_ct_right_title);
    rightIconVisiable = typedArray.getInteger(R.styleable.ContractDetailItemView_ct_right_icon_visiable, View.GONE);
    iconTxt = typedArray.getText(R.styleable.ContractDetailItemView_ct_tag_icon);
    typedArray.recycle();
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (title != null) {
      ((TextView) findViewById(R.id.title)).setText(title);
    }
    if (subTitle != null) {
      ((TextView) findViewById(R.id.tv_sub_title)).setText(subTitle);
    }
    if (tagIsVisiable != null) {
      findViewById(R.id.icon).setVisibility(tagIsVisiable);
    }
    if (rightColor != null) {
      ((TextView) findViewById(R.id.right_title)).setTextColor(rightColor);
    }
    if (rightText != null) {
      ((TextView) findViewById(R.id.right_title)).setText(rightText);
    }
    if (rightIconVisiable != null) {
      findViewById(R.id.iv_right_icon).setVisibility(rightIconVisiable);
    }
    if (iconTxt != null) {
      ((TextView) findViewById(R.id.icon)).setText(iconTxt);
    }
  }

  public void setTitle(String text) {
    ((TextView) findViewById(R.id.title)).setText(text);
  }

  public void setRightText(String text) {
    ((TextView) findViewById(R.id.right_title)).setText(text);
  }

  public void setIconVisiable(boolean visiable) {
    findViewById(R.id.icon).setVisibility(visiable ? View.VISIBLE : View.GONE);
  }

  public void setRightIconVisiable(boolean visiable) {
    findViewById(R.id.iv_right_icon).setVisibility(visiable ? View.VISIBLE : View.GONE);
  }

  public void setRightIconInVisiable() {
    findViewById(R.id.iv_right_icon).setVisibility(View.INVISIBLE);
  }

  public void setSubTitle(String txt) {
    findViewById(R.id.tv_sub_title).setVisibility(VISIBLE);
    ((TextView) findViewById(R.id.tv_sub_title)).setText(txt);

  }

  public void setTagText(String txt) {
    ((TextView) findViewById(R.id.icon)).setText(txt);
  }

  public void setRihtTextColor(int rightColor) {
    ((TextView) findViewById(R.id.right_title)).setTextColor(rightColor);

  }

}
