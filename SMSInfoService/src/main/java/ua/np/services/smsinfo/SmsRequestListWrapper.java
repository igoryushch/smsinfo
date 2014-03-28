package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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
 * Date: 13.02.14
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SmsRequestListWrapper {

    private List<SmsRequest> smsRequest = new ArrayList<>(  );

    public SmsRequestListWrapper() {
    }

    public SmsRequestListWrapper( List<SmsRequest> smsRequest ) {
        this.smsRequest.addAll( smsRequest );
    }

    public void addRequest(SmsRequest smsRequest){
        this.smsRequest.add( smsRequest );
    }

    public List<SmsRequest> getRequestList() {
        return smsRequest;
    }

    public void setRequestList( List<SmsRequest> requestList ) {
        this.smsRequest = requestList;
    }
}
