package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

@XmlRootElement(name = "report",namespace="http://goldetele.com/cpa")
public class KyivstarAcceptanceResponse {

    @XmlElement
    private KyivstarStatus status;

    public KyivstarStatus getStatus() {
        return status;
    }
}
