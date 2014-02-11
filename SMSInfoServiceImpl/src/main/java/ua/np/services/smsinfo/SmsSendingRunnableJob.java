package ua.np.services.smsinfo;

import java.util.ArrayList;
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
 * Date: 29.01.14
 */

public class SmsSendingRunnableJob implements Runnable {

    private SmsServiceUtils smsServiceUtils;
    private Map<String, SmsSendingStrategy> operator2StrategyMap;

    private SmsService smsService;
    private OperatorService operatorService;

    private SmsInfoService smsInfoServiceClient;

    private int maxSmsCountPerReqest;

    public void run() {

        // get messages to send
        List<SmsRequest> requestList = smsService.getRequestsForSending();

        if( requestList.size() > 0 ) {
            // divide into separate operators
            Map<Operator, List<SmsRequest>> sendingMap = generateSendingMap( requestList );

            // send using separate strategy
            for( Map.Entry<Operator, List<SmsRequest>> entry : sendingMap.entrySet() ) {
                Operator operator = entry.getKey();
                if( operator != null ) {
                    invokeSendingStrategy(operator, entry.getValue());
                }
            }
        }
    }

    private void invokeSendingStrategy( Operator operator, List<SmsRequest> value ) {
        SmsSendingStrategy sendingStrategy = operator2StrategyMap.get( operator.getName() );
        if (sendingStrategy != null) {
            sendingStrategy.send( value, operator );
            smsInfoServiceClient.updateStatuses( buildUpdateRequest( value ), operator.getName() );
        }
    }

    private Map<Operator, List<SmsRequest>> generateSendingMap( List<SmsRequest> requestList ) {
        Map<Operator, List<SmsRequest>> result = new HashMap<>();
        for( SmsRequest request : requestList ) {
            Operator operator = operatorService.resolveOperator( smsServiceUtils.getPhoneCodeFromNumber( request.getPhoneNumber() ) );
            if( !result.containsKey( operator ) ) {
                result.put( operator, new ArrayList<SmsRequest>() );
            }
            if( result.get( operator ).size() < maxSmsCountPerReqest )
                result.get( operator ).add( request );
        }
        return result;
    }

    private UpdateRequest buildUpdateRequest(List<SmsRequest> requestList){
        UpdateRequest updateRequest = new UpdateRequest(  );
        for( SmsRequest smsRequest : requestList ){
            updateRequest.addUpdateItem( new UpdateItem(smsRequest.getOperatorMessageId(), smsRequest.getStatus()) );
        }
        return updateRequest;
    }

    public void setSmsInfoServiceClient( SmsInfoService smsInfoServiceClient ) {
        this.smsInfoServiceClient = smsInfoServiceClient;
    }

    public void setSmsServiceUtils( SmsServiceUtils smsServiceUtils ) {
        this.smsServiceUtils = smsServiceUtils;
    }

    public void setMaxSmsCountPerReqest( int maxSmsCountPerReqest ) {
        this.maxSmsCountPerReqest = maxSmsCountPerReqest;
    }

    public void setOperatorService( OperatorService operatorService ) {
        this.operatorService = operatorService;
    }

    public void setSmsService( SmsService smsService ) {
        this.smsService = smsService;
    }

    public void setOperator2StrategyMap( Map<String, SmsSendingStrategy> operator2StrategyMap ) {
        this.operator2StrategyMap = operator2StrategyMap;
    }

}
