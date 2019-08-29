package com.rose.android.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
 * Created by xiaohuabu on 17/9/19.
 */

@SuppressFBWarnings("DC_PARTIALLY_CONSTRUCTED")
public class StockBuyOrSaleDialog extends DialogFragment {
  private Button positiveBtn;
  private Button negativeBtn;
  private TextView tvTitle;
  private TextView tvNum, tvName, tvPrice, tvCount;
  private View divider;
  private static DialogClick mDialogClick;
  private static volatile StockBuyOrSaleDialog stockBuyOrSaleDialog = null;

  public static StockBuyOrSaleDialog newInstance(DialogClick dialogClick, String pos, String neg, String title, String num, String name, String price, String count) {
    if (stockBuyOrSaleDialog == null) {
      synchronized (StockBuyOrSaleDialog.class) {
        if (stockBuyOrSaleDialog == null) {
          stockBuyOrSaleDialog = new StockBuyOrSaleDialog();
        } else {
          stockBuyOrSaleDialog.dismiss();
          stockBuyOrSaleDialog = null;
          stockBuyOrSaleDialog = new StockBuyOrSaleDialog();
        }
      }
    } else {
      stockBuyOrSaleDialog.dismiss();
      stockBuyOrSaleDialog = null;
      stockBuyOrSaleDialog = new StockBuyOrSaleDialog();
    }
    Bundle args = new Bundle();
    args.putString("pos", pos);
    args.putString("neg", neg);
    args.putString("title", title);
    args.putString("num", num);
    args.putString("price", price);
    args.putString("count", count);
    args.putString("name", name);
    mDialogClick = dialogClick;
    stockBuyOrSaleDialog.setArguments(args);
    return stockBuyOrSaleDialog;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    String pos = getArguments().getString("pos");
    String neg = getArguments().getString("neg");
    String title = getArguments().getString("title");
    String num = getArguments().getString("num");
    String price = getArguments().getString("price");
    String count = getArguments().getString("count");
    String name = getArguments().getString("name");
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.stock_buy_sale_dialog_layout, null);
    positiveBtn = (Button) view.findViewById(R.id.btn_positive);
    negativeBtn = (Button) view.findViewById(R.id.btn_negative);
    tvTitle = (TextView) view.findViewById(R.id.tv_title);
    tvNum = (TextView) view.findViewById(R.id.num);
    tvName = (TextView) view.findViewById(R.id.name);
    tvPrice = (TextView) view.findViewById(R.id.price);
    tvCount = (TextView) view.findViewById(R.id.count);
    divider = view.findViewById(R.id.view);
    if (neg != null) {
      negativeBtn.setVisibility(View.VISIBLE);
      divider.setVisibility(View.VISIBLE);
      negativeBtn.setText(neg);
      negativeBtn.setOnClickListener(v -> {
        mDialogClick.doNegativeClick();
        dismiss();
      });
    }
    if (pos != null) {
      positiveBtn.setVisibility(View.VISIBLE);
      positiveBtn.setText(pos);
      positiveBtn.setOnClickListener(v -> {
        mDialogClick.doPositiveClick();
        dismiss();
      });
    }
    if (title != null) {
      tvTitle.setVisibility(View.VISIBLE);
      tvTitle.setText(title);
    }
    if (num != null) {
      tvNum.setVisibility(View.VISIBLE);
      tvNum.setText(num);
    }
    if (price != null) {
      tvPrice.setVisibility(View.VISIBLE);
      tvPrice.setText(price);
    }
    if (count != null) {
      tvCount.setVisibility(View.VISIBLE);
      tvCount.setText(count);
    }
    if (name != null) {
      tvName.setVisibility(View.VISIBLE);
      tvName.setText(name);
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
