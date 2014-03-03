package ua.np.services.smsinfo;

import java.util.*;

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

public class SmsInfoServiceITUtils {

    public static SmsRequest getTestRequest(){
        return new SmsRequest( "0001","Awis","0671234567","FooBar",
                new GregorianCalendar(  ),new GregorianCalendar(  ),
                "Pending",null,"321321312");
    }

    public static String getStatusDataResponseString(){
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Structure>" +
                "<Value name=\"ChangesUUID\"><Type>String</Type><Data>null</Data></Value>" +
                "<ValueTable name=\"StatusData\"><Columns><Column><Name>IdInternal</Name></Column>" +
                "<Column><Name>CurrentStatus</Name></Column></Columns><Rows><Row><Value name=\"IdInternal\">" +
                "<Type>String</Type><Data>03213210321</Data></Value><Value name=\"CurrentStatus\"><Type>String</Type>" +
                "<Data>Pending</Data></Value></Row><Row><Value name=\"IdInternal\"><Type>String</Type><Data>23211654321</Data></Value>" +
                "<Value name=\"CurrentStatus\"><Type>String</Type><Data>Pending</Data></Value></Row></Rows></ValueTable></Structure>";
        return result;
    }

    public static String buildRequestStringFromSystem(){
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Structure>" +
                "    <Value name=\"operation\">" +
                "        <Type>String</Type>" +
                "        <Data>sendMessages</Data>" +
                "    </Value>" +
                "<Array name=\"messageArray\">" +
                "<Structure>" +
                "<Value name=\"id\">" +
                "<Type>String</Type>" +
                "<Data>03213210321</Data>" +
                "</Value>" +
                "<Value name=\"phone\">" +
                "<Type>String</Type>" +
                "<Data>0955942730</Data>" +
                "</Value>" +
                "<Value name=\"text\">" +
                "<Type>String</Type>" +
                "<Data>FooBar</Data>" +
                "</Value>" +
                "</Structure>" +
                "<Structure>" +
                "<Value name=\"id\">" +
                "<Type>String</Type>" +
                "<Data>23211654321</Data>" +
                "</Value>" +
                "<Value name=\"phone\">" +
                "<Type>String</Type>" +
                "<Data>0955942730</Data>" +
                "</Value>" +
                "<Value name=\"text\">" +
                "<Type>String</Type>" +
                "<Data>BarFoo</Data>" +
                "</Value>" +
                "</Structure>" +
                "</Array>" +
                "</Structure>";

        return request;
    }

    public static String getExpectedResponse(){
        String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<Array><Structure><Value name=\"IDIncoming\"><Type>String</Type><Data>03213210321</Data></Value>" +
                "<Value name=\"IDInternal\"><Type>String</Type><Data>1</Data></Value>" +
                "<Value name=\"IDIncoming\"><Type>String</Type><Data>23211654321</Data></Value>" +
                "<Value name=\"IDInternal\"><Type>String</Type><Data>2</Data></Value></Structure></Array>";

        return response;
    }

    public static List<SmsRequest> getTestRequestList(){
        List<SmsRequest> result = new ArrayList<SmsRequest>(  );
        for( int i = 1; i < 6;i++ ){
            result.add( new SmsRequest( "000"+i,"Awis","0671234567","FooBar",
                    new GregorianCalendar(  ),new GregorianCalendar(  ),
                    "Pending",newOperator( "Life:)" ),"321321312") );
        }
        return result;
    }

    public static Operator newOperator( String name ){
        Set<String> codes = new HashSet<String>(  );
        codes.add( "066" );
        codes.add( "095" );
        return new Operator( name, codes );
    }

}
