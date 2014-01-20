package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

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
        verifyNoMoreInteractions( mockEntityManager );
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
        String queryString = "UPDATE SmsRequest sr SET sr.status = :newStatus WHERE sr.operatorMessageId IN :idList";

        // expectations
        when(mockEntityManager.createQuery(queryString)).thenReturn(mockQuery);

        // logic
        smsServiceDao.updateStatuses( getTestStatusMap() );

        // verifications
        verify(mockEntityManager, times(1)).createQuery(queryString);
        verify(mockQuery, times(1)).executeUpdate();
        verifyNoMoreInteractions(mockEntityManager, mockQuery);
    }

    private Map<String, String> getTestStatusMap() {

        Map<String, String> result = new HashMap<>(  );
        result.put( "01233213321", "Sended" );
        result.put( "01233267461", "Error" );

        return result;
    }


}
