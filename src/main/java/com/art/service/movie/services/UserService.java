package com.art.service.movie.services;

import com.art.service.movie.tables.User;
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

@Service("userService")
@Transactional
public class UserService{

    @Autowired
    public SessionFactory sessionFactory;

    public void save(User user){
        Session ses = sessionFactory.getCurrentSession();
        ses.save(user);
    }

    public List<User> getAll(){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM User";
        Query q = ses.createQuery(sql);
        return q.list();
    }

    public User get(long id){
        Session ses = sessionFactory.getCurrentSession();
        return ses.get(User.class, id);
    }

    public User findByEmailAndPassword(String email, String pass){
        Session ses = sessionFactory.getCurrentSession();
        String sql = "FROM User where email=:e AND password=:p";
        Query q = ses.createQuery(sql);
        q.setParameter("p", pass);
        q.setParameter("e", email);
        List<User> list = q.getResultList();
        if(list == null || list.size() <= 0)
            return null;
        return list.get(0);
    }
}
