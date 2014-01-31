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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 22.01.14
 */


public class OperatorRestClientImpl implements OperatorRestClient {

    private String proxyHost;
    private String proxyPort;
    private String proxyUsername;
    private String proxyPassword;

    public InputStream sendRequest(HttpPost postRequest, String xmlRequest){

        HttpClient httpClient = buildHttpClient( postRequest, null, null );

        HttpResponse response = makeRequest( httpClient, xmlRequest, postRequest );

        if( response != null )
            try {
                return response.getEntity().getContent();
            } catch( IOException e ) {
                e.printStackTrace();
            }

        return null;
    }

    public InputStream sendRequest(HttpPost postRequest, String xmlRequest, String operatorLogin, String operatorPassword){

        HttpClient httpClient = buildHttpClient( postRequest, operatorLogin, operatorPassword );

        HttpResponse response = makeRequest( httpClient, xmlRequest, postRequest );

        if( response != null )
            try {
                return response.getEntity().getContent();
            } catch( IOException e ) {
                e.printStackTrace();
            }

        return null;

    }

    public HttpResponse makeRequest(HttpClient httpClient,String xmlRequest,HttpPost postRequest){

        try {
            StringEntity entity = new StringEntity(xmlRequest);
            postRequest.addHeader( "Content-Type", "application/xml; charset=UTF-8" );
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

    public HttpClient buildHttpClient( HttpPost postRequest, String operatorLogin, String operatorPassword ){
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyHost, Integer.valueOf( proxyPort )),
                new UsernamePasswordCredentials(proxyUsername, proxyPassword));

        if( operatorLogin != null && operatorPassword != null ){
            credsProvider.setCredentials(
                    new AuthScope(postRequest.getURI().toString(), AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials( operatorLogin, operatorPassword ));
        }

        HttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();

        HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf( proxyPort ));

        RequestConfig config = RequestConfig.custom()
                .setProxy( proxy )
                .build();

        postRequest.setConfig( config );

        return httpClient;
    }

    public void setProxyHost( String proxyHost ) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort( String proxyPort ) {
        this.proxyPort = proxyPort;
    }

    public void setProxyUsername( String proxyUsername ) {
        this.proxyUsername = proxyUsername;
    }

    public void setProxyPassword( String proxyPassword ) {
        this.proxyPassword = proxyPassword;
    }
}
