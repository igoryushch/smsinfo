package ua.np.services.smsinfo;

import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 07.02.14
 */

public class OperatorServiceImpl implements OperatorService {

    private OperatorDao operatorDao;
    private List<Operator> operators;
    private Operator defaultOperator;

    @Override
    public List<Operator> getOperators() {
        if( operators == null ) readOperators();
        return operators;
    }

    @Override
    public Operator resolveOperator( String phoneCode ) {
        if( operators == null ) readOperators();

        for( Operator operator : operators ) {
            for(String code : operator.getPhoneCodeMaping()){
                if( code.equals( phoneCode ) )
                    return operator;
            }
        }
        return getDefaultOperator();
    }

    @Override
    public Operator getDefaultOperator(){
        if( operators == null )
            readOperators();
        if( defaultOperator == null ) {

            for( Operator operator : operators ) {
                for( String code : operator.getPhoneCodeMaping() ) {
                    if( "000".equals( code ) )
                        return operator;
                }
            }
        }
        return defaultOperator;
    }

    public void readOperators(){
        this.operators = this.operatorDao.findAll();
    }

    public void setOperatorDao( OperatorDao operatorDao ) {
        this.operatorDao = operatorDao;
    }
}
