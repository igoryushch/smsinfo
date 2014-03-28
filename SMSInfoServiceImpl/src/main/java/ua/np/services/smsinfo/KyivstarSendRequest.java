package ua.np.services.smsinfo;

import javax.xml.bind.annotation.*;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 12.02.14
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rootType", namespace = "http://goldentele.com/cpa", propOrder = {
        "login",
        "paswd",
        "service",
        "expiry",
        "tid",
        "messages"
})
public class KyivstarSendRequest {

    @XmlElement(namespace = "http://goldentele.com/cpa", required = true)
    private String login;
    @XmlElement(namespace = "http://goldentele.com/cpa", required = true)
    private String paswd;
    @XmlElement(namespace = "http://goldentele.com/cpa", required = true)
    private String service;
    @XmlElement(namespace = "http://goldentele.com/cpa")
    private String expiry;
    @XmlElement(namespace = "http://goldentele.com/cpa", required = true)
    private String tid;
    @XmlElement(namespace = "http://goldentele.com/cpa", required = true)
    private KyivstarMessagesType messages;

    public String getLogin() {
        return login;
    }

    public void setLogin(String value) {
        this.login = value;
    }

    public String getPaswd() {
        return paswd;
    }

    public void setPaswd(String value) {
        this.paswd = value;
    }

    public String getService() {
        return service;
    }

    public void setService(String value) {
        this.service = value;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String value) {
        this.expiry = value;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String value) {
        this.tid = value;
    }

    public KyivstarMessagesType getMessages() {
        return messages;
    }

    public void setMessages(KyivstarMessagesType value) {
        this.messages = value;
    }


}
