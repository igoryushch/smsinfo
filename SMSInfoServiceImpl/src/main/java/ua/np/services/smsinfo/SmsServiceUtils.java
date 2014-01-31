package ua.np.services.smsinfo;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

public class SmsServiceUtils {

    private OperatorDao operatorDao;

    public void setOperatorDao( OperatorDao operatorDao ) {
        this.operatorDao = operatorDao;
    }

    public List<SmsRequest> getRequestsFromXmlString(String xmlString, String systemName){

        List<Map<String,String>> messages = getMessageParams( xmlString );
        List<SmsRequest> resultList = new ArrayList<>(  );

        for( Map<String,String> messageRequest : messages ){
            resultList.add( buildNewSmsRequest( messageRequest, systemName ) );
        }

        return resultList;
    }

    public SmsRequest buildNewSmsRequest(Map<String,String> messageRequest, String systemName){

        SmsRequest request = new SmsRequest(  );
        request.setIncomingId( messageRequest.get( "id" ) );
        request.setSystemName( systemName );
        request.setPhoneNumber( messageRequest.get( "phone" ) );
        request.setMessageText( messageRequest.get( "text" ) );
        request.setCreationDate( new GregorianCalendar(  ) );
        request.setUpdateDate( new GregorianCalendar() );
        request.setStatus( getInitialStatus() );

        return request;
    }

    public String getInitialStatus(){
        return "Pending";
    }

    public Operator resolveOperator( String phoneNumber ){
        return operatorDao.getOperatorByPhoneCode( getPhoneCodeFromNumber(phoneNumber) ) ;
    }

    public String getPhoneCodeFromNumber(String phoneNumber){
        return phoneNumber.substring( phoneNumber.length() - 10, phoneNumber.length() - 7);
    }

    public String buildAcceptedResponse( List<SmsRequest> smsRequests ){
        StringBuilder sb = new StringBuilder(  );
        sb.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Array><Structure>" );

        String template = "<Value name=\"IDIncoming\"><Type>String</Type><Data>{0}</Data></Value><Value name=\"IDInternal\"><Type>String</Type><Data>{1}</Data></Value>";

        for (SmsRequest request : smsRequests){
            sb.append( template.replace( "{0}", request.getIncomingId() ).replace( "{1}", String.valueOf( request.getId() ) ) );
        }

        sb.append( "</Structure></Array>" );

        return sb.toString();
    }

    public String buildDeliveryStatusResponse( List<SmsRequest> smsRequests ){
        StringBuilder sb = new StringBuilder(  );
        sb.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?><Structure><Value name=\"ChangesUUID\"><Type>String</Type><Data>null</Data></Value><ValueTable name=\"StatusData\"><Columns><Column><Name>IdInternal</Name></Column><Column><Name>CurrentStatus</Name></Column></Columns><Rows>" );

        String template = "<Row><Value name=\"IdInternal\"><Type>String</Type><Data>{0}</Data></Value><Value name=\"CurrentStatus\"><Type>String</Type><Data>{1}</Data></Value></Row>";

        for (SmsRequest request : smsRequests){
            sb.append( template.replace( "{0}",request.getIncomingId()).replace( "{1}",request.getStatus()) );
        }

        sb.append( "</Rows></ValueTable></Structure>" );
        return sb.toString();
    }

    public List<Map<String, String>> getMessageParams( String xmlString ) {

        List<Map<String,String>> result = new ArrayList<>(  ) ;
        Map<String,String> messageEntry = new HashMap<>(  );

        InputStream is = new ByteArrayInputStream(xmlString.getBytes());

        String currentKey = "";
        String currentValue = "";

        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = factory.createXMLStreamReader( is );

            while(reader.hasNext()){

                int event = reader.next();

                switch(event){
                    case XMLStreamConstants.START_ELEMENT:
                        if ("Value".equals(reader.getLocalName())){
                            currentKey = reader.getAttributeValue( 0 );
                        }
                        if("Data".equals(reader.getLocalName())){

                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        currentValue = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        if( "Value".equals( reader.getLocalName() )){
                            messageEntry.put( currentKey, currentValue );
                        }
                        if( "Structure".equals( reader.getLocalName() ) && !currentKey.equals( "" )){
                            result.add( messageEntry );
                            messageEntry = new HashMap<String,String>(  );
                            currentKey = "";
                        }
                        break;
                }
            }
        } catch( XMLStreamException e ) {
            e.printStackTrace();
        }
        return result;
    }
}