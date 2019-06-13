package com.art.service.movie.tables;

import javax.persistence.*;

@Entity
@Table(name = "ASSESSMENT_MOVIE")
public class AssessmentMovie {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    @Column(name = "ID_USER")
    public long idUser;
    @Column(name = "ID_MOVIE")
    public long idMovie;
    @Column(name = "ASSESSMENT")
    public int assessment;
    @Column(name = "TIME_CREATE")
    public long timeCreate;
}
