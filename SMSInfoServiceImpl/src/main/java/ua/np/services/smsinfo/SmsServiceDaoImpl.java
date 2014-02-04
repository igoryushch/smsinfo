package ua.np.services.smsinfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.GregorianCalendar;
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
 * Date: 10.01.14
 */


public class SmsServiceDaoImpl implements SmsServiceDao {

    private EntityManager entityManager;
    private int maxSendCount;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMaxSendCount( int maxSendCount ) {
        this.maxSendCount = maxSendCount;
    }

    @Override
    public List <SmsRequest> addRequests( List<SmsRequest> requests ) {
        for( SmsRequest smsRequest : requests ){
            entityManager.persist( smsRequest );
        }
        return requests;
    }

    @Override
    public List<SmsRequest> getRequestsForSystem( String systemName ) {
        if( systemName == null ) return Collections.<SmsRequest>emptyList();
        return entityManager.createNamedQuery( "findBySystemName", SmsRequest.class ).setParameter( "systemName", systemName ).getResultList();
    }

    @Override
    public List<SmsRequest> getMessagesToSend() {
        return entityManager.createNamedQuery( "findPendingRequests", SmsRequest.class )
                .setParameter( "statusPending", "Pending" )
                .setMaxResults( maxSendCount )
                .getResultList();
    }

    @Override
    public void updateStatuses( Map<String, String> statusMap ) {
        String queryString = "UPDATE SmsRequest sr SET sr.status = :newStatus WHERE sr.operatorMessageId = :operatorMid";
        for( Map.Entry<String, String> entry : statusMap.entrySet() ){
            entityManager.createQuery( queryString )
                    .setParameter( "newStatus", entry.getValue() )
                    .setParameter( "operatorMid", entry.getKey() )
                    .executeUpdate();
        }
    }

    @Override
    public void updateStatuses( Map<String, String> statusMap, Operator operator ) {
        String queryString = "UPDATE SmsRequest sr SET sr.status = :newStatus WHERE sr.operatorMessageId = :operatorMid";
        if( operator != null ) queryString = queryString + " AND sr.operator = :operator";
        for( Map.Entry<String, String> entry : statusMap.entrySet() ){
            entityManager.createQuery( queryString )
                    .setParameter( "newStatus", entry.getValue() )
                    .setParameter( "operatorMid", entry.getKey() )
                    .setParameter( "operator", operator )
                    .executeUpdate();
        }
    }

    @Override
    public List<SmsRequest> mergeMessages( List<SmsRequest> requests ) {
        for( SmsRequest smsRequest : requests ){
            smsRequest.setUpdateDate( new GregorianCalendar(  ) );
            entityManager.merge( smsRequest );
        }
        return requests;
    }
}
