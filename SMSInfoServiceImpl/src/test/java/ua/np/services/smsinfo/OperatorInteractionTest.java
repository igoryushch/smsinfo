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
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class OperatorInteractionTest {

    @Test(enabled = false)
    public void testSendKyivstar(){
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost( "http://cc.goldentele.com/sms_service/cpa.phtml" );
//        HttpPost postRequest = new HttpPost( "http://cc.goldentele.com/CPA_MULTI_XML/cpa.phtml" );

        try {
//            StringEntity entity = new StringEntity( getMultiRequestStringForKyivstar() );
            StringEntity entity = new StringEntity( getRequestStringForKyivstar( "First Test Message", "380962276147" ) );
            postRequest.addHeader( "Content-Type", "application/xml; charset=UTF-8" );

            postRequest.setEntity( entity );
            HttpResponse response = httpclient.execute( postRequest );

            Assert.assertEquals( response.getStatusLine().getStatusCode(), 200 );
            System.out.println( IOUtils.toString( response.getEntity().getContent() ) );

            List<Map<String,String>> statuses = RequestResponceParser.getResponceStatuses( response.getEntity().getContent() );

            String mip = "";
            for (Map<String,String> statusMap : statuses){
                for( Map.Entry<String, String> entry : statusMap.entrySet() ){
                    mip = entry.getKey();
                    System.out.println(entry.getKey() + " ==> " + entry.getValue());
                }
            }

            String deliveryRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<message xmlns=\"http://goldetele.com/cpa\" mid=\""+ mip + "\">" +
                    "    <service>delivery-request</service>" +
                    "</message>";

            entity = new StringEntity( deliveryRequest, "UTF-8" );
            postRequest.addHeader( "Content-Type", "application/xml; charset=UTF-8" );
            postRequest.setEntity( entity );

            for( int i = 0; i < 5 ;i++ ){
                response = httpclient.execute( postRequest );
                List<Map<String,String>> deliveryStatuses = RequestResponceParser.getDeliveryStatuses( response.getEntity().getContent() );

                for (Map<String,String> statusMap : deliveryStatuses){
                    for( Map.Entry<String, String> entry : statusMap.entrySet() ){
                        System.out.println(entry.getKey() + " ==> " + entry.getValue());
                    }
                }

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

    private String getMultiRequestStringForKyivstar(){
        return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
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

    private String getRequestStringForKyivstar(String message, String phone){
        return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<message xmlns=\"http://goldentele.com/cpa\"><login>newmail</login><paswd>sdgf232fsaqa2</paswd>" +
                    "<tid>1</tid>" +
                    "<sin>" + phone + "</sin>" +
                    "<service>bulk-request</service>" +
                    "<expiry>17.01.2014 16:10:15</expiry>" +
                    "<body content-type=\"text/plain\">" + message + "</body>" +
                "</message>";
    }

    @Test(enabled = true)
    public void testSendLife(){
        // test life api
//        HttpHost proxy = new HttpHost("proxy.np.ua", 3128, "http");
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//        CredentialsProvider credentialsProviderProxy = new BasicCredentialsProvider();
//        credentialsProviderProxy.setCredentials(
//                new AuthScope("proxy.np.ua", 3128),
//                new UsernamePasswordCredentials("yushchenko.i", "igory200382"));

//        HttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider( credentialsProviderProxy ).build();
        HttpClient httpclient = HttpClientBuilder.create().build();

        Credentials credentials = new UsernamePasswordCredentials( "NovaPoshta", "eEQo95eufh2trfphb" );
        HttpClientContext context = HttpClientContext.create();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials( AuthScope.ANY, credentials );
        context.setCredentialsProvider( credentialsProvider );


//        HttpPost postRequest = new HttpPost( "http://sms.businesslife.com.ua/clients.php" );
//        HttpPost postRequest = new HttpPost( "https://smsbulk.businesslife.com.ua" );
//        HttpPost postRequest = new HttpPost( "http://bulk.bs-group.com.ua/clients.php" );
        HttpPost postRequest = new HttpPost( "https://api-test.life.com.ua/ip2sms/" );
//        postRequest.setConfig(config );
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
            HttpResponse response = httpclient.execute( postRequest, context );
            Assert.assertEquals( response.getStatusLine().getStatusCode(), "200" );
            System.out.println( IOUtils.toString( response.getEntity().getContent() ) );
    }  catch( ClientProtocolException e ) {
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
}
