package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item_image")
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "file_name", length = 45)
    private String fileName;

    @Lob
    @Column(name = "bytes", length = Integer.MAX_VALUE)
    private byte[] bytes;

    private String contentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item", referencedColumnName = "id")
    private Item item;

}