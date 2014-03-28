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

public interface OperatorService {

    public List<Operator> getOperators();

    public Operator getDefaultOperator();

    public Operator resolveOperator( String phoneCode );

}
