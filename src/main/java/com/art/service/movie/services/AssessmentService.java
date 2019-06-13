package com.art.service.movie.services;

import com.art.service.movie.tables.AssessmentMovie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssessmentService {

    @Autowired
    public SessionFactory sessionFactory;

    public void save(AssessmentMovie assess){
        Session ses = sessionFactory.getCurrentSession();
        ses.save(assess);
    }

    public List<AssessmentMovie> getByMovie(long id){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM AssessmentMovie where idMovie=:i";
        Query<AssessmentMovie> q = ses.createQuery(sql, AssessmentMovie.class);
        q.setParameter("i", id);
        return q.getResultList();
    }

    public List<AssessmentMovie> getByUser(long id){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM AssessmentMovie where idUser=:i";
        Query<AssessmentMovie> q = ses.createQuery(sql, AssessmentMovie.class);
        q.setParameter("i", id);
        return q.getResultList();
    }

    public void delete(AssessmentMovie ass){
        Session ses = sessionFactory.getCurrentSession();
        ses.delete(ass);
    }

    public boolean isAssessmentUser(long id, long idUser){
        return getAssessment(id, idUser) != null;
    }

    public AssessmentMovie getAssessment(long id, long idUser){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM AssessmentMovie where idMovie=:i AND idUser=:u";
        Query<AssessmentMovie> q = ses.createQuery(sql, AssessmentMovie.class);
        q.setParameter("i", id);
        q.setParameter("u", idUser);
        List<AssessmentMovie> list = q.getResultList();
        if(list.size() == 0)
            return null;
        return list.get(0);
    }

}
