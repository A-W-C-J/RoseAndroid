package com.rose.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rose.android.R;
import com.rose.android.utils.Utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


/**
 * Created by Administrator on 2015/10/9.
 */
public class GuideFragment extends Fragment {
    private TextView mGuideSpeedTv;
    private TextView mSpeedGuideTv;
    private ImageView mGuideIv;

    private boolean mMoveRight;
    private int mImageSrc;
    private int mGuideSpeed, mSpeedGuide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View view1 = getView();
        if (view1 != null) {
            mGuideIv = view1.findViewById(R.id.iv_guide);
            mGuideSpeedTv = view1.findViewById(R.id.tv_guide_speed);
            mSpeedGuideTv = view1.findViewById(R.id.tv_speed_guide);
            updateImage(mImageSrc, mMoveRight);
            updateSpeedText(mGuideSpeed, mSpeedGuide);
        }
    }

    /**
     * @param guideSpeed 灰色字
     * @param speedGuide 红字
     */
    public void updateSpeedText(int guideSpeed, int speedGuide) {
        this.mGuideSpeed = guideSpeed;
        this.mSpeedGuide = speedGuide;
        if (mGuideSpeedTv != null && mSpeedGuideTv != null) {
            mGuideSpeedTv.setText(guideSpeed);
            mSpeedGuideTv.setText(speedGuide);
        }
    }

    public void updateImage(int imageSrc, boolean moveRight) {
        this.mImageSrc = imageSrc;
        this.mMoveRight = moveRight;
        if (mGuideIv != null) {
            if (moveRight) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mGuideIv.getLayoutParams());
                layoutParams.weight = 2.0f;
                layoutParams.setMargins((int) Utils.convertDp2Px(8), 0, 0, 0);
                mGuideIv.setLayoutParams(layoutParams);
            }
            mGuideIv.setBackgroundResource(imageSrc);
        }
    }


}
