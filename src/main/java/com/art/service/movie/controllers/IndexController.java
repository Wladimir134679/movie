package com.art.service.movie.controllers;

import com.art.service.movie.form.MovieData;
import com.art.service.movie.form.UserData;
import com.art.service.movie.services.AssessmentService;
import com.art.service.movie.services.CommentsMovieService;
import com.art.service.movie.services.MovieService;
import com.art.service.movie.services.UserService;
import com.art.service.movie.tables.AssessmentMovie;
import com.art.service.movie.tables.CommentsMovie;
import com.art.service.movie.tables.Movies;
import com.art.service.movie.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    public MovieService movieService;
    @Autowired
    public CommentsMovieService commentsMovieService;
    @Autowired
    public AssessmentService assessmentService;
    @Autowired
    public UserService userService;

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model){

        List<Movies> listMovies = movieService.getTop();
        model.addAttribute("moviesList", get(listMovies));

        return "index";
    }

    @RequestMapping(path="/search", method = RequestMethod.GET)
    public String search(Model model,
                         @RequestParam(name = "s", required = false)String str){
        if(str == null)
            return "search";

        model.addAttribute("str", str);
        List<Movies> listMovies = movieService.search(str);
        model.addAttribute("moviesList", get(listMovies));

        return "search";
    }

    @RequestMapping(path = "users")
    public String users(Model model){
        List<User> userList = userService.getAll();
        List<UserData> userListData = new ArrayList<>();
        for(User user : userList){
            UserData data = new UserData(user);

            List<AssessmentMovie> listAssessment = assessmentService.getByUser(user.id);
            data.numAssessment = listAssessment.size();

            List<CommentsMovie> commentsMovieList = commentsMovieService.getByUser(user.id);
            data.numComment = commentsMovieList.size();

            userListData.add(data);
        }
        model.addAttribute("userListData", userListData);

        return "users";
    }

    private List<MovieData> get(List<Movies> listMovies){
        List<MovieData> listData = new ArrayList<>();
        if(listMovies.size() == 0)
            return listData;
        for(int i = listMovies.size() - 1; i >= listMovies.size() - 6 && i >= 0; i--){
            Movies movies = listMovies.get(i);
            MovieData data = new MovieData(movies);

            List<CommentsMovie> coms = commentsMovieService.getByMovie(movies.id);
            data.numComments = coms.size();

            List<AssessmentMovie> listAssess = assessmentService.getByMovie(movies.id);
            double sum = 0;
            for(AssessmentMovie am : listAssess){
                sum += am.assessment;
            }
            if(listAssess.size() == 0)
                sum = 0;
            else
                sum /= listAssess.size();
            data.assessment = sum;

            listData.add(data);
        }
        return listData;
    }
}
