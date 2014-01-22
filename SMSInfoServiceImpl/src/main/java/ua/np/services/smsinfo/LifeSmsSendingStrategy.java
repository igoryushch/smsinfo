package ua.np.services.smsinfo;

import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 16.01.14
 */

public class LifeSmsSendingStrategy implements SmsSendingStrategy {

    @Value( "${lifeHost}" )
    private String operatorHost;
    @Value( "${lifeHostUser}" )
    private String operatorLogin;
    @Value( "${lifeHostPassword}" )
    private String operatorPassword;

    private OperatorRestClient operatorRestClient;

    @Override
    public List<SmsRequest> send( List<SmsRequest> smsRequestList ) {

        String xmlRequest = buildXmlRequest( smsRequestList );
        HttpPost postRequest = new HttpPost( operatorHost );
        InputStream responseInputStream = operatorRestClient.sendRequest( postRequest, xmlRequest, operatorLogin, operatorPassword );
        return smsRequestList;
    }

    private String buildXmlRequest( List<SmsRequest> smsRequestList ) {
        StringBuilder sb = new StringBuilder(  );
        return sb.toString();
    }
}
