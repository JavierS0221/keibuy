package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "log")
public class Log implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="person_id", nullable = false)
    private Person person;

    @Column(name = "message", length = 250, nullable = false)
    private String message;

    @Column(name = "type")
    private int type;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;
}