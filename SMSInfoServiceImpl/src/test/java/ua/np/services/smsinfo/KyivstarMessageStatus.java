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
public class KyivstarMessageStatus {

    @XmlAttribute
    private String date;
    @XmlAttribute
    private String error;

    @XmlValue
    private String status;

    public String getDate() {
        return date;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }
}
