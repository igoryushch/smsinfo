package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 11.02.14
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class UpdateItem {
    private String messageId;
    private String messageStatus;

    public UpdateItem() {
    }

    public UpdateItem( String messageId, String messageStatus ) {
        this.messageId = messageId;
        this.messageStatus = messageStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId( String messageId ) {
        this.messageId = messageId;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus( String messageStatus ) {
        this.messageStatus = messageStatus;
    }
}
