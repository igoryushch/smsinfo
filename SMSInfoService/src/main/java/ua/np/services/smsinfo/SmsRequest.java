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
                @NamedQuery(name = "findById", query = "SELECT sr FROM SmsRequest sr WHERE sr.id = :id"),
                @NamedQuery(name = "findByIncomingId", query = "SELECT sr FROM SmsRequest sr WHERE sr.incomingId = :incomingId"),
                @NamedQuery(name = "findBySystemName", query = "SELECT sr FROM SmsRequest sr WHERE sr.sytemName = :sytemName"),
                @NamedQuery(name = "findBySystemNameAndDate", query = "SELECT sr FROM SmsRequest sr WHERE sr.sytemName = :sytemName and (sr.creationDate between :startDate and :endDate)"),
                @NamedQuery(name = "findByStatus", query = "SELECT sr FROM SmsRequest sr WHERE sr.status = :status"),
                @NamedQuery(name = "findByOperatorId", query = "SELECT sr FROM SmsRequest sr WHERE sr.operatorId = :operatorId"),
                @NamedQuery(name = "findByOperatorIdList", query = "SELECT sr FROM SmsRequest sr WHERE sr.operatorId in :operatorIdList"),
                @NamedQuery(name = "findPendingRequests", query = "SELECT sr FROM SmsRequest sr WHERE sr.status = :statusPending")
        })
public class SmsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String incomingId;
    @Column(nullable = false)
    private String sytemName;
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

    public SmsRequest( String incomingId, String sytemName, String phoneNumber, String messageText, Calendar creationDate, Calendar updateDate, String status, Operator operator, String operatorMessageId ) {
        this.incomingId = incomingId;
        this.sytemName = sytemName;
        this.phoneNumber = phoneNumber;
        this.messageText = messageText;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.status = status;
        this.operator = operator;
        this.operatorMessageId = operatorMessageId;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getIncomingId() {
        return incomingId;
    }

    public void setIncomingId( String incomingId ) {
        this.incomingId = incomingId;
    }

    public String getSytemName() {
        return sytemName;
    }

    public void setSytemName( String sytemName ) {
        this.sytemName = sytemName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
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
