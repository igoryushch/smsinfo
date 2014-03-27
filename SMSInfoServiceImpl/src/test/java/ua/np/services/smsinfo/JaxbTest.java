package ua.np.services.smsinfo;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
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

    @Test(enabled = false)
    public void testSmsRequestListWrapperMarshall() throws Exception {
        List<SmsRequest> smsRequests = SmsServiceUnitTestSupport.getTestRequestList();
        for( int i = 0; i < smsRequests.size(); i++ ) {
            smsRequests.get( i ).setSmsRequestId( 10000 + i + 1L );
        }
        SmsRequestListWrapper smsRequestListWrapper = new SmsRequestListWrapper( smsRequests );
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance( SmsRequestListWrapper.class );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter stringWriter = new StringWriter();
            jaxbMarshaller.marshal(smsRequestListWrapper, System.out);

        } catch( JAXBException e ) {
            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        }
    }

    @Test(enabled = true)
    public void testSmsRequestListWrapperUnmarshall() throws Exception {
        String xmlString = "<smsRequestListWrapper><smsRequest xmlns:ns2=\"http://goldetele.com/cpa\">\n" +
                "    <smsRequestId>3</smsRequestId>\n" +
                "    <incomingId>03213210321</incomingId>\n" +
                "    <systemName>Awis</systemName>\n" +
                "    <phoneNumber>380962276147</phoneNumber>\n" +
                "    <messageText>FooBar</messageText>\n" +
                "    <creationDate>2014-03-24T16:23:04.399+02:00</creationDate>\n" +
                "    <updateDate>2014-03-24T16:23:04.399+02:00</updateDate>\n" +
                "    <status>Pending</status>\n" +
                "    <operator>\n" +
                "        <name>Kyivstar</name>\n" +
                "        <phoneCodeMaping>067</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>039</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>068</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>096</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>098</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>097</phoneCodeMaping>\n" +
                "    </operator>\n" +
                "</smsRequest><smsRequest xmlns:ns2=\"http://goldetele.com/cpa\">\n" +
                "    <smsRequestId>4</smsRequestId>\n" +
                "    <incomingId>23211654321</incomingId>\n" +
                "    <systemName>Awis</systemName>\n" +
                "    <phoneNumber>380962276147</phoneNumber>\n" +
                "    <messageText>BarFoo</messageText>\n" +
                "    <creationDate>2014-03-24T16:23:04.399+02:00</creationDate>\n" +
                "    <updateDate>2014-03-24T16:23:04.399+02:00</updateDate>\n" +
                "    <status>Pending</status>\n" +
                "    <operator>\n" +
                "        <name>Kyivstar</name>\n" +
                "        <phoneCodeMaping>067</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>039</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>068</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>096</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>098</phoneCodeMaping>\n" +
                "        <phoneCodeMaping>097</phoneCodeMaping>\n" +
                "    </operator>\n" +
                "</smsRequest></smsRequestListWrapper>";

        JAXBContext jc = null;
        List<SmsRequest> result = new ArrayList<>(  );
        try {
            jc = JAXBContext.newInstance( SmsRequestListWrapper.class );
            Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
            SmsRequestListWrapper requestListWrapper = (SmsRequestListWrapper) jaxbUnmarshaller.unmarshal( new StreamSource( new StringReader( xmlString ) ) );
            Assert.assertNotNull( requestListWrapper );
            result = requestListWrapper.getRequestList();
            Assert.assertEquals( result.size(),2 );
        } catch( JAXBException e ) {

            e.printStackTrace();
            Assert.fail( "Exception was thrown!" );
        }

    }


}
