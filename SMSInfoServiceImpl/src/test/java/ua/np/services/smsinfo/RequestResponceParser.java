package ua.np.services.smsinfo;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2013 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 23.12.13
 */


public class RequestResponceParser {

    private static XMLInputFactory factory = XMLInputFactory.newInstance();

    static List<Map<String,String>> getResponceStatuses(InputStream responceStream){

        return parseResponceParams( responceStream, false );

    }

    static List<Map<String,String>> getDeliveryStatuses(InputStream responceStream){
        return parseResponceParams( responceStream, true );
    }

    static List<Map<String,String>> parseResponceParams(InputStream responceStream, boolean deliveryCheck){

        List<Map<String,String>> result = new ArrayList<>(  );

        String currentKey = "";
        String currentValue = "";

        try {
            XMLStreamReader reader = factory.createXMLStreamReader( responceStream );

            while(reader.hasNext()){

                int event = reader.next();

                switch(event){
                    case XMLStreamConstants.START_ELEMENT:
                        if (!deliveryCheck && "status".equals(reader.getLocalName())){
                            currentKey = reader.getAttributeValue( 0 );
                        }

                        if (deliveryCheck && "message".equals(reader.getLocalName())){
                            currentKey = reader.getAttributeValue( 0 );
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        currentValue = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        if( "status".equals( reader.getLocalName() )){
                            Map<String,String> messageEntry = new HashMap<String,String>(  );
                            messageEntry.put( currentKey, currentValue );
                            result.add( messageEntry );
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
