package ua.np.services.smsinfo;

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

    @Override
    public String updateStatuses( String xml ) {
        return null;
    }
}
