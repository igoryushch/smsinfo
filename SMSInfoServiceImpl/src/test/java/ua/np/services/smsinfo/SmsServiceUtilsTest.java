package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 10.01.14
 */

public class SmsServiceUtilsTest {

    private SmsServiceUtils smsServiceUtils;
    private OperatorDao mockedOperatorDao;

    @BeforeMethod
    public void setUp() {
        smsServiceUtils = new SmsServiceUtils();
        mockedOperatorDao = mock( OperatorDao.class );
        smsServiceUtils.setOperatorDao( mockedOperatorDao );
    }

    @Test
    public void testGetRequestsFromXmlString() {

        String requestString = SmsServiceUnitTestSupport.getInternalTestRequestForUtils();
        List<SmsRequest> expectedResultList = SmsServiceUnitTestSupport.getExpectedRequestList();
        List<SmsRequest> resultList = smsServiceUtils.getRequestsFromXmlString( requestString, "1C" );

        Assert.assertEquals( resultList.size(),expectedResultList.size(), "Different size of expected and actual request list" );
        for( int i = 0; i < resultList.size(); i++ ){
            SmsRequest request = resultList.get( i );
            SmsRequest expected = expectedResultList.get( i );
            Assert.assertEquals( request.getIncomingId(),expected.getIncomingId() );
            Assert.assertEquals( request.getPhoneNumber(),expected.getPhoneNumber() );
            Assert.assertEquals( request.getMessageText(),expected.getMessageText() );
        }

    }

    @DataProvider(name = "requestParamsProvider")
    public static Object[][] requestParams(){
        return new Object[][] { { "111111", "1C", "063", "380631234567", "Foo"}, { "222222", "1C","067", "380671234567", "Bar" },
                                { "333333", "Awis","068", "380681234567", "FooBar" },{ "444444", "Awis","069", "380691234567", "BarFoo" },
                                { "555555", "loyalty","095", "380951234567", "Foo" },{ "666666", "loyalty","096", "380961234567", "Foo" } };
    }

    @Test(dataProvider = "requestParamsProvider")
    public void testBuildNewSmsRequest(String incomingId, String systemName, String phoneCode, String phoneNumber, String messageText){

        Map<String, String> params = new HashMap<>(  );
        params.put( "id", incomingId );
        params.put( "phone", phoneNumber );
        params.put( "text", messageText );

        SmsRequest request = smsServiceUtils.buildNewSmsRequest( params, systemName );
        Assert.assertEquals( request.getIncomingId(), incomingId );
        Assert.assertEquals( request.getSystemName(), systemName );
        Assert.assertEquals( request.getPhoneNumber(), phoneNumber );
        Assert.assertEquals( request.getMessageText(), messageText );
        Assert.assertNotNull( request.getCreationDate() );
        Assert.assertNotNull( request.getStatus() );
        Assert.assertNotNull( request.getUpdateDate() );
    }

    @Test
    public void testGetInitialStatus(){
        Assert.assertEquals( smsServiceUtils.getInitialStatus(), "Pending" );
    }

    @Test
    public void testBuildAcceptedResponse(){
        Assert.assertEquals( smsServiceUtils.buildAcceptedResponse( SmsServiceUnitTestSupport.getTestSmsRequestList() ),
                SmsServiceUnitTestSupport.getExpectedAcceptanceReport( "1010101" ) );
    }

    @Test
    public void testGetMessageParams(){
        String requestString = SmsServiceUnitTestSupport.getInternalTestRequestForUtils();
        List<Map<String, String>> result = smsServiceUtils.getMessageParams( requestString );
        Assert.assertTrue( result.size() == 2 );
        SmsServiceUnitTestSupport.checkAssertions( result.get( 0 ), "11111111111", "FooBar", "0661234567" );
        SmsServiceUnitTestSupport.checkAssertions( result.get( 1 ), "121222222222", "BarFoo", "0671234567" );
    }

    @Test
    public void testBuildDeliveryStatusResponse(){
        Assert.assertEquals( smsServiceUtils.buildDeliveryStatusResponse( SmsServiceUnitTestSupport.getTestSmsRequestList() ),
                SmsServiceUnitTestSupport.getExpectedBuildDeliveryStatusResponse( "1010101", "Delivered" ) );
    }

    @DataProvider(name = "phoneNumbersProvider")
    public static Object[][] getPhones(){
        return new Object[][] { { "+380661234567", "066"}, { "380661234567", "066" },
                                { "80661234567", "066" },{ "0661234567", "066" },};
    }

    @Test(dataProvider = "phoneNumbersProvider")
    public void testGetPhoneCodeFromNumber(String phoneNumber, String expectedCode){
        Assert.assertEquals( smsServiceUtils.getPhoneCodeFromNumber( phoneNumber ), expectedCode );
    }


}
