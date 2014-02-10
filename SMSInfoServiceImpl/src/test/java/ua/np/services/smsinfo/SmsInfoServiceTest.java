package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

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

public class SmsInfoServiceTest {

    private SmsInfoServiceImpl smsInfoService;
    private SmsServiceUtils smsServiceUtils;
    private SmsService smsService;

    @BeforeMethod
    public void setUp() {
        smsService = mock( SmsService.class);
        smsServiceUtils = mock( SmsServiceUtils.class );
        smsInfoService = spy( new SmsInfoServiceImpl() );
        smsInfoService.setSmsService( smsService );
        smsInfoService.setSmsServiceUtils( smsServiceUtils );
    }

    @Test
    public void testSendMessages(){
        // init
        String systemRequest = SmsServiceUnitTestSupport.getInternalTestRequest();
        List<SmsRequest> requestList = SmsServiceUnitTestSupport.getTestRequestList();

        when( smsServiceUtils.getRequestsFromXmlString( systemRequest,"Awis" ) ).thenReturn( requestList );
        doNothing().when( smsService ).saveRequests( requestList );
        when( smsServiceUtils.buildAcceptedResponse( requestList ) ).thenReturn( SmsServiceUnitTestSupport.buildAcceptedResponseTest() );

        //logic
        String response = smsInfoService.sendMessages( systemRequest,"Awis" );

        // verifications
        verify( smsService, times( 1 ) ).saveRequests( requestList );
        verifyNoMoreInteractions( smsService );

        // assertions
        Assert.assertNotNull(response, "call of sendMessages returned null");
        Assert.assertTrue( response.contains( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Array><Structure>" ) );
        for( SmsRequest request : requestList ){
            Assert.assertTrue( response.contains( "<Value name=\"IDInternal\"><Type>String</Type>" ));
            Assert.assertTrue( response.contains( "<Value name=\"IDIncoming\"><Type>String</Type><Data>" + request.getIncomingId() + "</Data></Value>" ) );
        }
    }

    @Test
    public void testGetDeliveryStatusData(){
        List<SmsRequest> requestList = SmsServiceUnitTestSupport.getTestRequestList();
        when( smsService.getRequestsForSystem( "Awis" ) ).thenReturn( requestList );

        //logic
        String response = smsInfoService.reportDeliveryData( "Awis" );

        // verifications
        verify( smsService, times( 1 ) ).getRequestsForSystem( "Awis" );
    }

//    @Test
//    public void testUpdateStatuses(){
//        Map<String, String> testData = SmsServiceUnitTestSupport.getTestStatusMap();
//        smsInfoService.updateStatuses( testData, null );
//        verify( mockSmsServiceDao, times( 1 ) ).updateStatuses( testData );
//
//        Operator testOperator = SmsServiceUnitTestSupport.getTestOperator();
//        smsInfoService.updateStatuses( testData,testOperator  );
//        verify( mockSmsServiceDao, times( 1 ) ).updateStatuses( testData, testOperator );
//    }

}