package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.mockito.Mockito.spy;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 27.01.14
 */

public class KyivstarSendingStrategyUnitTest {

    private KyivstarSmsSendingStrategy smsSendingStrategy;
    private OperatorRestClient operatorRestClient;

    @BeforeMethod
    public void setUp() {
        smsSendingStrategy = spy( new KyivstarSmsSendingStrategy() );
        smsSendingStrategy.setOperatorHost( "testHost" );
        smsSendingStrategy.setOperatorLogin( "testLogin" );
        smsSendingStrategy.setOperatorPassword( "testPassword" );

        this.operatorRestClient = new OperatorRestClientStub();
        smsSendingStrategy.setOperatorRestClient( operatorRestClient );

        Unmarshaller jaxbUnmarshaller = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( KyivstarAcceptanceResponse.class );

            jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        } catch( JAXBException e ) {
            e.printStackTrace();
        }

        smsSendingStrategy.setJaxbUnmarshaller( jaxbUnmarshaller );
    }

    @Test
    public void testSend(){

        List<SmsRequest> actualList = smsSendingStrategy.send( SmsServiceUnitTestSupport.getTestRequestListWithIds() );
        Assert.assertTrue( actualList.size() == 5 );
        for( SmsRequest smsRequest : actualList ){
            Assert.assertNotNull( smsRequest.getOperatorMessageId(), "Operator message id was null" );
            Assert.assertEquals( smsRequest.getStatus(), "Accepted" );
        }
    }

    @Test
    public void testParseResponse(){
        try {
            Method method = KyivstarSmsSendingStrategy.class.getDeclaredMethod( "parseResponse", InputStream.class );
            method.setAccessible(true);
            KyivstarAcceptanceResponse acceptanceResponse = (KyivstarAcceptanceResponse) method.invoke(smsSendingStrategy, getTestInputStream());
            Assert.assertNotNull( acceptanceResponse );
            List<KyivstarAcceptanceStatus> statusList = acceptanceResponse.getStatus();
            for( KyivstarAcceptanceStatus acceptanceStatus : statusList ){
                Assert.assertEquals( acceptanceStatus.getValue(), "Accepted" );
            }
        } catch( NoSuchMethodException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        } catch( InvocationTargetException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        }
    }

    @Test void testBuildXmlRequest(){
        List<SmsRequest> requestList = SmsServiceUnitTestSupport.getTestRequestList();

        try {
            Method method = KyivstarSmsSendingStrategy.class.getDeclaredMethod("buildXmlRequest", List.class);
            method.setAccessible(true);
            String output = (String) method.invoke(smsSendingStrategy, requestList);
            Assert.assertTrue( output.contains( "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root xmlns=\"http://goldentele.com/cpa\">" ) );
            for( SmsRequest smsRequest : requestList ){
                String expected = "<message><IDint>" + smsRequest.getId() + "</IDint><sin>" + smsRequest.getPhoneNumber() +
                        "</sin><body content-type=\"text/plain\">" + smsRequest.getMessageText() + "</body></message>";
                Assert.assertTrue( output.contains( expected ) );
            }
        } catch( NoSuchMethodException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        } catch( InvocationTargetException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        } catch( IllegalAccessException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        }
    }

    private InputStream getTestInputStream() {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<report xmlns=\"http://goldetele.com/cpa\">" +
                    "<status mid=\"84140005\" clid=\"0000-00001321\">Accepted</status>" +
                    "<status mid=\"84140006\" clid=\"0000-00001322\">Accepted</status>" +
                "</report>";
        return new ByteArrayInputStream(str.getBytes());
    }
}