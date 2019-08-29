package com.rose.android.ui.activity;

import com.rose.android.presenter.GetZoomInfoPresenter;
import com.rose.android.presenter.PutZoomPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by wenen on 2017/12/29.
 */
public class ZoomOutContractActivityTest extends BaseActivityTest {
    ZoomOutContractActivity zoomOutContractActivity;
    GetZoomInfoPresenter getZoomInfoPresenter;
    PutZoomPresenter putZoomPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        zoomOutContractActivity = Mockito.mock(ZoomOutContractActivity.class);
        getZoomInfoPresenter = new GetZoomInfoPresenter(zoomOutContractActivity, httpClient);
        putZoomPresenter = new PutZoomPresenter(zoomOutContractActivity, httpClient);
    }

    @Test
    public void putZoom() throws Exception {
        putZoomPresenter.putZoom(10000l, "1");
        Mockito.verify(zoomOutContractActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }

    @Test
    public void getZoomInfo() throws Exception {
        getZoomInfoPresenter.getZoomInfo(100000L, "1");
        Mockito.verify(zoomOutContractActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }

}
