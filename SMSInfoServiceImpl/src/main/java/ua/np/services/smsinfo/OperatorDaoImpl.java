package ua.np.services.smsinfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 15.01.14
 */

public class OperatorDaoImpl implements OperatorDao {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Operator getOperatorByPhoneCode(String phoneCode){
        List<Operator> resultList = entityManager.createNamedQuery( "findByPhoneCode",Operator.class ).setParameter( "code", phoneCode ).getResultList();
        if( resultList.isEmpty() ) return getDefaultOperator();
        return resultList.get( 0 );
    }

    @Override
    public Operator getDefaultOperator(){
        List<Operator> resultList = entityManager.createNamedQuery( "findByPhoneCode",Operator.class ).setParameter( "code", "000" ).getResultList();
        if( resultList.isEmpty() ) return null;
        return resultList.get( 0 );
    }

}
