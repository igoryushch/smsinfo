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
 * Date: 10.01.14
 */
public class SmsServiceImpl implements SmsService {

    private SmsServiceDao smsServiceDao;

    public void setSmsServiceDao( SmsServiceDao smsServiceDao ) {
        this.smsServiceDao = smsServiceDao;
    }

    @Override
    public List<SmsRequest> getRequestsForSending() {
        return smsServiceDao.getMessagesToSend();
    }

    @Override
    public void saveRequests( List<SmsRequest> smsRequests ) {
        smsServiceDao.addRequests( smsRequests );
    }

    @Override
    public void updateRequests( List<SmsRequest> smsRequests ) {
        smsServiceDao.mergeMessages( smsRequests );
    }

    @Override
    public List<SmsRequest> getRequestsForSystem( String systemName ) {
        return smsServiceDao.getRequestsForSystem( systemName );
    }
}