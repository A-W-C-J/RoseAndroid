package com.rose.android.ui.activity;

import com.rose.android.RxTestTools;
import com.rose.android.network.HttpClient;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by wenen on 2017/12/29.
 */

public class BaseActivityTest {
    public HttpClient httpClient;
    BaseActivity baseActivity;

    @BeforeClass
    public static void setRx() {
        RxTestTools.setUp();
    }

    @Before
    public void setUp() throws Exception {
        baseActivity = Mockito.mock(BaseActivity.class);
        httpClient = HttpClient.Builder.getUserService(baseActivity.context);
    }

    @Test
    public void initViews() throws Exception {
        baseActivity.initViews();
        Mockito.verify(baseActivity).initViews();
    }
}
