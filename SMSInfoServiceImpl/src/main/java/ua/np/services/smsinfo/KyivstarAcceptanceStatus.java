package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

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

@XmlRootElement(name = "status" )
public class KyivstarAcceptanceStatus {

    @XmlAttribute
    private String mid;
    @XmlAttribute
    private String clid;

    @XmlValue
    private String status;

    public String getMid() {
        return mid;
    }

    public String getStatus() {
        return status;
    }

    public String getClid() {
        return clid;
    }
}
