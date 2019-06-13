package com.art.service.movie.tables;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    @Column(name = "NICKNAME")
    public String nickname;
    @Column(name = "EMAIL")
    public String email;
    @Column(name = "PASSWORD")
    public String password;
    @Column(name = "IS_ADMIN")
    public boolean isAdmin;
}
