package com.art.service.movie.form;

import com.art.service.movie.tables.CommentsMovie;
import com.art.service.movie.tables.Movies;
import com.art.service.movie.tables.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentsData {

    public CommentsMovie comments;
    public Movies movie;
    public User user;

    public CommentsData(CommentsMovie comments) {
        this.comments = comments;
    }

    public CommentsData(CommentsMovie comments, User user) {
        this.comments = comments;
        this.user = user;
    }

    public String getMovie(){
        return movie.name;
    }

    public String getText(){
        return comments.text;
    }

    public String getTime(){
        String pattern = "HH:mm / dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();
        date.setTime(comments.timeCreate);
        return format.format(date);
    }

    public String getAuthor(){
        return user.nickname;
    }
}
