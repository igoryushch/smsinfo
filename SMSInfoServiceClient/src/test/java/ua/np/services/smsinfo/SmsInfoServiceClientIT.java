package ua.np.services.smsinfo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 04.02.14
 */

@ContextConfiguration
public class SmsInfoServiceClientIT extends AbstractTestNGSpringContextTests{

    @Resource
    @Qualifier(value = "smsInfoClient")
    private SmsInfoService smsInfoClient;

    @Test(enabled = false)
    public void testWSInteraction(){
        Assert.assertNotNull( smsInfoClient );
        String response = smsInfoClient.sendMessages( SmsInfoServiceITUtils.buildRequestStringFromSystem(), "Awis" );
        Assert.assertNotNull( response );
        System.out.println(response);
    }


}
