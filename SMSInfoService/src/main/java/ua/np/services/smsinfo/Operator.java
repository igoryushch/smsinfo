package ua.np.services.smsinfo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (C) 2013 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 26.12.13
 */

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "findAll", query = "SELECT op FROM Operator op"),
                @NamedQuery(name = "findByPhoneCode", query = "SELECT op FROM Operator op WHERE op.phoneCodes = :code"),
                @NamedQuery(name = "findByName", query = "SELECT op FROM Operator op WHERE LOWER(op.name) = :name"),
        })
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long operatorId;
    @Column(nullable = false)
    private String name;
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(
            name = "phoneCodeMapping",
            joinColumns =
            @JoinColumn(name = "operatorId"))
    private Set<String> phoneCodes;

    public Operator() {
    }

    public Operator( String name, Set<String> phoneCodes ) {
        this.name = name;
        this.phoneCodes = phoneCodes;
    }

    public Operator( String name ) {
        this.name = name;
        this.phoneCodes = new HashSet<>(  );
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId( Long id ) {
        this.operatorId = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set<String> getPhoneCodeMaping() {
        return phoneCodes;
    }

    public void setPhoneCodeMaping( Set<String> phoneCodes ) {
        this.phoneCodes = phoneCodes;
    }

}
