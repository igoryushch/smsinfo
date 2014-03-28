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
public class LifeSmsStatusReceiverImpl implements SmsStatusReceiver {

    private SmsServiceDao smsServiceDao;
    private SmsServiceUtils smsServiceUtils;
    private OperatorDao operatorDao;
    private Unmarshaller jaxbUnmarshaller;

    @Override
    public String updateStatuses( String xml ) {

        LifeMessageStatus messageStatus = parseResponse( xml );
        if( messageStatus != null ){
            Map<String, String> idList = new HashMap<>();

            List<DetailType> detailTypeList = messageStatus.getDetail();
            for( DetailType detailType : detailTypeList ){
                 idList.put( detailType.getId(), detailType.getState() );
            }
            smsServiceDao.updateStatuses( idList, operatorDao.getOperatorByName( "Life" ) );
        }
        return "";
    }

    private LifeMessageStatus parseResponse( String statusString ) {
        LifeMessageStatus resultResponse = null;
        try {
            resultResponse = (LifeMessageStatus) jaxbUnmarshaller.unmarshal(new StringReader( statusString ));
        } catch( JAXBException e ) {
            e.printStackTrace();
        }
        return null;
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
