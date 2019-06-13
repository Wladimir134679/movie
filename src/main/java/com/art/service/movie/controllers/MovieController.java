package com.art.service.movie.controllers;

import com.art.service.movie.form.CommentsData;
import com.art.service.movie.form.MovieCommentAdd;
import com.art.service.movie.form.MovieData;
import com.art.service.movie.services.*;
import com.art.service.movie.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    public MovieService movieService;
    @Autowired
    public CommentsMovieService commentsMovieService;
    @Autowired
    public TokenService tokenService;
    @Autowired
    public UserService userService;
    @Autowired
    public AssessmentService assessmentService;

    @RequestMapping(path = "/movies", method = RequestMethod.GET)
    public String movies(Model model,
                         @CookieValue(name = "userToken", required = false) Cookie cookie,
                         @RequestParam(name = "page", required = false) Integer page){
        if(page == null)
            page = 0;
        else
            page--;
        List<Movies> listMovies = movieService.getPage(page, 3);
        List<MovieData> listData = new ArrayList<>();
        for(Movies movies : listMovies){
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
            else {
                sum /= listAssess.size();
            }
            data.assessment = sum;

            listData.add(data);
        }
        int maxPage = (int)Math.ceil(movieService.getMax() / 3f);
        ArrayList<Integer> listPages = new ArrayList<>();
        for(int i = 1; i <= maxPage; i++)
            listPages.add(i);
        model.addAttribute("page", page);
        model.addAttribute("listPages", listPages);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("moviesList", listData);

        return "movies";
    }

    @RequestMapping(path = "/movie/show", method = RequestMethod.GET)
    public String getMovie(Model model,
                           @CookieValue(name = "userToken", required = false) Cookie cookie,
                           @RequestParam(name = "id")long id){
        Movies movies = movieService.get(id);
        if(movies == null)
            return "redirect:/index";
        MovieData data = new MovieData(movies);

        double sum = 0;
        List<AssessmentMovie> listAssess = assessmentService.getByMovie(movies.id);
        data.assessmentMovieList = listAssess;
        for(AssessmentMovie am : listAssess){
            sum += am.assessment;
        }
        if(listAssess.size() == 0)
            sum = 0;
        else
            sum /= listAssess.size();
        data.assessment = sum;

        model.addAttribute("movieData", data);

        List<CommentsMovie> listMovie = commentsMovieService.getByMovie(id);
        List<CommentsData> listData = new ArrayList<>();
        for(CommentsMovie m : listMovie){
            User u = userService.get(m.idUser);
            listData.add(new CommentsData(m, u));
        }
        model.addAttribute("commentsList", listData);

        if(cookie != null){
            UserTokenCookie token = tokenService.get(cookie.getValue());
            if(token != null){
                User user = userService.get(token.idUser);
                AssessmentMovie am = assessmentService.getAssessment(data.movies.id, user.id);
                data.isAssessment = (am != null);
                if(am != null)
                    data.assessmentUser = am.assessment;

                MovieCommentAdd add = new MovieCommentAdd();
                model.addAttribute("addForm", add);
            }
        }

        return "movie";
    }

    @RequestMapping(path = "/movie/add", method = RequestMethod.POST)
    public String addComment(Model model,
                             @CookieValue(name = "userToken", required = false) Cookie cookie,
                             @RequestParam(name = "id")long id,
                             @ModelAttribute(name = "addForm") MovieCommentAdd add){
        if(cookie != null){
            UserTokenCookie token = tokenService.get(cookie.getValue());
            if(token != null) {
                User user = userService.get(token.idUser);
                CommentsMovie commentsMovie = new CommentsMovie();
                commentsMovie.idMovie = id;
                commentsMovie.idUser = user.id;
                commentsMovie.text = add.text;
                commentsMovie.timeCreate = System.currentTimeMillis();
                commentsMovieService.save(commentsMovie);
            }
        }
        return "redirect:/movie/show?id="+id;
    }

    @RequestMapping(path = "/movie/assessment", method = RequestMethod.GET)
    public String assessment(Model model,
                             @CookieValue(name = "userToken", required = false) Cookie cookie,
                             @RequestParam(name = "id")long id,
                             @RequestParam(name = "as")int as){
        if(cookie != null) {
            UserTokenCookie token = tokenService.get(cookie.getValue());
            if (token != null) {
                User user = userService.get(token.idUser);
                AssessmentMovie assessmentMovie = assessmentService.getAssessment(id, user.id);
                boolean a = true;
                if(assessmentMovie != null){
                    assessmentService.delete(assessmentMovie);
                    if(assessmentMovie.assessment == as) a = false;
                    else a = true;
                }
                if(a){
                    assessmentMovie = new AssessmentMovie();
                    assessmentMovie.assessment = as;
                    assessmentMovie.idMovie = id;
                    assessmentMovie.idUser = user.id;
                    assessmentMovie.timeCreate = System.currentTimeMillis();

                    assessmentService.save(assessmentMovie);
                }

            }
        }
        return "redirect:/movie/show?id="+id;
    }
}
