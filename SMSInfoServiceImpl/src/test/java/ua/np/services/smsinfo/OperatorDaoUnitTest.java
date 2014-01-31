package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
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
 * Date: 15.01.14
 */

public class OperatorDaoUnitTest {

    private EntityManager mockEntityManager;
    private OperatorDaoImpl operatorDao;
    private TypedQuery mockQuery;

    @BeforeMethod
    public void setUp() {
        // mocks & inits
        mockEntityManager = mock(EntityManager.class);
        operatorDao = new OperatorDaoImpl();
        operatorDao.setEntityManager( mockEntityManager );
        mockQuery = mock( TypedQuery.class);
    }

    @Test
    public void testGetOperatorByPhoneCode(){
        // mocks & inits
        String queryName = "findByPhoneCode";
        Operator expectedOperator = SmsServiceUnitTestSupport.newOperator( "Life" );

        // expectations
        when( mockEntityManager.createNamedQuery( queryName, Operator.class) ).thenReturn( mockQuery );
        when( mockQuery.setParameter( "code", "063")).thenReturn( mockQuery );
        when( mockQuery.getResultList() ).thenReturn( Arrays.asList( expectedOperator ) );

        Operator actualOperator = operatorDao.getOperatorByPhoneCode( "063" );

        // verifications
        verify(mockEntityManager, times(1)).createNamedQuery( queryName, Operator.class );
        verify(mockQuery, times(1)).setParameter( "code", "063" );
        verify(mockQuery, times(1)).getResultList();
        verifyNoMoreInteractions(mockEntityManager, mockQuery);

        // assertions
        Assert.assertEquals( actualOperator.getName(), expectedOperator.getName() );
        Assert.assertNotNull( actualOperator.getPhoneCodeMaping());
    }

    @Test
    public void testGetDefaultOperator(){
        // mocks & inits
        String queryName = "findByPhoneCode";
        Operator expectedOperator = SmsServiceUnitTestSupport.newOperator( "Life" );

        // expectations
        when( mockEntityManager.createNamedQuery( queryName, Operator.class) ).thenReturn( mockQuery );
        when( mockQuery.setParameter( "code", "000")).thenReturn( mockQuery );
        when( mockQuery.getResultList() ).thenReturn( Arrays.asList( expectedOperator ) );

        Operator actualOperator = operatorDao.getDefaultOperator();

        // verifications
        verify(mockEntityManager, times(1)).createNamedQuery( queryName, Operator.class );
        verify(mockQuery, times(1)).setParameter( "code", "000" );
        verify(mockQuery, times(1)).getResultList();
        verifyNoMoreInteractions(mockEntityManager, mockQuery);

        // assertions
        Assert.assertEquals( actualOperator.getName(), expectedOperator.getName() );
        Assert.assertNotNull( actualOperator.getPhoneCodeMaping());
    }

    @Test
    public void testGetOperatorByName() throws Exception {
        // mocks & inits
        String queryName = "findByName";
        Operator expectedOperator = SmsServiceUnitTestSupport.newOperator( "Life" );

        // expectations
        when( mockEntityManager.createNamedQuery( queryName, Operator.class) ).thenReturn( mockQuery );
        when( mockQuery.setParameter( "name", expectedOperator.getName().toLowerCase() )).thenReturn( mockQuery );
        when( mockQuery.getResultList() ).thenReturn( Arrays.asList( expectedOperator ) );

        // logic
        Operator actualOperator = operatorDao.getOperatorByName( expectedOperator.getName() );

        // verifications
        verify(mockEntityManager, times(1)).createNamedQuery( queryName, Operator.class );
        verify(mockQuery, times(1)).setParameter( "name", expectedOperator.getName().toLowerCase() );
        verify(mockQuery, times(1)).getResultList();
        verifyNoMoreInteractions(mockEntityManager, mockQuery);

        // assertions
        Assert.assertEquals( actualOperator.getName(), expectedOperator.getName() );
    }

    @Test
    public void testFindAll() throws Exception {
        String queryParam = "from " + Operator.class.getName();
        // expectations
        when(mockEntityManager.createQuery(queryParam)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn( Collections.<Operator>emptyList());

        // logic
        List<Operator> operatorList = operatorDao.findAll();

        // verifications
        verify(mockEntityManager, times(1)).createQuery(queryParam);
        verify(mockQuery, times(1)).getResultList();
        verifyNoMoreInteractions(mockEntityManager, mockQuery);

        // assertions
        Assert.assertSame(operatorList, Collections.<Operator>emptyList());
    }
}
