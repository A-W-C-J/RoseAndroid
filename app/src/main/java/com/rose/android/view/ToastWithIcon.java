package com.rose.android.view;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rose.android.R;
import com.rose.android.RoseApplication;
import com.rose.android.utils.Utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.annotations.NonNull;

/**
 * Created by xiaohuabu on 17/9/2.
 */

@SuppressFBWarnings("DC_PARTIALLY_CONSTRUCTED")
public class ToastWithIcon {
    private static volatile Toast toast = null;
    private static TextView text;

    public static Toast init(String msg) {
        if (toast == null) {
            synchronized (ToastWithIcon.class) {
                if (toast == null) {
                    LayoutInflater inflater = LayoutInflater.from(RoseApplication.getAppContext());
                    View layout = inflater.inflate(R.layout.toast_layout,
                            null);
                    text = (TextView) layout.findViewById(R.id.text);
                    text.setText(msg);
                    toast = new Toast(RoseApplication.getAppContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, Utils.getHeight() / 4);
                    toast.setView(layout);
                }
            }
        } else {
            if (text != null) {
                text.setText(msg);
            }
        }
        return toast;
    }
}
