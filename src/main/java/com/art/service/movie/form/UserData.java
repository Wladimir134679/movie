package com.art.service.movie.form;

import com.art.service.movie.tables.User;

public class UserData {

    public User user;
    public int numComment;
    public int numAssessment;

    public UserData(User user) {
        this.user = user;
    }

    public String getNickname(){
        return user.nickname;
    }

    public int getNumComment(){
        return numComment;
    }

    public int getNumAssessment(){
        return numAssessment;
    }

    public String getEmail(){
        return user.email;
    }

    public boolean isAdmin(){
        return user.isAdmin;
    }
}
