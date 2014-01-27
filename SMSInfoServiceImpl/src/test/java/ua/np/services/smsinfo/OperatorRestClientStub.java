package ua.np.services.smsinfo;

import org.apache.http.client.methods.HttpPost;

import java.io.ByteArrayInputStream;
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

public class OperatorRestClientStub implements OperatorRestClient {

    public InputStream sendRequest(HttpPost postRequest, String xmlRequest){
        return getKyivstarTestInputStream();
    }

    public InputStream sendRequest(HttpPost postRequest, String xmlRequest, String operatorLogin, String operatorPassword){
        return getLifeTestInputStream();
    }

    private InputStream getKyivstarTestInputStream(){
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<report xmlns=\"http://goldetele.com/cpa\">\n" +
                "    <status mid=\"84140005\" clid=\"0001\">Accepted</status>\n" +
                "    <status mid=\"84140006\" clid=\"0002\">Accepted</status>\n" +
                "    <status mid=\"84140007\" clid=\"0003\">Accepted</status>\n" +
                "    <status mid=\"84140008\" clid=\"0004\">Accepted</status>\n" +
                "    <status mid=\"84140009\" clid=\"0005\">Accepted</status>\n" +
                "</report>";
        return new ByteArrayInputStream(str.getBytes());
    }

    private InputStream getLifeTestInputStream() {
        String str = "<status date='Thu, 23 Jan 2014 10:18:02 +0200' groupid='2716196'><id>700068872</id><state>Accepted</state><id>700068873</id><state>Accepted</state>" +
                "<id>700068874</id><state>Accepted</state><id>700068875</id><state>Accepted</state><id>700068876</id><state>Accepted</state></status>";
        return new ByteArrayInputStream(str.getBytes());
    }

}
