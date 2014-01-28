package ua.np.services.smsinfo;

import javax.xml.bind.JAXBContext;
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

    @Override
    public String updateStatuses( String xml ) {

        KyivstarAcceptanceResponse acceptanceResponse = parseResponse( xml );
        List<KyivstarAcceptanceStatus> statusList = acceptanceResponse.getStatus();

        Map<String, String> idList = new HashMap<>(  );
        for (KyivstarAcceptanceStatus acceptanceStatus : statusList){
            idList.put( acceptanceStatus.getClid() , acceptanceStatus.getValue());
        }

        smsServiceDao.updateOperatorStatuses( idList, operatorDao.getOperatorByName( "Kyivstar" ) );

        return null;
    }

    private KyivstarAcceptanceResponse parseResponse( String statusString ) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( KyivstarAcceptanceResponse.class );

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            KyivstarAcceptanceResponse resultResponse = (KyivstarAcceptanceResponse) jaxbUnmarshaller.unmarshal(new StringReader( statusString ));

            return resultResponse;

        } catch( JAXBException e ) {
            e.printStackTrace();
        }

        return null;
    }
}
