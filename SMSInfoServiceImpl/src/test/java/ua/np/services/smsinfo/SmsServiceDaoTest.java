package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

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

public class SmsServiceDaoTest {

    private EntityManager mockEntityManager;
    private SmsServiceDaoImpl smsServiceDao;
    private static final int QUERY_MAX_RESULT = 100;

    @BeforeMethod
    public void setUp() {
        // mocks & inits
        mockEntityManager = mock(EntityManager.class);
        smsServiceDao = new SmsServiceDaoImpl();
        smsServiceDao.setEntityManager( mockEntityManager );
    }

    @Test
    public void testAddRequests(){
        List<SmsRequest> requestList = SmsServiceUnitTestSupport.getTestRequestList();
        // logic
        smsServiceDao.addRequests( requestList );
        // verifications
        for( SmsRequest request : requestList ){
            verify( mockEntityManager, times( 1 ) ).persist( request );
        }
//        verifyNoMoreInteractions( mockEntityManager );
    }

    @Test
    public void testGetRequestsForSystem(){
        // mocks & inits
        TypedQuery mockQuery = mock( TypedQuery.class);
        String queryName = "findBySystemName";

        // expectations
        when( mockEntityManager.createNamedQuery( queryName, SmsRequest.class) ).thenReturn( mockQuery );
        when( mockQuery.setParameter( "systemName", "Awis")).thenReturn( mockQuery );
        when( mockQuery.getResultList() ).thenReturn( Collections.<SmsRequest>emptyList() );

        // logic
        List<SmsRequest> result = smsServiceDao.getRequestsForSystem( "Awis" );

        // verifications
        verify(mockEntityManager, times(1)).createNamedQuery( queryName, SmsRequest.class );
        verify(mockQuery, times(1)).setParameter( "systemName", "Awis" );
        verify(mockQuery, times(1)).getResultList();
        verifyNoMoreInteractions(mockEntityManager, mockQuery);

        // assertions
        Assert.assertSame( result, Collections.<SmsRequest>emptyList() );
    }

    @Test
    public void testUpdateStatuses(){
        // mocks & inits
        Query mockQuery = mock(Query.class);
        String queryString = "UPDATE SmsRequest sr SET sr.status = :newStatus WHERE sr.operatorMessageId = :operatorMid AND sr.operator = :operator";

        Operator operator = SmsServiceUnitTestSupport.getTestOperator();
        // expectations
        when( mockEntityManager.createQuery( queryString ) ).thenReturn(mockQuery);
        when( mockQuery.setParameter( "newStatus", "Delivered" )).thenReturn( mockQuery );
        when( mockQuery.setParameter( "operatorMid", "01233213321")).thenReturn( mockQuery );
        when( mockQuery.setParameter( "newStatus", "Error")).thenReturn( mockQuery );
        when( mockQuery.setParameter( "operatorMid", "01233267461")).thenReturn( mockQuery );
        when( mockQuery.setParameter( "operator", operator)).thenReturn( mockQuery );
        when( mockQuery.executeUpdate() ).thenReturn( 1 );
        // logic
        smsServiceDao.updateStatuses( SmsServiceUnitTestSupport.getTestStatusMap(), operator );

        int mapSize = SmsServiceUnitTestSupport.getTestStatusMap().size();
        // verifications
        verify(mockEntityManager, times(mapSize)).createQuery( queryString );
        verify(mockQuery, times(mapSize)).executeUpdate();
    }

    @Test
    public void testGetMessagesToSend() throws Exception {

        TypedQuery mockQuery = mock( TypedQuery.class);
        String queryName = "findPendingRequests";

        // expectations
        when( mockEntityManager.createNamedQuery( queryName, SmsRequest.class) ).thenReturn( mockQuery );
        when( mockQuery.setParameter( "statusPending", "Pending" )).thenReturn( mockQuery );
        when( mockQuery.setMaxResults( QUERY_MAX_RESULT )).thenReturn( mockQuery );
        when( mockQuery.getResultList() ).thenReturn( Collections.<SmsRequest>emptyList() );

        // logic
        smsServiceDao.setMaxSendCount( QUERY_MAX_RESULT );
        List<SmsRequest> result = smsServiceDao.getMessagesToSend();

        // verifications
        verify(mockEntityManager, times(1)).createNamedQuery( queryName, SmsRequest.class );
        verify(mockQuery, times(1)).setParameter( "statusPending", "Pending" );
        verify(mockQuery, times(1)).setMaxResults( QUERY_MAX_RESULT );
        verify(mockQuery, times(1)).getResultList();
        verifyNoMoreInteractions(mockEntityManager, mockQuery);

        // assertions
        Assert.assertSame( result, Collections.<SmsRequest>emptyList() );
    }
}
