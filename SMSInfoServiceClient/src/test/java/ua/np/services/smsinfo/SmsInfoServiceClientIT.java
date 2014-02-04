package ua.np.services.smsinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

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
public class SmsInfoServiceClientIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private SmsInfoService smsInfoClient;

    @Test
    public void testWSInteraction(){

    }

}
