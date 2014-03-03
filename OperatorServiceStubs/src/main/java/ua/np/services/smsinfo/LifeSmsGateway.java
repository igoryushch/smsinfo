package ua.np.services.smsinfo;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 11.02.14
 */

@Consumes("application/xml")
@Produces("application/xml")
public interface LifeSmsGateway {

    @POST
    @Path( "/send" )
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response sendSms( LifeSendRequest request );

    @GET
    @Produces("application/xml")
    public Response hello();
}
