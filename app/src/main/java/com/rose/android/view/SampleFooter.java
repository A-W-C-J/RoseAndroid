package com.rose.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.rose.android.R;


/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * RecyclerView的FooterView，简单的展示一个TextView
 */
public class SampleFooter extends RelativeLayout {

    public SampleFooter(Context context) {
        super(context);
        init(context);
    }

    public SampleFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SampleFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        inflate(context, R.layout.sample_footer, this);
    }
}
