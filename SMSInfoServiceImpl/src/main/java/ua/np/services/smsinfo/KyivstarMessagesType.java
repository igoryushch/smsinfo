package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

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
@XmlType(name = "messagesType", namespace = "http://goldentele.com/cpa", propOrder = {
        "message"
})
public class KyivstarMessagesType {

    @XmlElement(namespace = "http://goldentele.com/cpa")
    private List<KyivstarMessage> message;

    public List<KyivstarMessage> getMessage() {
        if (message == null) {
            message = new ArrayList<KyivstarMessage>();
        }
        return this.message;
    }

    public void setMessage( List<KyivstarMessage> message ) {
        this.message = message;
    }
}
