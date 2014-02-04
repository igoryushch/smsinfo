package ua.np.services.smsinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 30.01.14
 */

@ContextConfiguration
public class SmsInfoServiceIT extends AbstractTestNGSpringContextTests{

    @Autowired
    private SmsInfoService smsInfoService;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testConfiguration() throws Exception {
        Assert.assertNotNull( applicationContext.getBean( "smsInfoServiceWsServer" ) );
        Assert.assertNotNull( applicationContext.getBean( "transactionManager" ) );
        Assert.assertNotNull( smsInfoService );
        Assert.assertNotNull( entityManager );
    }

    @Test
    public void testSendMessages(){
        String response = smsInfoService.sendMessages( SmsInfoServiceITUtils.buildRequestStringFromSystem(),"Awis" );
        flushAndClear();
        Assert.assertNotNull( response );
        Assert.assertEquals( response, SmsInfoServiceITUtils.getExpectedResponse() );
        testGetDeliveryStatusData();
        testOperatorInteraction();
    }

    public void testGetDeliveryStatusData(){
        String response = smsInfoService.getDeliveryStatusData( "Awis" );
        Assert.assertEquals( response, SmsInfoServiceITUtils.getStatusDataResponseString() );
    }

    public void testOperatorInteraction(){
        SmsSendingJob sendingJob = applicationContext.getBean( "smsSendingJob",SmsSendingJob.class );
        sendingJob.doSend();
    }

    public void testUpdateStatuses(){
        updateOperatorDataInSms();
    }

    public void updateOperatorDataInSms(){

    }

    void flushAndClear() {
        if (entityManager != null) {
            entityManager.flush();
            entityManager.clear();
        }
    }
}
