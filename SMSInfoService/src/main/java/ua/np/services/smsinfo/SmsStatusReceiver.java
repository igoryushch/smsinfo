package ua.np.services.smsinfo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 28.01.14
 */

@WebService
public interface SmsStatusReceiver {

    @WebMethod
    public String updateStatuses(@WebParam(name = "xml")String xml);

}
