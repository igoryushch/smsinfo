package ua.np.services.smsinfo;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;

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

public class ProxySettingsHolder {

    @Value("${host}")
    private String proxyHost;
    @Value("${port}")
    private String proxyPort;
    @Value("${username}")
    private String proxyUsername;
    @Value("${password}")
    private String proxyPassword;
    
    public CredentialsProvider newProxyCredential(){

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyHost, Integer.valueOf( proxyPort )),
                new UsernamePasswordCredentials(proxyUsername, proxyPassword));

        return credsProvider;

    }

    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPort() {
        return Integer.valueOf( proxyPort );
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

}
