package com.project.auction.model;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "report")
public class Report implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="user_id")
    private long userId;
    @Column(name="reported_user_id")
    private long reportedUserId;
    @Column(name="type")
    private int type;
    @Column(name="message")
    private String message;
}