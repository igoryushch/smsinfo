package ua.np.services.smsinfo;

/**
 * Copyright (C) 2013 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 20.12.13
 */


import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;

@ContextConfiguration(locations = "classpath*:ua/np/services/smsinfo/OperatorInteractionTest-context.xml")
public class KyivstarIntegrationTest extends AbstractTestNGSpringContextTests {

    @Value( "${kyivstarHost}" )
    private String operatorHost;
    @Value( "${kyivstarHostUser}" )
    private String operatorLogin;
    @Value( "${kyivstarHostPassword}" )
    private String operatorPassword;
    @Autowired
    private ProxySettingsHolder proxySettingsHolder;

    @Test(enabled = true)
    public void testSendMessage() {
        HttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(proxySettingsHolder.newProxyCredential()).build();

        HttpHost proxy = new HttpHost(proxySettingsHolder.getProxyHost(), proxySettingsHolder.getProxyPort());

        RequestConfig config = RequestConfig.custom()
                .setProxy( proxy )
                .build();
        HttpPost postRequest = new HttpPost( operatorHost );
        postRequest.setConfig( config );

        try {
//            StringEntity entity = new StringEntity( getMultiRequestStringForKyivstar() );
            StringEntity entity = new StringEntity( getRequestStringForKyivstar( "First Test Message", "380962276147" ) );
            postRequest.addHeader( "Content-Type", "application/xml; charset=UTF-8" );

            postRequest.setEntity( entity );
            HttpResponse response = httpClient.execute( postRequest );

            Assert.assertEquals( response.getStatusLine().getStatusCode(), 200 );
            KyivstarAcceptanceResponse acceptanceResponse = getAcceptanceStatus( response );

            String deliveryRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<message xmlns=\"http://goldetele.com/cpa\" mid=\"" + acceptanceResponse.getStatus().getMid() + "\">" +
                    "    <service>delivery-request</service>" +
                    "</message>";

            entity = new StringEntity( deliveryRequest, "UTF-8" );
            postRequest.addHeader( "Content-Type", "application/xml; charset=UTF-8" );
            postRequest.setEntity( entity );

            for( int i = 0; i < 5; i++ ) {
                response = httpClient.execute( postRequest );
                printKyivstarStatuses( response );
                try {
                    Thread.sleep( 3000 );
                } catch( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        } catch( IOException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown" );
        }
    }

    private KyivstarAcceptanceResponse getAcceptanceStatus(HttpResponse response){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( KyivstarAcceptanceResponse.class );

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (KyivstarAcceptanceResponse) jaxbUnmarshaller.unmarshal( response.getEntity().getContent() );
        } catch( JAXBException e ) {
            e.printStackTrace();
        } catch( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private void printKyivstarStatuses(HttpResponse response){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( KyivstarMessageStatusReport.class );

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            KyivstarMessageStatusReport messageStatusReport = (KyivstarMessageStatusReport) jaxbUnmarshaller.unmarshal( response.getEntity().getContent() );

            System.out.println( "mid = " + messageStatusReport.getMid() + "  date = " + messageStatusReport.getMessageStatus().getDate()
                    + "  status = " + messageStatusReport.getMessageStatus().getStatus() );

        } catch( JAXBException e ) {
            e.printStackTrace();
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }

    private String getMultiRequestStringForKyivstar() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "id=<message xmlns=\"http://goldentele.com/cpa\"><login>" + operatorLogin + "</login><paswd>" + operatorPassword + "</paswd>" +
                "<tid>1</tid>" +
                "<sin>380962276147</sin>" +
                "<service>bulk-request</service>" +
                "<expiry>17.01.2014 16:10:15</expiry>" +
                "<body content-type=\"text/plain\">First Test Message</body>" +
                "</message>" +
                "&id2=<message xmlns=\"http://goldentele.com/cpa\"><login>newmail</login><paswd>sdgf232fsaqa2</paswd>" +
                "<tid>1</tid>" +
                "<sin>380962276147</sin>" +
                "<service>bulk-request</service>" +
                "<expiry>17.01.2014 16:10:15</expiry>" +
                "<body content-type=\"text/plain\">Second Test Message</body>" +
                "</message>"
                ;
    }

    private String getRequestStringForKyivstar( String message, String phone ) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<message xmlns=\"http://goldentele.com/cpa\"><login>" + operatorLogin + "</login><paswd>" + operatorPassword + "</paswd>" +
                "<tid>1</tid>" +
                "<sin>" + phone + "</sin>" +
                "<service>bulk-request</service>" +
                "<expiry>17.01.2014 16:10:15</expiry>" +
                "<body content-type=\"text/plain\">" + message + "</body>" +
                "</message>";
    }
}