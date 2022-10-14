package com.project.auction.model;

import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Data
@Entity
@Table(name = "image")
public class Image {

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