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
 * Date: 21.01.14
 */

@XmlRootElement(name = "message",namespace="http://goldetele.com/cpa")
public class KyivstarMessageStatusReport {

    @XmlAttribute
    private String mid;

    @XmlElement
    private String service;

    @XmlElement(name = "status")
    private KyivstarMessageStatus messageStatus;

    public String getMid() {
        return mid;
    }

    public String getService() {
        return service;
    }

    public KyivstarMessageStatus getMessageStatus() {
        return messageStatus;
    }
}
