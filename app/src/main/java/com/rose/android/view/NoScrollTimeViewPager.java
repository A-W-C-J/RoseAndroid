package com.rose.android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wenen on 2017/11/30.
 */

public class NoScrollTimeViewPager extends ViewPager {
  private boolean noScroll = false;
  private ViewPageHelper helper;

  public NoScrollTimeViewPager(Context context) {
    this(context, null);
  }

  public NoScrollTimeViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    helper = new ViewPageHelper(this);
  }

  @Override
  public void setCurrentItem(int item) {
    setCurrentItem(item, true);
  }

  @Override
  public void setCurrentItem(int item, boolean smoothScroll) {
    MScroller scroller = helper.getScroller();
    if (Math.abs(getCurrentItem() - item) > 1) {
      scroller.setNoDuration(true);
      super.setCurrentItem(item, smoothScroll);
      scroller.setNoDuration(true);
    } else {
      scroller.setNoDuration(true);
      super.setCurrentItem(item, smoothScroll);
    }
  }

  public void setNoScroll(boolean noScroll) {
    this.noScroll = noScroll;
  }

  @Override
  public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
    if (noScroll) {
      return false;
    } else {
      return super.onTouchEvent(arg0);
    }
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent arg0) {
    if (noScroll) {
      return false;
    } else {
      return super.onInterceptTouchEvent(arg0);
    }
  }
}
