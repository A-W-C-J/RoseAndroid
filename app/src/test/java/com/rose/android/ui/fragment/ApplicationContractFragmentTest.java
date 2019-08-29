package com.rose.android.ui.fragment;

import com.rose.android.network.HttpClient;
import com.rose.android.presenter.GetProductListPresenter;
import com.rose.android.ui.activity.BaseActivityTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;

/**
 * Created by wenen on 2017/12/29.
 */
public class ApplicationContractFragmentTest extends BaseActivityTest {
    ApplicationContractFragment applicationContractFragment;
    GetProductListPresenter getProductListPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        applicationContractFragment = Mockito.mock(ApplicationContractFragment.class);
        getProductListPresenter = new GetProductListPresenter(httpClient, applicationContractFragment);
    }

    @Test
    public void getProductList() throws Exception {
        getProductListPresenter.getProductList(1);
        Mockito.verify(applicationContractFragment).requestCallBack(Mockito.anyObject());
    }

    @Test
    public void getProductListOnError() throws Exception {
        getProductListPresenter.getProductList(999999999);
        Mockito.verify(applicationContractFragment).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }
}
