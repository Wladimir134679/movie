package com.art.service.movie.tables;

import javax.persistence.*;

@Entity
@Table(name = "USER_TOKEN_COOKIE")
public class UserTokenCookie {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    @Column(name = "ID_USER")
    public long idUser;
    @Column(name = "TOKEN")
    public String token;
    @Column(name = "TIME_CREATE")
    public long timeCreate;
    @Column(name = "AGE")
    public int age;
}
