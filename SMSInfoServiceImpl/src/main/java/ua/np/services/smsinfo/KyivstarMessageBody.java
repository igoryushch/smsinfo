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
@XmlType(name = "bodyType", namespace = "http://goldentele.com/cpa", propOrder = {
        "value"
})
public class KyivstarMessageBody {

    @XmlValue
    private String value;
    @XmlAttribute(name = "content-type")
    private String contentType;

    public KyivstarMessageBody() {
    }

    public KyivstarMessageBody( String value, String contentType ) {
        this.value = value;
        this.contentType = contentType;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType( String contentType ) {
        this.contentType = contentType;
    }
}
