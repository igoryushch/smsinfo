package ua.np.services.smsinfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
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

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addRequests( List<SmsRequest> requests ) {
        for( SmsRequest smsRequest : requests ){
            entityManager.persist( smsRequest );
        }
    }

    @Override
    public List<SmsRequest> getRequestsForSystem( String systemName ) {
        if( systemName == null ) return Collections.<SmsRequest>emptyList();
        return entityManager.createNamedQuery( "findBySystemName", SmsRequest.class ).setParameter( "systemName", systemName ).getResultList();
    }

    @Override
    public void updateStatuses( Map<String, String> statusMapByOperatorId ) {

    }
}
