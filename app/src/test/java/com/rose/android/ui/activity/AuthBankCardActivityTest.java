package com.rose.android.ui.activity;

import com.rose.android.presenter.AuthBankCardPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by wenen on 2017/12/29.
 */
public class AuthBankCardActivityTest extends BaseActivityTest {
    AuthBankCardActivity authBankCardActivity;
    AuthBankCardPresenter authBankCardPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        authBankCardActivity = Mockito.mock(AuthBankCardActivity.class);
        authBankCardPresenter = new AuthBankCardPresenter(authBankCardActivity, httpClient);
    }

    @Test
    public void postBankCard() throws Exception {
        authBankCardPresenter.postBankCard("12346", "1234", "1780990");
        Mockito.verify(authBankCardActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }

}
