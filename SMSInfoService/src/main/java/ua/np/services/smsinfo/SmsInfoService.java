package ua.np.services.smsinfo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

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

    public void updateStatuses( Map<String, String> newMessageStatuses, Operator operator );

    public List<SmsRequest> readRequestsForSystem( String systemName );

    public void saveRequests( List<SmsRequest> smsRequests );

    public List<SmsRequest> readRequestsForSending();

    public void updateRequests(List<SmsRequest> requestList);
}
