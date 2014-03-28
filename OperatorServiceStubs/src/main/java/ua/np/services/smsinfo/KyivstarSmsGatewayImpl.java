package ua.np.services.smsinfo;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 12.02.14
 */

public class KyivstarSmsGatewayImpl implements KyivstarSmsGateway {

    @Override
    public Response sendSms(KyivstarSendRequest request) {

        ObjectFactory objectFactory = new ObjectFactory();
        KyivstarAcceptanceResponse acceptanceResponse = objectFactory.createKyivstarAcceptanceResponse();

        List<KyivstarMessage> messageList = request.getMessages().getMessage();
        for( KyivstarMessage message : messageList ){
            acceptanceResponse.getStatus().add( objectFactory.createStatusType( generateID(), message.getiDint(), "Accepted" ) );
        }

        return  Response.ok( objectFactory.createKyivstarAcceptanceResponse( acceptanceResponse ) ).build();
    }

    @Override
    public Response hello() {
        Response.ResponseBuilder builder =
                Response.status( Response.Status.ACCEPTED);
        builder.type("application/xml");
        builder.entity("<result>Hello to you!</result>");

        return builder.build();
    }

    private String generateID(){
        return String.valueOf( (int) (Math.random()*Integer.MAX_VALUE) );
    }

}
