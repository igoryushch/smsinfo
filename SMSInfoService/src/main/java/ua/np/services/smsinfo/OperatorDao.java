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
 * Date: 15.01.14
 */

public interface OperatorDao {
    public Operator getOperatorByPhoneCode(String phoneCode);
    public Operator getDefaultOperator();
    public Operator getOperatorByName(String name);
    public List<Operator> findAll();
}
