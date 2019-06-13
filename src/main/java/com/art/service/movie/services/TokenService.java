package com.art.service.movie.services;

import com.art.service.movie.tables.UserTokenCookie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("tokenService")
@Transactional
public class TokenService{

    @Autowired
    public SessionFactory sessionFactory;

    public void save(UserTokenCookie token){
        Session ses = sessionFactory.getCurrentSession();
        ses.save(token);
    }

    public UserTokenCookie get(String token){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM UserTokenCookie where token=:t";
        Query q = ses.createQuery(sql);
        q.setParameter("t", token);
        List list = q.getResultList();
        if(list == null || list.size() <= 0)
            return null;
        return (UserTokenCookie)list.get(0);
    }

    public void delete(UserTokenCookie token){
        Session ses = sessionFactory.getCurrentSession();
        ses.delete(token);
    }
}
