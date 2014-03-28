package ua.np.services.smsinfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 11.03.14
 */
public class FuseInteractionTest {

    @Test
    public void testSendRequest() {

        List<SmsRequest> smsRequestList = SmsServiceUnitTestSupport.getTestFuseRequest();
        String xmlRequest = buildXmlRequest( smsRequestList );
        HttpPost postRequest = new HttpPost( "http://localhost:8099/smsSend" );
        HttpClient httpClient = buildHttpClient(  );

        HttpResponse response = makeRequest( httpClient, xmlRequest, postRequest );
        try {
            String responseString = EntityUtils.toString( response.getEntity() );
            System.out.println(responseString);
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }

    public HttpResponse makeRequest(HttpClient httpClient,String xmlRequest,HttpPost postRequest){

        try {
            StringEntity entity = new StringEntity(xmlRequest);
            postRequest.addHeader( "Content-Type", "text/xml; charset=UTF-8" );
            postRequest.setEntity( entity );
            HttpResponse response = httpClient.execute( postRequest );

            return response;

        } catch( UnsupportedEncodingException e ) {
            e.printStackTrace();
        } catch( ClientProtocolException e ) {
            e.printStackTrace();
        } catch( IOException e ) {
            e.printStackTrace();
        }

        return null;
    }

    public HttpClient buildHttpClient(  ){

        return HttpClients.createDefault();
    }

    private String buildXmlRequest( List<SmsRequest> smsRequestList ) {
        SmsRequestListWrapper smsRequestListWrapper = new SmsRequestListWrapper( smsRequestList );
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance( SmsRequestListWrapper.class );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            jaxbMarshaller.marshal(smsRequestListWrapper, stringWriter);
            return stringWriter.toString();
        } catch( JAXBException e ) {
            e.printStackTrace();
        }
        return "";
    }
}
