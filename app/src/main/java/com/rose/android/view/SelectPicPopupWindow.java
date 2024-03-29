package com.rose.android.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rose.android.R;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("UUF_UNUSED_FIELD")
@SuppressWarnings("deprecation")
public class SelectPicPopupWindow extends PopupWindow implements
    OnClickListener {

  private Context mContext;
  private Animation mIn_PopupwindAnimation;
  private Animation mOut_PopupwindAnimation;
  private RelativeLayout mMain;
  private ViewClickListener mCameraSelectListener;
  private ViewClickListener mPhotoSelectListener;
  private RelativeLayout mRlTakephoto, mRlAlbum, mRlDission;
  private TextView[] arrTextView = new TextView[3];

  public SelectPicPopupWindow(Context context) {
    super(context);
    mContext = context;
    this.setWidth(LayoutParams.MATCH_PARENT);
    this.setHeight(LayoutParams.MATCH_PARENT);
    this.setOutsideTouchable(false);
    this.setBackgroundDrawable(new BitmapDrawable());
    this.setFocusable(true);


    initView();
    setListener();
  }

  private void initView() {
    findView();
    // 添加菜单视图
    this.setContentView(mMain);
  }

  private void setListener() {
    mRlDission.setOnClickListener(this);
    mRlTakephoto.setOnClickListener(this);
    mRlAlbum.setOnClickListener(this);
  }


  private void findView() {
    mMain = (RelativeLayout) LayoutInflater.from(mContext).inflate(
        R.layout.popupwindow_selectpic, null);

    mRlTakephoto = (RelativeLayout) mMain.findViewById(R.id.rl_takephoto);
    mRlAlbum = (RelativeLayout) mMain.findViewById(R.id.rl_album);
    mRlDission = (RelativeLayout) mMain.findViewById(R.id.rl_dission);
    arrTextView[0] = (TextView) mMain.findViewById(R.id.tv_1);
    arrTextView[1] = (TextView) mMain.findViewById(R.id.tv_2);
    arrTextView[2] = (TextView) mMain.findViewById(R.id.tv_3);

  }

  public void setCameraSelectListener(ViewClickListener listener) {
    mCameraSelectListener = listener;
  }

  public void setPhotoSelectListener(ViewClickListener listener) {
    mPhotoSelectListener = listener;
  }

  public void onAnimationStart(Animation animation) {

  }

  public void onAnimationEnd(Animation animation) {
    mHandler.sendEmptyMessage(0);
  }

  public void onAnimationRepeat(Animation animation) {
  }

  /**
   * dismiss 时 必须用到Handler不然 会报错。原因不明
   **/
  Handler mHandler = new Handler() {
    public void handleMessage(android.os.Message msg) {
      dismiss();
    }
  };

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rl_dission:
        //   changeColor(2);
        dismiss();
        break;
      case R.id.rl_takephoto:
        if (mCameraSelectListener != null) {
          //   changeColor(0);
          mCameraSelectListener.onClick();
          dismiss();
        }
        break;
      case R.id.rl_album:
        if (mPhotoSelectListener != null) {
          //  changeColor(1);
          mPhotoSelectListener.onClick();
          dismiss();
        }
        break;

      default:
        break;
    }
  }

  public interface ViewClickListener {
    public void onClick();
  }

  public void changeColor(int number) {
    for (int i = 0; i <= 2; i++) {
      arrTextView[i].setTextColor(ContextCompat.getColor(mContext, R.color.half_transparent_black_pop));
    }
    arrTextView[number].setTextColor(ContextCompat.getColor(mContext, R.color.promoter_orange));
  }
}
