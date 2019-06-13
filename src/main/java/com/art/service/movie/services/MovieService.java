package com.art.service.movie.services;

import com.art.service.movie.tables.AssessmentMovie;
import com.art.service.movie.tables.Movies;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    public SessionFactory sessionFactory;

    public List<Movies> getTop(){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM Movies ORDER BY id";
        Query<Movies> q = ses.createQuery(sql, Movies.class);
        return q.getResultList();
    }

    public Movies get(long id){
        Session ses = sessionFactory.getCurrentSession();
        return ses.get(Movies.class, id);
    }

    public void save(Movies movies){
        Session ses = sessionFactory.getCurrentSession();
        ses.save(movies);
    }

    public List<Movies> search(String str){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM Movies where description LIKE '%" + str + "%' OR name LIKE '%" + str + "%'";
        Query<Movies> q = ses.createQuery(sql, Movies.class);
        return q.getResultList();
    }

    public List<Movies> getPage(int page, int num){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM Movies";
        Query<Movies> q = ses.createQuery(sql, Movies.class);
        q.setFirstResult(page*num);
        q.setMaxResults(num);
        return q.getResultList();
    }

    public List<Movies> getAll(){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM Movies";
        Query<Movies> q = ses.createQuery(sql, Movies.class);
        return q.getResultList();
    }

    public int getMax(){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM Movies";
        Query<Movies> q = ses.createQuery(sql, Movies.class);
        return q.getResultList().size();
    }

    public void delete(Movies m){
        Session ses = sessionFactory.getCurrentSession();
        ses.delete(m);
    }
}
