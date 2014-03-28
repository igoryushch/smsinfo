package ua.np.services.smsinfo;

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
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 21.01.14
 */

@ContextConfiguration(locations = "classpath*:ua/np/services/smsinfo/OperatorInteractionTest-context.xml")
public class LifeIntegrationTest extends AbstractTestNGSpringContextTests {

    @Value( "${lifeHost}" )
    private String operatorHost;
    @Value( "${lifeAuthHost}" )
    private String operatorAuthHost;
    @Value( "${lifeHostUser}" )
    private String operatorLogin;
    @Value( "${lifeHostPassword}" )
    private String operatorPassword;
    @Autowired
    private ProxySettingsHolder proxySettingsHolder;

    @Test(enabled = false)
    public void testSendMessage() {
        // test life api
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxySettingsHolder.getProxyHost(), proxySettingsHolder.getProxyPort()),
                new UsernamePasswordCredentials(proxySettingsHolder.getProxyUsername(), proxySettingsHolder.getProxyPassword()));

        credsProvider.setCredentials(
                new AuthScope(operatorAuthHost, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials( operatorLogin, operatorPassword ));

        HttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();

        HttpHost proxy = new HttpHost(proxySettingsHolder.getProxyHost(), proxySettingsHolder.getProxyPort());

        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        HttpPost postRequest = new HttpPost( operatorHost );
        postRequest.setConfig( config );
        int uniqueKey = (int) (Math.random()*Integer.MAX_VALUE);
        String request = "<message>" +
                "<service id=\"individual\" validity=\"+5 hour\" source = \"NovaPoshta\" uniq_key=\"" + String.valueOf( uniqueKey ) + "\"/>" +
                "<to>380962276147</to>" +
                "<body content-type=\"text/plain\">First Test Message</body>" +
                "<to>380962276147</to>" +
                "<body content-type=\"text/plain\">Second Test Message</body>" +
                "</message>";

        System.out.println(request);

        try {
            StringEntity entity = new StringEntity( request, "UTF-8" );
            postRequest.addHeader( "Content-Type", "text/xml; charset=UTF-8" );
            postRequest.setEntity( entity );
            HttpResponse response = httpClient.execute( postRequest );
            Assert.assertEquals( response.getStatusLine().getStatusCode(), 200 );
            System.out.println( EntityUtils.toString( response.getEntity() ) );
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
}
