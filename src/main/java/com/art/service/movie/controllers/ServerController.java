package com.art.service.movie.controllers;

import com.art.service.movie.services.MovieService;
import com.art.service.movie.services.UserService;
import com.art.service.movie.tables.Movies;
import com.art.service.movie.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ServerController {

    @Autowired
    public UserService userService;
    @Autowired
    public MovieService movieService;

    @RequestMapping(path = "/server/com1", method = RequestMethod.GET)
    public String com1(HttpServletRequest request){
        User user = new User();
        user.email = "www@email.com";
        user.isAdmin = true;
        user.password = "www";
        user.nickname = "\\WWW/";
        userService.save(user);

        return "redirect:/index";
    }

    @RequestMapping(path = "/server/com2", method = RequestMethod.GET)
    public String com2(HttpServletRequest request){
        Movies movies = new Movies();
        movies.name = "Фильм 1";
        movies.description = "Какое-то супер описание большое для нового крутоо фильма";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Новый 2";
        movies.description = "Описание как по мне";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Фильм 3";
        movies.description = "Что-бы кто-то сказал";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Новый 4";
        movies.description = "4 - самый топ";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Фильм 44";
        movies.description = "Не верь ему!";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Новый 5";
        movies.description = "Топ фильм о приключениях человека";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Фильм 6";
        movies.description = "Документальный фильм";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Новый 7";
        movies.description = "Привет из рая";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Новый 8";
        movies.description = "Достучаться до небес";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Фильм 9";
        movies.description = "Быстро ходим далеко и надолго";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Фильм 10";
        movies.description = "История о мальчике-спальчике в современном стиле";
        movieService.save(movies);

        movies = new Movies();
        movies.name = "Фильм 11";
        movies.description = "ШАЙТАН-МАШИНА тюх-тюх";
        movieService.save(movies);


        return "redirect:/index";
    }
}
