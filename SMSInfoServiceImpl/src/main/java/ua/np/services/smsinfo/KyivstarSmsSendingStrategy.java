package ua.np.services.smsinfo;

import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 16.01.14
 */

public class KyivstarSmsSendingStrategy implements SmsSendingStrategy{

    @Value( "${kyivstarHost}" )
    private String operatorHost;
    @Value( "${kyivstarHostUser}" )
    private String operatorLogin;
    @Value( "${kyivstarHostPassword}" )
    private String operatorPassword;

    private Unmarshaller jaxbUnmarshaller;

    private OperatorRestClient operatorRestClient;

    @Override
    public List<SmsRequest> send( List<SmsRequest> smsRequestList ) {

        String xmlRequest = buildXmlRequest( smsRequestList );
        HttpPost postRequest = new HttpPost( operatorHost );
        InputStream responseInputStream = operatorRestClient.sendRequest( postRequest, xmlRequest );
        // unmarshall response
        KyivstarAcceptanceResponse resultResponse = parseResponse(responseInputStream);
        // update operator message id & statuses
        List<KyivstarAcceptanceStatus> statusList = resultResponse.getStatus();
        for (KyivstarAcceptanceStatus acceptanceStatus : statusList){
            for(SmsRequest smsRequest : smsRequestList){
                if( smsRequest.getId() == Long.valueOf( acceptanceStatus.getClid() ) ){
//                    smsRequest.setOperator(  );
                    smsRequest.setOperatorMessageId( acceptanceStatus.getMid() );
                    smsRequest.setStatus( acceptanceStatus.getValue() );
                }
            }
        }

        return smsRequestList;
    }

    private KyivstarAcceptanceResponse parseResponse( InputStream responseInputStream ) {
        KyivstarAcceptanceResponse resultResponse = null;
        try {
            resultResponse = (KyivstarAcceptanceResponse) jaxbUnmarshaller.unmarshal(new StreamSource(responseInputStream));
            return resultResponse;
        } catch( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildXmlRequest(List<SmsRequest> smsRequestList){

        StringBuilder sb = new StringBuilder(  );

        sb.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
        sb.append( "<root xmlns=\"http://goldentele.com/cpa\">" );
        sb.append( "<login>" + operatorLogin + "</login><paswd>" + operatorPassword + "</paswd>" );
        sb.append( "<service>bulk-request</service>" );
//        sb.append( "<expiry>22.01.2014 12:00:00</expiry>" );
        sb.append( "<tid>1</tid>" );
        sb.append( "<messages>" );

        for( SmsRequest request : smsRequestList ){
            sb.append( "<message><IDint>" + request.getId() + "</IDint><sin>" + request.getPhoneNumber() +
                    "</sin><body content-type=\"text/plain\">" + request.getMessageText() + "</body></message>" );
        }

        sb.append( "</messages></root>" );

        return sb.toString();
    }

    public void setOperatorHost( String operatorHost ) {
        this.operatorHost = operatorHost;
    }

    public void setOperatorLogin( String operatorLogin ) {
        this.operatorLogin = operatorLogin;
    }

    public void setOperatorPassword( String operatorPassword ) {
        this.operatorPassword = operatorPassword;
    }

    public void setOperatorRestClient( OperatorRestClient operatorRestClient ) {
        this.operatorRestClient = operatorRestClient;
    }

    public void setJaxbUnmarshaller( Unmarshaller jaxbUnmarshaller ) {
        this.jaxbUnmarshaller = jaxbUnmarshaller;
    }
}
