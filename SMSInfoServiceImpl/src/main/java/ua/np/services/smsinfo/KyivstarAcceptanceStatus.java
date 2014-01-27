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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statusType", namespace = "http://goldetele.com/cpa", propOrder = {
        "value"
})
public class KyivstarAcceptanceStatus {

    @XmlAttribute
    private String mid;
    @XmlAttribute
    private String clid;

    @XmlValue
    private String value;

    public String getMid() {
        return mid;
    }

    public void setMid( String mid ) {
        this.mid = mid;
    }

    public String getClid() {
        return clid;
    }

    public void setClid( String clid ) {
        this.clid = clid;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }
}
