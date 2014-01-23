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


import org.apache.cxf.helpers.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@ContextConfiguration
public class OperatorInteractionTest extends AbstractTestNGSpringContextTests {

    @Value("${host}")
    private String proxyHost;
    @Value("${port}")
    private String proxyPort;
    @Value("${username}")
    private String proxyUsername;
    @Value("${password}")
    private String proxyPassword;

    @Test(enabled = true)
    public void testSendKyivstar() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyHost, Integer.valueOf( proxyPort )),
                new UsernamePasswordCredentials(proxyUsername, proxyPassword));
        HttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();

        HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf( proxyPort ));

        RequestConfig config = RequestConfig.custom()
                .setProxy( proxy )
                .build();
        HttpPost postRequest = new HttpPost( "http://cc.goldentele.com/sms_service/cpa.phtml" );
//        HttpPost postRequest = new HttpPost( "http://cc.goldentele.com/CPA_MULTI_XML/cpa.phtml" );
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
                    "<message xmlns=\"http://goldetele.com/cpa\" mid=\"" + //acceptanceResponse.getStatus().getMid() + "\">" +
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

    private String getMultiRequestStringForKyivstar() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "id=<message xmlns=\"http://goldentele.com/cpa\"><login>newmail</login><paswd>sdgf232fsaqa2</paswd>" +
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
                "<message xmlns=\"http://goldentele.com/cpa\"><login>newmail</login><paswd>sdgf232fsaqa2</paswd>" +
                "<tid>1</tid>" +
                "<sin>" + phone + "</sin>" +
                "<service>bulk-request</service>" +
                "<expiry>17.01.2014 16:10:15</expiry>" +
                "<body content-type=\"text/plain\">" + message + "</body>" +
                "</message>";
    }

    @Test(enabled = false)
    public void testSendLife() {
        // test life api

//        String lifeHost = "https://api-test.life.com.ua/ip2sms/";
//        String lifeHost = "http://bulk.bs-group.com.ua/clients.php";
        String lifeHost = "https://smsbulk.businesslife.com.ua";
//        String lifeHost = "http://sms.businesslife.com.ua/clients.php";

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyHost, Integer.valueOf( proxyPort )),
                new UsernamePasswordCredentials(proxyUsername, proxyPassword));

        credsProvider.setCredentials(
                new AuthScope(lifeHost, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials( "NovaPoshta", "eEQo95eufh2trfphb" ));

        HttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();

        HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf( proxyPort ));

        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        HttpPost postRequest = new HttpPost( lifeHost );
        postRequest.setConfig( config );
        String request = "<message>" +
                "<service id=\"individual\" source = “TEST_NUMBER”/>" +
                "<to>+380962276147</to>" +
                "<body content-type=\"text/plain\">First Test Message</body>" +
                "<to>+380962276147</to>" +
                "<body content-type=\"text/plain\">Second Test Message</body>" +
                "</message>";

        try {
            StringEntity entity = new StringEntity( request, "UTF-8" );
            postRequest.addHeader( "Content-Type", "application/xml; charset=UTF-8" );
            postRequest.setEntity( entity );
            HttpResponse response = httpClient.execute( postRequest );
            Assert.assertEquals( response.getStatusLine().getStatusCode(), 200 );
            System.out.println( IOUtils.toString( response.getEntity().getContent() ) );
        } catch( ClientProtocolException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown" );
        } catch( UnsupportedEncodingException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown" );
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
}
