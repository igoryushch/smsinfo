package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.spy;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 23.01.14
 */


public class LifeSendingStrategyUnitTest {

    private LifeSmsSendingStrategy smsSendingStrategy;
    private OperatorRestClient operatorRestClient;

    @BeforeMethod
    public void setUp() {
        smsSendingStrategy = spy( new LifeSmsSendingStrategy() );
        smsSendingStrategy.setOperatorHost( "testHost" );
        smsSendingStrategy.setOperatorAuthHost( "testAuthHost" );
        smsSendingStrategy.setOperatorLogin( "testLogin" );
        smsSendingStrategy.setOperatorPassword( "testPassword" );

        this.operatorRestClient = new OperatorRestClientStub();
        smsSendingStrategy.setOperatorRestClient( operatorRestClient );
    }

    @Test
    public void testSend(){

        List<SmsRequest> actualList = smsSendingStrategy.send( SmsServiceUnitTestSupport.getTestRequestList(),SmsServiceUnitTestSupport.getTestOperator() );
        Assert.assertTrue( actualList.size() == 5 );
        for( SmsRequest smsRequest : actualList ){
            Assert.assertNotNull( smsRequest.getOperatorMessageId(), "Operator message id was null" );
            Assert.assertEquals( smsRequest.getStatus(), "Accepted" );
        }
    }

    @Test
    public void testGetServiceType(){

        try {
            Method method = LifeSmsSendingStrategy.class.getDeclaredMethod("getServiceType", int.class);
            method.setAccessible(true);
            String output = (String) method.invoke(smsSendingStrategy, 2);
            Assert.assertEquals( output, "individual" );
            output = (String) method.invoke(smsSendingStrategy, 1);
            Assert.assertEquals( output, "single" );
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

    @Test
    public void testGenerateUniqueKey(){
        try {
            Method method = LifeSmsSendingStrategy.class.getDeclaredMethod("generateUniqueKey");
            method.setAccessible(true);
            String output = (String) method.invoke(smsSendingStrategy);
            Assert.assertNotNull( output );
            Assert.assertTrue( output.length() > 0 );
            output = (String) method.invoke(smsSendingStrategy);
            Assert.assertNotNull( output );
            Assert.assertTrue( output.length() > 0 );
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

    @Test
    public void testBuildXmlRequest(){
        List<SmsRequest> requestList = SmsServiceUnitTestSupport.getTestRequestList();

        try {
            Method method = LifeSmsSendingStrategy.class.getDeclaredMethod("buildXmlRequest", List.class);
            method.setAccessible(true);
            String output = (String) method.invoke(smsSendingStrategy, requestList);
            Assert.assertTrue( output.contains( "<message><service id=\"individual\" validity=\"+5 hour\" source = \"NovaPoshta\"" ) );
            Assert.assertTrue( output.contains( "<to>0671234567</to><body content-type=\"text/plain\">FooBar</body>" ) );
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

    @Test
    public void testParseResponseStatuses(){
        try {
            Method method = LifeSmsSendingStrategy.class.getDeclaredMethod("parseResponseStatuses", InputStream.class);
            method.setAccessible(true);
            Map<String, String> output = (Map<String, String>) method.invoke(smsSendingStrategy, getTestInputStream());
            Assert.assertTrue( output.containsKey( "700068872" ) );
            Assert.assertTrue( output.containsKey( "700068873" ) );
            Assert.assertEquals( output.get( "700068872" ),"Accepted" );
            Assert.assertEquals( output.get( "700068873" ),"Accepted" );
            Assert.assertTrue( output.size() == 2 );
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
        String str = "<status date='Thu, 23 Jan 2014 10:18:02 +0200' groupid='2716196'><id>700068872</id><state>Accepted</state><id>700068873</id><state>Accepted</state></status>";
        return new ByteArrayInputStream(str.getBytes());
    }

}
