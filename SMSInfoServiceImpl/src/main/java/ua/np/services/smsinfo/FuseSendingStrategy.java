package ua.np.services.smsinfo;

import org.apache.http.client.methods.HttpPost;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 06.03.14
 */
public class FuseSendingStrategy implements SmsSendingStrategy {

    private String operatorHost;
    private OperatorRestClient operatorRestClient;

    @Override
    public List<SmsRequest> send( List<SmsRequest> smsRequestList, Operator operator ) {
        String xmlRequest = buildXmlRequest( smsRequestList );
        HttpPost postRequest = new HttpPost( operatorHost );
        InputStream responseInputStream = operatorRestClient.sendRequest( postRequest, xmlRequest );

        KyivstarAcceptanceResponse resultResponse = parseResponse( responseInputStream );
        // update operator message id & statuses
        List<KyivstarAcceptanceStatus> statusList = resultResponse.getStatus();
        for( KyivstarAcceptanceStatus acceptanceStatus : statusList ) {
            for( SmsRequest smsRequest : smsRequestList ) {
                if( smsRequest.getSmsRequestId().equals( Long.valueOf( acceptanceStatus.getClid() ) ) ) {
                    smsRequest.setOperator( operator );
                    smsRequest.setOperatorMessageId( acceptanceStatus.getMid() );
                    smsRequest.setStatus( acceptanceStatus.getValue() );
                }
            }
        }
        return smsRequestList;
    }

    private String buildXmlRequest( List<SmsRequest> smsRequestList ) {
        SmsRequestListWrapper smsRequestListWrapper = new SmsRequestListWrapper( smsRequestList );
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance( SmsRequestListWrapper.class );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            jaxbMarshaller.marshal(smsRequestListWrapper, stringWriter);
            return stringWriter.toString();
        } catch( JAXBException e ) {
            e.printStackTrace();
        }
        return "";
    }

    private KyivstarAcceptanceResponse parseResponse( InputStream responseInputStream ) {
//        KyivstarAcceptanceResponse resultResponse = null;
//        try {
//            JAXBElement<KyivstarAcceptanceResponse> jeResponse = (JAXBElement<KyivstarAcceptanceResponse>)jaxbUnmarshaller.unmarshal( responseInputStream );
//            resultResponse = jeResponse.getValue();
//            return resultResponse;
//        } catch( JAXBException e ) {
//            e.printStackTrace();
//        }
        return null;
    }

    public void setOperatorRestClient( OperatorRestClient operatorRestClient ) {
        this.operatorRestClient = operatorRestClient;
    }

    public void setOperatorHost( String operatorHost ) {
        this.operatorHost = operatorHost;
    }
}
