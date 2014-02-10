package ua.np.services.smsinfo;

import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 10.02.14
 */
public interface SmsService {

    List<SmsRequest> getRequestsForSending();

    List<SmsRequest> getRequestsForSystem( String systemName );

    void saveRequests( List<SmsRequest> smsRequests );

    void updateRequests( List<SmsRequest> requestList );
}
