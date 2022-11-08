package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "avatar_image")
public class AvatarImage {

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
}