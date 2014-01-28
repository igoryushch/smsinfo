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
 * Date: 09.01.14
 */


public interface SmsServiceDao {

    public void addRequests(List <SmsRequest> requests);

    public List<SmsRequest> getRequestsForSystem(String systemName);

    public void updateOperatorStatuses(Map<String,String> statusMap, Operator operator);

    public void updateInternalStatuses(List<SmsRequest> requestList);

}
