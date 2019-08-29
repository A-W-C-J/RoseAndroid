package com.rose.android.view;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.DialogClick;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Created by xiaohuabu on 17/9/12.
 */

public class RoseDialog extends DialogFragment {
  private Button positiveBtn;
  private Button negativeBtn;
  private TextView tvTitle;
  private TextView tvContent;
  private TextView tvSubContent;
  private View divider;
  private static DialogClick mDialogClick;
  private static volatile RoseDialog roseDialog = null;
  private boolean canFinish;

  @SuppressFBWarnings("DC_PARTIALLY_CONSTRUCTED")
  public static RoseDialog newInstance(DialogClick dialogClick, String pos, String neg, String title, String content, String subContent) {
    if (roseDialog == null) {
      synchronized (RoseDialog.class) {
        if (roseDialog == null) {
          roseDialog = new RoseDialog();
        } else {
          roseDialog.dismiss();
          roseDialog = null;
          mDialogClick = null;
          roseDialog = new RoseDialog();
        }
      }
    } else {
      roseDialog.dismiss();
      roseDialog = null;
      mDialogClick = null;
      roseDialog = new RoseDialog();
    }
    Bundle args = new Bundle();
    args.putString("pos", pos);
    args.putString("neg", neg);
    args.putString("title", title);
    args.putString("content", content);
    args.putString("subContent", subContent);
    mDialogClick = dialogClick;
    roseDialog.setArguments(args);
    return roseDialog;
  }

  @SuppressWarnings("deprecation")
  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    String pos = getArguments().getString("pos");
    String neg = getArguments().getString("neg");
    String title = getArguments().getString("title");
    String content = getArguments().getString("content");
    String subContent = getArguments().getString("subContent");
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_layout, null);
    positiveBtn = (Button) view.findViewById(R.id.btn_positive);
    negativeBtn = (Button) view.findViewById(R.id.btn_negative);
    tvTitle = (TextView) view.findViewById(R.id.tv_title);
    tvContent = (TextView) view.findViewById(R.id.tv_content);
    tvSubContent = (TextView) view.findViewById(R.id.tv_sub_content);
    divider = view.findViewById(R.id.view);
    if (neg != null) {
      negativeBtn.setVisibility(View.VISIBLE);
      divider.setVisibility(View.VISIBLE);
      negativeBtn.setText(neg);
      negativeBtn.setOnClickListener(v -> {
        dismiss();
        mDialogClick.doNegativeClick();
        divider = null;
        negativeBtn = null;

      });
    }
    if (pos != null) {
      positiveBtn.setVisibility(View.VISIBLE);
      positiveBtn.setText(pos);
      positiveBtn.setOnClickListener(v -> {
        dismiss();
        mDialogClick.doPositiveClick();
        divider = null;
        positiveBtn = null;
      });
    }
    if (title != null) {
      tvTitle.setVisibility(View.VISIBLE);
      tvTitle.setText(title);
    }
    if (content != null) {
      tvContent.setVisibility(View.VISIBLE);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        tvContent.setText(Html.fromHtml(content, 0));
      } else {
        tvContent.setText(Html.fromHtml(content));
      }
    }
    if (subContent != null) {
      tvSubContent.setVisibility(View.VISIBLE);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        tvSubContent.setText(Html.fromHtml(subContent, 0));
      } else {
        tvSubContent.setText(Html.fromHtml(subContent));
      }
    }
    builder.setView(view);
    return builder.create();
  }

  @Override
  public void dismiss() {
    super.dismiss();
    divider = null;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.RoseDialog);
  }

  @Override
  public void onStart() {
    super.onStart();
    Window window = getDialog().getWindow();
    WindowManager.LayoutParams windowParams = window.getAttributes();
    windowParams.dimAmount = 0.5f;
    windowParams.gravity = Gravity.CENTER;
    window.setAttributes(windowParams);
    Dialog dialog = getDialog();
    if (dialog != null) {
      dialog.setCanceledOnTouchOutside(false);
      DisplayMetrics dm = new DisplayMetrics();
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
      dialog.getWindow().setLayout((int) (dm.widthPixels * 0.7), WindowManager.LayoutParams.WRAP_CONTENT);
    }
  }
}
