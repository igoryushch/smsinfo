package ua.np.services.smsinfo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Copyright 2013 Nova Poshta property
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 18.12.13
 * Time: 10:50
 */

@WebService
public interface SmsInfoService {

    @WebMethod(operationName = "sendMessages")
    public String sendMessages( @WebParam(name = "xml") String xml, @WebParam(name = "systemName") String systemName );

    @WebMethod(operationName = "getDeliveryStatusData")
    public String reportDeliveryData( @WebParam(name = "systemName") String systemName );

    @WebMethod
    public void updateStatuses( UpdateRequest updateRequest, String operatorName );
}
