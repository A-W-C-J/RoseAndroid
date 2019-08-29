package com.rose.android.view;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.rose.android.R;

/**
 * Created by xiaohuabu on 17/9/2.
 */
@SuppressWarnings("deprecation")
public class LoadProgressDialog extends android.app.ProgressDialog {
  public LoadProgressDialog(Context context) {
    super(context, R.style.LoadProgressDialog);
  }

  public LoadProgressDialog(Context context, int theme) {
    super(context, theme);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init(getContext());
  }

  private void init(Context context) {
    setCancelable(false);
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.load_dialog);
    WindowManager.LayoutParams params = getWindow().getAttributes();
    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    getWindow().setAttributes(params);
  }

  @Override
  public void show() {
    super.show();
  }
}
