package ua.np.services.smsinfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
 * Date: 13.02.14
 */
public class OperatorRestClientNoProxyImpl implements OperatorRestClient {
    public InputStream sendRequest(HttpPost postRequest, String xmlRequest){

        HttpClient httpClient = buildHttpClient(  );

        HttpResponse response = makeRequest( httpClient, xmlRequest, postRequest );

        if( response != null )
            try {
                return response.getEntity().getContent();
            } catch( IOException e ) {
                e.printStackTrace();
            }

        return null;
    }

    public InputStream sendRequest(HttpPost postRequest, String xmlRequest, String operatorLogin, String operatorPassword, String authUrl){

        HttpClient httpClient = buildHttpClient(  );

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

    public HttpClient buildHttpClient(  ){

        return HttpClients.createDefault();
    }

    private String getAuthUrl(String authUrl, HttpPost postRequest ){
        return authUrl == null ? postRequest.getURI().toString() : authUrl;
    }
}
