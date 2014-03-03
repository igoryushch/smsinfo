package ua.np.services.smsinfo;

/**
 * Copyright (C) 2013 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 20.12.13
 */


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(locations = "classpath*:ua/np/services/smsinfo/OperatorInteractionTest-context.xml")
public class KyivstarIntegrationTest extends AbstractTestNGSpringContextTests {

//    @Value( "${kyivstarHost}" )
//    private String operatorHost;
    private String operatorHost = "http://localhost:9090/kyivstar_cpa/send";
    @Value( "${kyivstarHostUser}" )
    private String operatorLogin;
    @Value( "${kyivstarHostPassword}" )
    private String operatorPassword;
    @Autowired
    private ProxySettingsHolder proxySettingsHolder;

    @Test
    public void testMarshalResponse(){
        ObjectFactory objectFactory = new ObjectFactory();

        KyivstarAcceptanceResponse acceptanceResponse = objectFactory.createKyivstarAcceptanceResponse();
        acceptanceResponse.getStatus().add( objectFactory.createStatusType( "84140005", "0000-00001321", "Accepted" ) );
        acceptanceResponse.getStatus().add( objectFactory.createStatusType( "84140006", "0000-00001322", "Accepted" ) );

        JAXBElement<KyivstarAcceptanceResponse> jaxbElement = objectFactory.createKyivstarAcceptanceResponse( acceptanceResponse );

        StringWriter stringWriter = new StringWriter(  );
        // marshalling
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance( KyivstarAcceptanceResponse.class );
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal( jaxbElement, stringWriter );

            String res = stringWriter.toString();
            Assert.assertEquals( res, getExpectedXmlResponse() );

            jc = JAXBContext.newInstance( KyivstarAcceptanceResponse.class, ObjectFactory.class );
            Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
            JAXBElement<KyivstarAcceptanceResponse> jeResponse = (JAXBElement<KyivstarAcceptanceResponse>)jaxbUnmarshaller
                    .unmarshal( new StreamSource( new StringReader( res ) ));
            KyivstarAcceptanceResponse resultResponse = jeResponse.getValue();
            Assert.assertNotNull( resultResponse );

        } catch( JAXBException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        }


    }

    private String getExpectedXmlResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<report xmlns=\"http://goldetele.com/cpa\">" +
                    "<status mid=\"84140005\" clid=\"0000-00001321\">Accepted</status>" +
                    "<status mid=\"84140006\" clid=\"0000-00001322\">Accepted</status>" +
                "</report>";
    }

    @Test
    public void testMarshalRequest(){

        ObjectFactory objectFactory = new ObjectFactory();

        KyivstarSendRequest sendRequest = objectFactory.createKyivstarSendRequest();

        sendRequest.setExpiry( "22.01.2014 12:00:00" );
        sendRequest.setService( "bulk-request" );
        sendRequest.setTid( "1" );
        sendRequest.setPaswd( "sdgf232fsaqa2" );
        sendRequest.setLogin( "newmail" );

        List<KyivstarMessage> messages = new ArrayList<>(  );
        messages.add( new KyivstarMessage( "0000-00001321","380962276147", new KyivstarMessageBody( "First Test Message", "text/plain" ) ) );
        messages.add( new KyivstarMessage( "0000-00001322","380962276147", new KyivstarMessageBody( "Second Test Message", "text/plain" ) ) );

        KyivstarMessagesType messagesType = new KyivstarMessagesType();
        messagesType.setMessage( messages );

        sendRequest.setMessages(messagesType );

        JAXBElement<KyivstarSendRequest> request = objectFactory.createKyivstarSendRequest(sendRequest);

        //acceptance response
        KyivstarAcceptanceResponse acceptanceResponse = new KyivstarAcceptanceResponse();
        acceptanceResponse.getStatus().add( new KyivstarAcceptanceStatus("0000-00001321","84140005","Accepted") );
        acceptanceResponse.getStatus().add( new KyivstarAcceptanceStatus("0000-00001322","84140006","Accepted") );

        StringWriter stringWriter = new StringWriter(  );

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( KyivstarSendRequest.class  );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(request, stringWriter);
            Assert.assertEquals( stringWriter.toString(), getExpectedXmlRequest() );
        } catch( JAXBException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        }

    }

    @Test(enabled = false)
    public void testSendMessage() {
//        HttpClient httpClient = HttpClients.custom()
//                .setDefaultCredentialsProvider(proxySettingsHolder.newProxyCredential()).build();
//
//        HttpHost proxy = new HttpHost(proxySettingsHolder.getProxyHost(), proxySettingsHolder.getProxyPort());
//
//        RequestConfig config = RequestConfig.custom()
//                .setProxy( proxy )
//                .build();
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost( operatorHost );
//        postRequest.setConfig( config );

        try {
            StringEntity entity = new StringEntity( getMultiRequestStringForKyivstarMarshalled() );
            postRequest.addHeader( "Content-Type", "application/xml; charset=UTF-8" );

            postRequest.setEntity( entity );
            HttpResponse response = httpClient.execute( postRequest );

            Assert.assertEquals( response.getStatusLine().getStatusCode(), 200 );
            JAXBContext jc = null;
            try {
                jc = JAXBContext.newInstance( KyivstarAcceptanceResponse.class, ObjectFactory.class );
                Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
                JAXBElement<KyivstarAcceptanceResponse> jeResponse = (JAXBElement<KyivstarAcceptanceResponse>) jaxbUnmarshaller
                        .unmarshal( response.getEntity().getContent() );
                KyivstarAcceptanceResponse resultResponse = jeResponse.getValue();
                Assert.assertNotNull( resultResponse );
                for( KyivstarAcceptanceStatus acceptanceStatus : resultResponse.getStatus() ){
                    Assert.assertNotNull( acceptanceStatus.getMid() );
                    Assert.assertEquals( acceptanceStatus.getValue(), "Accepted" );
                }

            } catch( JAXBException e ) {
                e.printStackTrace();
                Assert.fail( "Exception was thrown!" );
            }

        } catch( IOException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown" );
        }
    }

    private String getMultiRequestStringForKyivstarMarshalled(){
        ObjectFactory objectFactory = new ObjectFactory();

        KyivstarSendRequest sendRequest = objectFactory.createKyivstarSendRequest();

        sendRequest.setExpiry( "22.01.2014 12:00:00" );
        sendRequest.setService( "bulk-request" );
        sendRequest.setTid( "1" );
        sendRequest.setPaswd( "sdgf232fsaqa2" );
        sendRequest.setLogin( "newmail" );

        List<KyivstarMessage> messages = new ArrayList<>(  );
        messages.add( new KyivstarMessage( "0000-00001321","380962276147", new KyivstarMessageBody( "First Test Message", "text/plain" ) ) );
        messages.add( new KyivstarMessage( "0000-00001322","380962276147", new KyivstarMessageBody( "Second Test Message", "text/plain" ) ) );

        KyivstarMessagesType messagesType = new KyivstarMessagesType();
        messagesType.setMessage( messages );

        sendRequest.setMessages(messagesType );

        JAXBElement<KyivstarSendRequest> request = objectFactory.createKyivstarSendRequest(sendRequest);

        StringWriter stringWriter = new StringWriter(  );

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( KyivstarSendRequest.class  );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(request, stringWriter);
            return stringWriter.toString();
        } catch( JAXBException e ) {
            e.printStackTrace();
        }
        return "";
    }

    private String getExpectedXmlRequest(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<root xmlns=\"http://goldentele.com/cpa\">" +
                "<login>newmail</login><paswd>sdgf232fsaqa2</paswd>" +
                "<service>bulk-request</service>" +
                "<expiry>22.01.2014 12:00:00</expiry>" +
                "<tid>1</tid>" +
                "<messages>" +
                "<message>" +
                "<IDint>0000-00001321</IDint>" +
                "<sin>380962276147</sin>" +
                "<body content-type=\"text/plain\">First Test Message</body>" +
                "</message>" +

                "<message>" +
                "<IDint>0000-00001322</IDint>" +
                "<sin>380962276147</sin>" +
                "<body content-type=\"text/plain\">Second Test Message</body>" +
                "</message>" +
                "</messages>" +
                "</root>"
                ;
    }
}