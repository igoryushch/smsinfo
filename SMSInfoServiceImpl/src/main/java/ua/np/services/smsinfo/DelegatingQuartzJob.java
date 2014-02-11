package ua.np.services.smsinfo;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

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

public class DelegatingQuartzJob extends QuartzJobBean {

    private String runnableBeanName;

    public void setRunnableBeanName( String runnableBeanName ) {
        this.runnableBeanName = runnableBeanName;
    }

    @Override
    protected void executeInternal( JobExecutionContext context ) throws JobExecutionException {
        try {
            ((ApplicationContext) context.getScheduler().getContext().get("applicationContext")).getBean( runnableBeanName, Runnable.class ).run();
        }
        catch( Exception e ) {
            throw new JobExecutionException( e );
        }
    }
}
