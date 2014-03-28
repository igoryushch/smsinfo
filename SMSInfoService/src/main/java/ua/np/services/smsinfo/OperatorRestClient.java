package ua.np.services.smsinfo;

import org.apache.http.client.methods.HttpPost;

import java.io.InputStream;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 27.01.14
 */

public interface OperatorRestClient {

    public InputStream sendRequest(HttpPost postRequest, String xmlRequest);
    public InputStream sendRequest(HttpPost postRequest, String xmlRequest, String operatorLogin, String operatorPassword, String authUrl);

}
