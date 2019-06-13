package com.art.service.movie.tables;

import javax.persistence.*;

@Entity
@Table(name = "COMMENTS_MOVIE")
public class CommentsMovie {


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    @Column(name = "ID_USER")
    public long idUser;
    @Column(name = "ID_MOVIE")
    public long idMovie;
    @Column(name = "TEXT")
    public String text;
    @Column(name = "TIME_CREATE")
    public long timeCreate;
}
