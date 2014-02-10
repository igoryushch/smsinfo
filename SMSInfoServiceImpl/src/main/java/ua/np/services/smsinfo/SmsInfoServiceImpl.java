package ua.np.services.smsinfo;

import javax.jws.WebService;
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

@WebService(endpointInterface = "ua.np.services.smsinfo.SmsInfoService", serviceName = "SmsInfoPort")
public class SmsInfoServiceImpl implements SmsInfoService {

    private SmsService smsService;
    private SmsServiceUtils smsServiceUtils;

    public void setSmsService( SmsService smsService ) {
        this.smsService = smsService;
    }

    public void setSmsServiceUtils( SmsServiceUtils smsServiceUtils ) {
        this.smsServiceUtils = smsServiceUtils;
    }

    @Override
    public String sendMessages( String xml, String systemName ) {
        List<SmsRequest> smsRequests = smsServiceUtils.getRequestsFromXmlString( xml, systemName );
        smsService.saveRequests( smsRequests );
        return smsServiceUtils.buildAcceptedResponse( smsRequests );
    }


    @Override
    public String reportDeliveryData( String systemName ) {
        List<SmsRequest> requestsForSystem = smsService.getRequestsForSystem( systemName );
        return smsServiceUtils.buildDeliveryStatusResponse( requestsForSystem );
    }

/*    @Override
        public void updateStatuses( Map<String, String> newMessageStatuses, Operator operator ) {
            if( operator == null ) {
                smsService.updateStatuses( newMessageStatuses );
            } else {
                smsService.updateStatuses( newMessageStatuses, operator );
            }
        }
*/

}