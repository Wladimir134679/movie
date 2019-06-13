package com.art.service.movie.controllers;

import com.art.service.movie.form.MovieAddForm;
import com.art.service.movie.form.MovieData;
import com.art.service.movie.services.AssessmentService;
import com.art.service.movie.services.CommentsMovieService;
import com.art.service.movie.services.MovieService;
import com.art.service.movie.services.UserService;
import com.art.service.movie.tables.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    public MovieService movieService;
    @Autowired
    public CommentsMovieService commentsMovieService;
    @Autowired
    public AssessmentService assessmentService;
    @Autowired
    public UserService userService;

    @RequestMapping(path = "/admin/movie", method = RequestMethod.GET)
    public String movie(Model model,
                        @CookieValue(name = "userToken", required = false)Cookie cookie){
        if(cookie == null){
            return "redirect:/index";
        }

        MovieAddForm movieForm = new MovieAddForm();
        model.addAttribute("movieForm", movieForm);

        List<Movies> moviesList = movieService.getAll();
        List<MovieData> movieDataList = new ArrayList<>();
        for(Movies m : moviesList){
            movieDataList.add(new MovieData(m));
        }

        model.addAttribute("movieList", movieDataList);

        return "admin";
    }

    @PostMapping(path = "/admin/movie/add")
    public String add(Model model,
                      @CookieValue(name = "userToken", required = false)Cookie cookie,
                      @ModelAttribute("movieForm") MovieAddForm form){
        Movies m = new Movies();
        m.description = form.description;
        m.name = form.name;

        movieService.save(m);

        return "redirect:/admin/movie";
    }

    @GetMapping(path = "/admin/movie/del")
    public String del(Model model,
                      @CookieValue(name = "userToken", required = false)Cookie cookie,
                      @RequestParam(name = "id", required = false)Integer id){
        if(id == null)
            return "redirect:/admin/movie";

        Movies movies = movieService.get(id);
        if(movies != null){
            movieService.delete(movies);
        }

        return "redirect:/admin/movie";
    }
}
