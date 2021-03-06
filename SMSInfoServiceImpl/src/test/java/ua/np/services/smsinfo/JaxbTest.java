package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 25.02.14
 */


public class JaxbTest {

    @Test
    public void testSmsRequestListWrapper() throws Exception {
        List<SmsRequest> smsRequests = SmsServiceUnitTestSupport.getTestRequestList();
        for( int i = 0; i < smsRequests.size(); i++ ) {
            smsRequests.get( i ).setSmsRequestId( 10000 + i + 1L );
        }
        SmsRequestListWrapper smsRequestListWrapper = new SmsRequestListWrapper( smsRequests );
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance( SmsRequestListWrapper.class );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            File file = new File("F:\\Java\\IdeaProjects\\repositories-root\\services\\SMSInfo\\SMSInfoServiceImpl\\src\\test\\resources\\test.xml");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter stringWriter = new StringWriter();
            //Marshal the employees list in console
            jaxbMarshaller.marshal(smsRequestListWrapper, file);

        } catch( JAXBException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        }
    }
}
