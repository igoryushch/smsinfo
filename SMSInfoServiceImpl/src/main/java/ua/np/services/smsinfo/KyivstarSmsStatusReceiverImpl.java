package ua.np.services.smsinfo;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
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
 * Date: 28.01.14
 */
public class KyivstarSmsStatusReceiverImpl implements SmsStatusReceiver {

    private SmsServiceDao smsServiceDao;
    private SmsServiceUtils smsServiceUtils;
    private OperatorDao operatorDao;
    private Unmarshaller jaxbUnmarshaller;

    @Override
    public String updateStatuses( String xml ) {

        KyivstarAcceptanceResponse acceptanceResponse = parseResponse( xml );
        if( acceptanceResponse != null ) {
            List<KyivstarAcceptanceStatus> statusList = acceptanceResponse.getStatus();

            Map<String, String> idList = new HashMap<>();
            for( KyivstarAcceptanceStatus acceptanceStatus : statusList ) {
                idList.put( acceptanceStatus.getClid(), acceptanceStatus.getValue() );
            }
            smsServiceDao.updateStatuses( idList, operatorDao.getOperatorByName( "Kyivstar" ) );
        }
        return "";
    }

    private KyivstarAcceptanceResponse parseResponse( String statusString ) {
        KyivstarAcceptanceResponse resultResponse = null;
        try {
            resultResponse = (KyivstarAcceptanceResponse) jaxbUnmarshaller.unmarshal(new StringReader( statusString ));
        } catch( JAXBException e ) {
            e.printStackTrace();
        }
        return resultResponse;
    }

    public void setSmsServiceDao( SmsServiceDao smsServiceDao ) {
        this.smsServiceDao = smsServiceDao;
    }

    public void setSmsServiceUtils( SmsServiceUtils smsServiceUtils ) {
        this.smsServiceUtils = smsServiceUtils;
    }

    public void setOperatorDao( OperatorDao operatorDao ) {
        this.operatorDao = operatorDao;
    }

    public void setJaxbUnmarshaller( Unmarshaller jaxbUnmarshaller ) {
        this.jaxbUnmarshaller = jaxbUnmarshaller;
    }
}
