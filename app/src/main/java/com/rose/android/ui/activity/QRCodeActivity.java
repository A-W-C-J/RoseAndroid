package com.rose.android.ui.activity;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rose.android.R;
import com.rose.android.utils.QRCode;

@Route(path = "/ui/qrcodeActivity")
public class QRCodeActivity extends BaseActivity {
  private ImageView myView;
  @Autowired
  public String url;
  @Autowired
  public String title;
  @Autowired
  public String payChannel;
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ARouter.getInstance().inject(this);
    create(R.layout.activity_qrcode);
    setContentView(getRootView());
    if (title == null) {
      setTitle("二维码");
    } else {
      setTitle(title);
    }
    new Handler().postDelayed(() -> {
      initViews();
      initListener();
    }, 500);
  }

  @Override
  public void initViews() {
    super.initViews();
    myView = findViewById(R.id.qr_code);
    textView = findViewById(R.id.tv_pay_channel);
    if (payChannel != null) {
      textView.setVisibility(View.VISIBLE);
      textView.setText("请使用" + payChannel + "扫码充值");
    }
    if (url != null) {
      Bitmap bitmap = QRCode.createQRCode(url);
      myView.setImageBitmap(bitmap);
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        myView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
          @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
          @Override
          public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            v.removeOnLayoutChangeListener(this);
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            myView.setVisibility(View.VISIBLE);
            anim.start();
          }
        });
      } else {
        myView.setVisibility(View.VISIBLE);
      }
    }
  }
}
