package com.art.service.movie.services;

import com.art.service.movie.tables.CommentsMovie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentsMovieService {

    @Autowired
    public SessionFactory sessionFactory;

    public void save(CommentsMovie com){
        Session ses = sessionFactory.getCurrentSession();
        ses.save(com);
    }

    public List<CommentsMovie> getByMovie(long idMovie){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM CommentsMovie where idMovie=:i";
        Query<CommentsMovie> q = ses.createQuery(sql, CommentsMovie.class);
        q.setParameter("i", idMovie);
        return q.getResultList();
    }

    public List<CommentsMovie> getByUser(long id){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM CommentsMovie where idUser=:i";
        Query<CommentsMovie> q = ses.createQuery(sql, CommentsMovie.class);
        q.setParameter("i", id);
        return q.getResultList();
    }
}
