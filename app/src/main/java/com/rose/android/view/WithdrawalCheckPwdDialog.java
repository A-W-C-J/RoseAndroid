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
import android.widget.EditText;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.contract.WithdrawalPwdDialogClick;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Created by xiaohuabu on 17/9/12.
 */

@SuppressFBWarnings("DC_PARTIALLY_CONSTRUCTED")
public class WithdrawalCheckPwdDialog extends DialogFragment {
    private Button positiveBtn;
    private Button negativeBtn;
    private TextView tvTitle;
    private View divider;
    private EditText editText;
    private static WithdrawalPwdDialogClick mDialogClick;
    private static volatile WithdrawalCheckPwdDialog withdrawalCheckPwdDialog = null;

    public static WithdrawalCheckPwdDialog newInstance(WithdrawalPwdDialogClick dialogClick, String pos, String neg, String title) {
        if (withdrawalCheckPwdDialog == null) {
            synchronized (WithdrawalCheckPwdDialog.class) {
                if (withdrawalCheckPwdDialog == null) {
                    withdrawalCheckPwdDialog = new WithdrawalCheckPwdDialog();
                } else {
                    withdrawalCheckPwdDialog.dismiss();
                    withdrawalCheckPwdDialog = null;
                    withdrawalCheckPwdDialog = new WithdrawalCheckPwdDialog();
                }
            }
        } else {
            withdrawalCheckPwdDialog.dismiss();
            withdrawalCheckPwdDialog = null;
            withdrawalCheckPwdDialog = new WithdrawalCheckPwdDialog();
        }
        Bundle args = new Bundle();
        args.putString("pos", pos);
        args.putString("neg", neg);
        args.putString("title", title);
        mDialogClick = dialogClick;
        withdrawalCheckPwdDialog.setArguments(args);
        return withdrawalCheckPwdDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String pos = getArguments().getString("pos");
        String neg = getArguments().getString("neg");
        String title = getArguments().getString("title");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.withdrawal_check_pwd_dialog, null);
        positiveBtn = (Button) view.findViewById(R.id.btn_positive);
        negativeBtn = (Button) view.findViewById(R.id.btn_negative);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        editText = (EditText) view.findViewById(R.id.pwd);
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
                mDialogClick.doPositiveClick(editText.getText().toString());
                dismiss();
            });
        }
        if (title != null) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
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
