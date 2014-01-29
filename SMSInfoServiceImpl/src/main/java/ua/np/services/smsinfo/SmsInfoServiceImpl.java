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

public class SmsInfoServiceImpl implements SmsInfoService{

    private SmsServiceDao smsServiceDao;
    private SmsServiceUtils smsServiceUtils;

    public void setSmsServiceDao( SmsServiceDao smsServiceDao ) {
        this.smsServiceDao = smsServiceDao;
    }

    public void setSmsServiceUtils( SmsServiceUtils smsServiceUtils ) {
        this.smsServiceUtils = smsServiceUtils;
    }

    @Override
    public String sendMessages( String xml, String systemName ) {
        List<SmsRequest> smsRequests = smsServiceUtils.getRequestsFromXmlString( xml, systemName );
        smsServiceDao.addRequests( smsRequests );

        return smsServiceUtils.buildAcceptedResponse( smsRequests );
    }

    @Override
    public String getDeliveryStatusData( String systemName ) {
        return smsServiceUtils.buildDeliveryStatusResponse(smsServiceDao.getRequestsForSystem( systemName ));
    }

    @Override
    public void updateStatuses( Map<String, String> newMessageStatuses, Operator operator ) {
        if( operator == null ) smsServiceDao.updateStatuses( newMessageStatuses );
        else smsServiceDao.updateStatuses( newMessageStatuses, operator );
    }
}