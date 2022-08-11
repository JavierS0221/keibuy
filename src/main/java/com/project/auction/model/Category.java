package com.project.auction.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Item> listItems;


}