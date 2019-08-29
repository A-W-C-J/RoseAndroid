package com.rose.android.ui.activity;

import com.rose.android.presenter.GetOrderDetailsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by wenen on 2017/12/29.
 */
public class ContractFlowsActivityTest extends BaseActivityTest {
    ContractFlowsActivity contractFlowsActivity;
    GetOrderDetailsPresenter getOrderDetailsPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        contractFlowsActivity = Mockito.mock(ContractFlowsActivity.class);
        getOrderDetailsPresenter = new GetOrderDetailsPresenter(contractFlowsActivity, httpClient);
    }

    @Test
    public void getOrderDetails() throws Exception {
        getOrderDetailsPresenter.getOrderDetails(1);
        Mockito.verify(contractFlowsActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }

    @Test
    public void requestCallBack() throws Exception {
        contractFlowsActivity.requestCallBack(Mockito.anyObject());
        Mockito.verify(contractFlowsActivity).requestCallBack(Mockito.anyObject());
    }

    @Test
    public void onDestroy() throws Exception {
        contractFlowsActivity.onDestroy();
        Mockito.verify(contractFlowsActivity).onDestroy();
    }
}
