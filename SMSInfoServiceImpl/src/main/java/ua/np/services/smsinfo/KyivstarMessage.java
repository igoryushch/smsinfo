package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

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
@XmlType(name = "messageType", namespace = "http://goldentele.com/cpa", propOrder = {
        "iDint",
        "sin",
        "body"
})
public class KyivstarMessage {

    @XmlElement(name = "IDint", namespace = "http://goldentele.com/cpa", required = true)
    private String iDint;
    @XmlElement(namespace = "http://goldentele.com/cpa", required = true)
    private String sin;
    @XmlElement(namespace = "http://goldentele.com/cpa", required = true)
    private KyivstarMessageBody body;

    public KyivstarMessage() {
    }

    public KyivstarMessage( String iDint, String sin, KyivstarMessageBody body ) {
        this.iDint = iDint;
        this.sin = sin;
        this.body = body;
    }

    public String getiDint() {
        return iDint;
    }

    public void setiDint( String iDint ) {
        this.iDint = iDint;
    }

    public String getSin() {
        return sin;
    }

    public void setSin( String sin ) {
        this.sin = sin;
    }

    public KyivstarMessageBody getBody() {
        return body;
    }

    public void setBody( KyivstarMessageBody body ) {
        this.body = body;
    }
}
