package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "image")
public class Image implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "file_name", length = 45, nullable = false)
    private String fileName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="item_id", nullable = false)
    private Item item;
}