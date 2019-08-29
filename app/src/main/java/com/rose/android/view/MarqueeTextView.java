package com.rose.android.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.rose.android.R;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


/**
 * Created by wenen on 2017/11/1.
 */

@SuppressFBWarnings({"URF_UNREAD_FIELD", "URF_UNREAD_FIELD"})
public class MarqueeTextView extends LinearLayout {

  private Context mContext;
  private ViewFlipper viewFlipper;
  private View marqueeTextView;
  private String[] textArrays;
  private MarqueeTextViewClickListener marqueeTextViewClickListener;

  public MarqueeTextView(Context context) {
    super(context);
    mContext = context;
    initBasicView();
  }


  public MarqueeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    initBasicView();
  }

  public void setTextArraysAndClickListener(String[] textArrays, MarqueeTextViewClickListener marqueeTextViewClickListener) {
    this.textArrays = textArrays.clone();
    this.marqueeTextViewClickListener = marqueeTextViewClickListener;
    initMarqueeTextView(textArrays, marqueeTextViewClickListener);
  }

  public void initBasicView() {
    marqueeTextView = LayoutInflater.from(mContext).inflate(R.layout.marquee_textview_layout, null);
    LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    addView(marqueeTextView, layoutParams);
    viewFlipper = (ViewFlipper) marqueeTextView.findViewById(R.id.viewFlipper);
    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
    viewFlipper.startFlipping();
  }

  public void initMarqueeTextView(String[] textArrays, MarqueeTextViewClickListener marqueeTextViewClickListener) {
    if (textArrays == null) {
      return;
    }

    int i = 0;
    viewFlipper.removeAllViews();
    while (i < textArrays.length) {
      TextView textView = new TextView(mContext);
      textView.setText(textArrays[i]);
      textView.setTextColor(ContextCompat.getColor(getContext(),R.color.title_color_2));
      textView.setOnClickListener(marqueeTextViewClickListener);
      LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
      viewFlipper.addView(textView, lp);
      i++;
    }
  }

  public void releaseResources() {
    if (marqueeTextView != null) {
      if (viewFlipper != null) {
        viewFlipper.stopFlipping();
        viewFlipper.removeAllViews();
        viewFlipper = null;
      }
      marqueeTextView = null;
    }
  }

}
