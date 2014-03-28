package ua.np.services.smsinfo;

import javax.ws.rs.core.Response;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 13.02.14
 */

public class LifeSmsGatewayImpl implements LifeSmsGateway {
    @Override
    public Response sendSms( LifeSendRequest request ) {
        return null;
    }

    @Override
    public Response hello() {
        Response.ResponseBuilder builder =
                Response.status( Response.Status.ACCEPTED);
        builder.type("application/xml");
        builder.entity("<result>Hello to you!</result>");

        return builder.build();
    }
}
