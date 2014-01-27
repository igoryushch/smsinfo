package ua.np.services.smsinfo;

import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 16.01.14
 */

public class LifeSmsSendingStrategy implements SmsSendingStrategy {

    @Value( "${lifeHost}" )
    private String operatorHost;
    @Value( "${lifeAuthHost}" )
    private String operatorAuthHost;
    @Value( "${lifeHostUser}" )
    private String operatorLogin;
    @Value( "${lifeHostPassword}" )
    private String operatorPassword;

    private OperatorRestClient operatorRestClient;

    @Override
    public List<SmsRequest> send( List<SmsRequest> smsRequestList ) {

        String xmlRequest = buildXmlRequest( smsRequestList );
        HttpPost postRequest = new HttpPost( operatorHost );
        InputStream responseInputStream = operatorRestClient.sendRequest( postRequest, xmlRequest, operatorLogin, operatorPassword );
        Map<String,String> statusMap = parseResponseStatuses(responseInputStream);

        int i = 0;

        for( Map.Entry<String, String> entry : statusMap.entrySet() ){
            SmsRequest request = smsRequestList.get( i );
            request.setOperatorMessageId( entry.getKey() );
            request.setOperatorStatus( entry.getValue() );
            if( "Accepted".equals( entry.getValue() )){
                request.setStatus( "Posted" );
            }
            i++;
        }
        return smsRequestList;
    }

    private Map<String, String> parseResponseStatuses( InputStream responseInputStream ) {

        Map<String, String> result = new HashMap<>(  );

        String tagContent = null;
        String currentKey = null;

        try {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader( responseInputStream );
            while(reader.hasNext()){
                int event = reader.next();
                switch(event){
                    case XMLStreamConstants.CHARACTERS:
                        tagContent = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        if( "id".equals( reader.getLocalName() )){
                            currentKey = tagContent;
                        }
                        if ("state".equals(reader.getLocalName())){
                            result.put( currentKey, tagContent );
                        }
                        break;
                }
            }

        } catch( XMLStreamException e ) {
            e.printStackTrace();
        }
        return result;
    }

    private String buildXmlRequest( List<SmsRequest> smsRequestList ) {
        StringBuilder sb = new StringBuilder(  );

        sb.append( "<message><service id=\"" + getServiceType( smsRequestList.size() ) + "\" validity=\"+5 hour\" source = \"NovaPoshta\" uniq_key=\"" + generateUniqueKey() + "\"/>" );
        for(SmsRequest smsRequest : smsRequestList){
            sb.append( "<to>" );
            sb.append(smsRequest.getPhoneNumber());
            sb.append("</to><body content-type=\"text/plain\">");
            sb.append(smsRequest.getMessageText());
            sb.append("</body>");
        }
        sb.append( "</message>" );
        return sb.toString();
    }

    private String getServiceType(int smsCount){
        return smsCount > 1 ? "individual" : "single";
    }

    private String generateUniqueKey(){
        return String.valueOf( (int) (Math.random()*Integer.MAX_VALUE) );
    }

    public void setOperatorHost( String operatorHost ) {
        this.operatorHost = operatorHost;
    }

    public void setOperatorAuthHost( String operatorAuthHost ) {
        this.operatorAuthHost = operatorAuthHost;
    }

    public void setOperatorLogin( String operatorLogin ) {
        this.operatorLogin = operatorLogin;
    }

    public void setOperatorPassword( String operatorPassword ) {
        this.operatorPassword = operatorPassword;
    }

    public void setOperatorRestClient( OperatorRestClient operatorRestClient ) {
        this.operatorRestClient = operatorRestClient;
    }
}
