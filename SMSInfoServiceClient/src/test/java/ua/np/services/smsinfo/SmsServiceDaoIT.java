package ua.np.services.smsinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 31.01.14
 */

@ContextConfiguration(locations = "SmsInfoServiceIT-context.xml")
public class SmsServiceDaoIT  extends AbstractTestNGSpringContextTests {

    @Autowired
    private SmsServiceDao smsServiceDao;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testAddRequests() throws Exception {
        List<SmsRequest> requestList = smsServiceDao.addRequests( Arrays.asList(SmsInfoServiceITUtils.getTestRequest()) );
        for(SmsRequest smsRequest : requestList){
            Assert.assertNotNull( smsRequest.getId() );
        }
    }

    @Test
    public void testGetRequestsForSystem() throws Exception {
        Assert.fail( "Not implemented yet!" );

    }

    @Test
    public void testGetMessagesToSend() throws Exception {
        Assert.fail( "Not implemented yet!" );

    }

    @Test
    public void testUpdateStatuses() throws Exception {
        Assert.fail( "Not implemented yet!" );

    }
}
