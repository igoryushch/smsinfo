package ua.np.services.smsinfo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

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

public class SmsSendingJob implements Job {

    private SmsServiceDao smsServiceDao;
    private OperatorDao operatorDao;
    private List<Operator> operators;
    private Operator defaultOperator;
    private SmsServiceUtils smsServiceUtils;
    @Autowired
    private ApplicationContext applicationContext;
    private int maxSmsCountPerReqest;

    public void doSend(){

        // get messages to send
        List<SmsRequest> requestList = smsServiceDao.getMessagesToSend();

        if( requestList.size() > 0 ){
            // divide into separate operators
            Map<Operator, List<SmsRequest>> sendingMap = generateSendingMap(requestList);

            // send using separate strategy
            for( Map.Entry<Operator, List<SmsRequest>> entry : sendingMap.entrySet()){
                Operator operator = entry.getKey();
                if( operator != null ) {
//                    invokeSendingStrategy(operator, entry.getValue());
                }
            }
        }
    }

    private void invokeSendingStrategy( Operator operator, List<SmsRequest> value ) {
        SmsSendingStrategy sendingStrategy = getSendingStrategy(operator);
        sendingStrategy.send( value, operator );
        smsServiceDao.mergeMessages( value );
    }

    private SmsSendingStrategy getSendingStrategy( Operator operator ) {
        return applicationContext.getBean( operator.getName().toLowerCase() + "SmsSendingStrategy", SmsSendingStrategy.class );
    }

    private Map<Operator, List<SmsRequest>> generateSendingMap( List<SmsRequest> requestList ) {
        Map<Operator, List<SmsRequest>> result = new HashMap<>(  );
        for( SmsRequest request : requestList ) {
            Operator operator = resolveOperator( smsServiceUtils.getPhoneCodeFromNumber( request.getPhoneNumber() ) );
            if( !result.containsKey( operator ) ) {
                result.put( operator, new ArrayList<SmsRequest>(  ) );
            }
            if( result.get( operator ).size() < maxSmsCountPerReqest )
                result.get( operator ).add( request );
        }
        return result;
    }

    private Operator resolveOperator( String phoneCode ) {
        if( this.operators == null )
            this.operators = operatorDao.findAll();

        if( this.defaultOperator == null )
            this.defaultOperator = getDefaultOperator();


        for( Operator operator : operators ) {
            for(String code : operator.getPhoneCodeMaping()){
                if( code.equals( phoneCode ) )
                    return operator;
            }
        }
        return defaultOperator;
    }

    private Operator getDefaultOperator(){
        for( Operator operator : operators ) {
            for(String code : operator.getPhoneCodeMaping()){
                if( "000".equals( code ) )
                    return operator;
            }
        }
        return null;
    }

    public void setSmsServiceDao( SmsServiceDao smsServiceDao ) {
        this.smsServiceDao = smsServiceDao;
    }

    public void setOperatorDao( OperatorDao operatorDao ) {
        this.operatorDao = operatorDao;
    }

    public void setSmsServiceUtils( SmsServiceUtils smsServiceUtils ) {
        this.smsServiceUtils = smsServiceUtils;
    }

    public void setMaxSmsCountPerReqest( int maxSmsCountPerReqest ) {
        this.maxSmsCountPerReqest = maxSmsCountPerReqest;
    }

    @Override
    public void execute( JobExecutionContext jobExecutionContext ) throws JobExecutionException {
        doSend();
    }
}
