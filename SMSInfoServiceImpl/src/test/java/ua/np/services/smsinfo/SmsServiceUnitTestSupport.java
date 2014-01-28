package ua.np.services.smsinfo;

import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

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

public class SmsServiceUnitTestSupport {

    public static SmsRequest getTestRequest(){
        return new SmsRequest( "0001","Awis","0671234567","FooBar",
                new GregorianCalendar(  ),new GregorianCalendar(  ),
                "Pending",newOperator( "Life:)" ),"321321312","");
    }

    public static List<SmsRequest> getTestRequestList(){
        List<SmsRequest> result = new ArrayList<SmsRequest>(  );
        for( int i = 1; i < 6;i++ ){
            result.add( new SmsRequest( "000"+i,"Awis","0671234567","FooBar",
                    new GregorianCalendar(  ),new GregorianCalendar(  ),
                    "Pending",newOperator( "Life:)" ),"321321312","") );
        }
        return result;
    }

    public static List<SmsRequest> getTestRequestListWithIds(){
        List<SmsRequest> result = getTestRequestList();
        Long i = 1L;
        for( SmsRequest smsRequest : result ){
            smsRequest.setId( i );
            i++;
        }
        return result;
    }

    public static Operator newOperator( String name ){
        Set<String> codes = new HashSet<String>(  );
        codes.add( "066" );
        codes.add( "095" );
        return new Operator( name, codes );
    }

    public static String getInternalTestRequest(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    }

    public static String getInternalTestRequestForUtils(){
        
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Structure>" +
                "<Value name=\"operation\">" +
                "<Type>String</Type>" +
                "<Data>sendMessages</Data>" +
                "</Value>" +
                "<Array name=\"messageArray\">" +
                "<Structure>" +
                "<Value name=\"id\">" +
                "<Type>String</Type>" +
                "<Data>11111111111</Data>" +
                "</Value>" +
                "<Value name=\"phone\">" +
                "<Type>String</Type>" +
                "<Data>0661234567</Data>" +
                "</Value>" +
                "<Value name=\"text\">" +
                "<Type>String</Type>" +
                "<Data>FooBar</Data>" +
                "</Value>" +
                "</Structure>" +
                "<Structure>" +
                "<Value name=\"id\">" +
                "<Type>String</Type>" +
                "<Data>121222222222</Data>" +
                "</Value>" +
                "<Value name=\"phone\">" +
                "<Type>String</Type>" +
                "<Data>0671234567</Data>" +
                "</Value>" +
                "<Value name=\"text\">" +
                "<Type>String</Type>" +
                "<Data>BarFoo</Data>" +
                "</Value>" +
                "</Structure>" +
                "</Array>" +
                "</Structure>";
        
        return str;
    }

    public static String buildAcceptedResponseTest(){

        StringBuilder sb = new StringBuilder(  );
        sb.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Array><Structure>" );
        for( int i = 1; i < 6;i++ ){
            sb.append( "<Value name=\"IDIncoming\"><Type>String</Type><Data>" + "000"+i + "</Data></Value><Value name=\"IDInternal\"><Type>String</Type><Data>321321312</Data></Value>");
        }
        sb.append( "</Structure></Array>" );

        return sb.toString();
    }

    public static List<SmsRequest> getExpectedRequestList(){

        return Arrays.asList(
                new SmsRequest( "11111111111", "1C", "0661234567", "FooBar",
                                new GregorianCalendar(), new GregorianCalendar(),
                                "Pending", newOperator( "Life:)" ), "321321312","" ),
                new SmsRequest( "121222222222", "1C", "0671234567", "BarFoo",
                                new GregorianCalendar(), new GregorianCalendar(),
                                "Pending", newOperator( "Life:)" ), "321321313","" ) );
    }

    private static String getXmlAsString(String filePath){

        try {
            Scanner scanner = new Scanner( new FileInputStream(new File(filePath)) );
            StringBuilder sb = new StringBuilder(  );
            while( scanner.hasNext() ){
                sb.append( scanner.nextLine().replaceAll( "", "" ).replaceAll( "\r", "" ).replaceAll( "", "" ) );
            }
            return sb.toString();

        } catch( Exception e ) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<SmsRequest> getTestSmsRequestList(){
        return Arrays.asList(
                new SmsRequest( "1010101", "1C", "0991112233", "FooBar",
                        new GregorianCalendar(  ), new GregorianCalendar(  ), "Pending",newOperator( "Life:)" ),"","Delivered")
        );
    }

    public static String getExpectedAcceptanceReport(String incomingId){
        StringBuilder sb = new StringBuilder(  );
        sb.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Array><Structure>" );
        sb.append( "<Value name=\"IDIncoming\"><Type>String</Type><Data>" + incomingId + "</Data></Value><Value name=\"IDInternal\"><Type>String</Type><Data>null</Data></Value>");
        sb.append( "</Structure></Array>" );

        return sb.toString();
    }

    public static String getExpectedStatusResponseForSystem(String expectedInternalId, String expectedStatus){
        StringBuilder sb = new StringBuilder(  );
        sb.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Structure><Value name=\"ChangesUUID\"><Type>String</Type><Data>null</Data></Value><ValueTable name=\"StatusData\"><Columns><Column><Name>IdInternal</Name></Column><Column><Name>CurrentStatus</Name></Column></Columns><Rows>" );
        sb.append( "<Row><Value name=\"IdInternal\"><Type>String</Type><Data>" + expectedInternalId + "</Data></Value><Value name=\"CurrentStatus\"><Type>String</Type><Data>" + expectedStatus + "</Data></Value></Row>");
        sb.append( "</Rows></ValueTable></Structure>" );
        return sb.toString();
    }

    public static String getExpectedBuildDeliveryStatusResponse(String incomingId, String status){
        StringBuilder sb = new StringBuilder(  );
        sb.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Structure><Value name=\"ChangesUUID\"><Type>String</Type><Data>null</Data></Value><ValueTable name=\"StatusData\"><Columns><Column><Name>IdInternal</Name></Column><Column><Name>CurrentStatus</Name></Column></Columns><Rows>" );
        sb.append( "<Row><Value name=\"IdInternal\"><Type>String</Type><Data>" + incomingId + "</Data></Value><Value name=\"CurrentStatus\"><Type>String</Type><Data>" + status + "</Data></Value></Row>");
        sb.append( "</Rows></ValueTable></Structure>" );
        return sb.toString();
    }

    public static void checkAssertions(Map<String, String> param, String expectedId, String expectedText, String expectedPhone){
        Assert.assertTrue( param.containsKey( "id" ) );
        Assert.assertTrue( param.containsKey( "text" ) );
        Assert.assertTrue( param.containsKey( "phone" ) );
        Assert.assertEquals( param.get( "id" ), expectedId );
        Assert.assertEquals( param.get( "text" ), expectedText );
        Assert.assertEquals( param.get( "phone" ), expectedPhone );
    }

}
