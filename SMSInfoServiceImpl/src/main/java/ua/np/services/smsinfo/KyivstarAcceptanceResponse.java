package ua.np.services.smsinfo;

import javax.xml.bind.annotation.*;
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
 * Date: 21.01.14
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reportType", namespace = "http://goldetele.com/cpa", propOrder = {
        "status"
})
public class KyivstarAcceptanceResponse {

    @XmlElement(namespace = "http://goldetele.com/cpa")
    private List<KyivstarAcceptanceStatus> status;

    public List<KyivstarAcceptanceStatus> getStatus() {
        if( status == null ){
            status = new ArrayList<KyivstarAcceptanceStatus>();
        }
        return status;
    }
}
