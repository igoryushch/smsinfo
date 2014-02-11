package ua.np.services.smsinfo;

import java.util.List;
import java.util.Map;

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
    private OperatorDao operatorDao;

    public void setSmsServiceDao( SmsServiceDao smsServiceDao ) {
        this.smsServiceDao = smsServiceDao;
    }

    public void setOperatorDao( OperatorDao operatorDao ) {
        this.operatorDao = operatorDao;
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
    public void updateRequests( Map<String, String> requestList, String operatorName ) {
        Operator operator = operatorDao.getOperatorByName( operatorName );
        if( operator != null ){
            smsServiceDao.updateStatuses( requestList, operator );
        } else {
            updateRequests( requestList );
        }
    }

    @Override
    public void updateRequests( Map<String, String> requestList ) {
        smsServiceDao.updateStatuses( requestList );
    }

    @Override
    public List<SmsRequest> getRequestsForSystem( String systemName ) {
        return smsServiceDao.getRequestsForSystem( systemName );
    }
}