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

    private SmsServiceDao mockSmsServiceDao;
    private SmsInfoServiceImpl smsInfoService;
    private SmsServiceUtils smsServiceUtils;

    @BeforeMethod
    public void setUp() {
        mockSmsServiceDao = mock( SmsServiceDao.class );
        smsServiceUtils = mock( SmsServiceUtils.class );
        smsInfoService = spy( new SmsInfoServiceImpl() );
        smsInfoService.setSmsServiceDao( mockSmsServiceDao );
        smsInfoService.setSmsServiceUtils( smsServiceUtils );
    }

    @Test
    public void testSendMessages(){
        // init
        String systemRequest = SmsServiceUnitTestSupport.getInternalTestRequest();
        List<SmsRequest> requestList = SmsServiceUnitTestSupport.getTestRequestList();

        when( smsServiceUtils.getRequestsFromXmlString( systemRequest,"Awis" ) ).thenReturn( requestList );
        when( smsServiceUtils.buildAcceptedResponse( requestList ) ).thenReturn( SmsServiceUnitTestSupport.buildAcceptedResponseTest() );

        //logic
        String response = smsInfoService.sendMessages( systemRequest,"Awis" );

        // verifications
        verify( mockSmsServiceDao, times( 1 ) ).addRequests( requestList );
        verifyNoMoreInteractions( mockSmsServiceDao );

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
        when( mockSmsServiceDao.getRequestsForSystem( "Awis" ) ).thenReturn( requestList );

        //logic
        String response = smsInfoService.getDeliveryStatusData( "Awis" );

        // verifications
        verify( mockSmsServiceDao, times( 1 ) ).getRequestsForSystem( "Awis" );

        // assertions
        Assert.assertNotNull(response, "call of getDeliveryStatusData returned null");
        Assert.assertTrue( response.contains( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Array><Structure>" ) );
        for( SmsRequest request : requestList ){
            Assert.assertTrue( response.contains( "<Row><Value name=\"IdInternal\"><Type>String</Type>" ));
            Assert.assertTrue( response.contains( "<Value name=\"CurrentStatus\"><Type>String</Type><Data>" + request.getIncomingId() + "</Data></Value></Row>" ) );
        }
    }

}