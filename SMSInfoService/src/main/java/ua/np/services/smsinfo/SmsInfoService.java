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

    @WebMethod
    public String sendMessages (@WebParam(name = "xml")String xml, @WebParam(name = "systemName") String systemName);

    @WebMethod
    public String getDeliveryStatusData ( @WebParam(name = "systemName") String systemName );

    @WebMethod
    public String updateStatuses ( @WebParam(name = "operatorName") String operatorName, @WebParam(name = "statusData") String statusData );

}
