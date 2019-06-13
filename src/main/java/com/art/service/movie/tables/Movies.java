package com.art.service.movie.tables;

import javax.persistence.*;

@Entity
@Table(name = "MOVIES")
public class Movies {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    @Column
    public String name;
    @Column(name = "DESCRIPTION")
    public String description;
}
