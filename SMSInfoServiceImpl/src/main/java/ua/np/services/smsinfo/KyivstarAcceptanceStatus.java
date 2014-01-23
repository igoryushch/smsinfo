package ua.np.services.smsinfo;

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

    private String mid;
    private String clid;

    @XmlValue
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }
}
