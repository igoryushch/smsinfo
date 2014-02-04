package ua.np.services.smsinfo;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 09.01.14
 */

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "findById", query = "SELECT sr FROM SmsRequest sr WHERE sr.smsRequestId = :id"),
                @NamedQuery(name = "findByIncomingId", query = "SELECT sr FROM SmsRequest sr WHERE sr.incomingId = :incomingId"),
                @NamedQuery(name = "findBySystemName", query = "SELECT sr FROM SmsRequest sr WHERE sr.systemName = :systemName"),
                @NamedQuery(name = "findBySystemNameAndDate", query = "SELECT sr FROM SmsRequest sr WHERE sr.systemName = :systemName and (sr.creationDate between :startDate and :endDate)"),
                @NamedQuery(name = "findByStatus", query = "SELECT sr FROM SmsRequest sr WHERE sr.status = :status"),
                @NamedQuery(name = "findByOperatorId", query = "SELECT sr FROM SmsRequest sr WHERE sr.operator.operatorId = :operatorId"),
                @NamedQuery(name = "findByOperatorIdList", query = "SELECT sr FROM SmsRequest sr WHERE sr.operator.operatorId in :operatorIdList"),
                @NamedQuery(name = "findPendingRequests", query = "SELECT sr FROM SmsRequest sr WHERE sr.status = :statusPending")
        })
public class SmsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smsRequestId;

    @Column(nullable = false)
    private String incomingId;
    @Column(nullable = false)
    private String systemName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String messageText;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateDate;
    private String status;
    @ManyToOne
    @JoinColumn(name="operatorId")
    private Operator operator;
    private String operatorMessageId;

    public SmsRequest() {
    }

    public SmsRequest( String incomingId, String systemName, String phoneNumber, String messageText, Calendar creationDate, Calendar updateDate, String status, Operator operator, String operatorMessageId ) {
        this.incomingId = incomingId;
        this.systemName = systemName;
        this.phoneNumber = phoneNumber;
        this.messageText = messageText;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.status = status;
        this.operator = operator;
        this.operatorMessageId = operatorMessageId;
    }

    public Long getSmsRequestId() {
        return smsRequestId;
    }

    public void setSmsRequestId( Long id ) {
        this.smsRequestId = id;
    }

    public String getIncomingId() {
        return incomingId;
    }

    public void setIncomingId( String incomingId ) {
        this.incomingId = incomingId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName( String systemName ) {
        this.systemName = systemName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        if( phoneNumber.length() == 10 )
            this.phoneNumber = "38" + phoneNumber;
        else
            this.phoneNumber = phoneNumber;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText( String messageText ) {
        this.messageText = messageText;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate( Calendar creationDate ) {
        this.creationDate = creationDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate( Calendar updateDate ) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator( Operator operator ) {
        this.operator = operator;
    }

    public String getOperatorMessageId() {
        return operatorMessageId;
    }

    public void setOperatorMessageId( String operatorMessageId ) {
        this.operatorMessageId = operatorMessageId;
    }
}
