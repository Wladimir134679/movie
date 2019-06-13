package com.art.service.movie.controllers;

import com.art.service.movie.form.*;
import com.art.service.movie.services.*;
import com.art.service.movie.tables.AssessmentMovie;
import com.art.service.movie.tables.CommentsMovie;
import com.art.service.movie.tables.User;
import com.art.service.movie.tables.UserTokenCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ProfileController {

    @Resource(name = "userService")
    public UserService userService;
    @Resource(name = "tokenService")
    public TokenService tokenService;
    @Autowired
    public AssessmentService assessmentService;
    @Autowired
    public CommentsMovieService commentsMovieService;
    @Autowired
    public MovieService movieService;

    @RequestMapping(path = "/reg", method = RequestMethod.GET)
    public String registration(Model model){
        UserRegForm userForm = new UserRegForm();
        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public String reg(Model model,
                      @ModelAttribute("userForm")UserRegForm userRegForm,
                      HttpServletResponse response){
        if(!userRegForm.password.equals(userRegForm.password1)){
            model.addAttribute("error", "Пароли не совпадают");
            return "registration";
        }
        User user = new User();
        user.email = userRegForm.email;
        user.nickname = userRegForm.nickname;
        user.password = userRegForm.password;
        userService.save(user);

        createCookieAuth(user, response);

        return "redirect:/profile";
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String getProfile(Model model,
                             @CookieValue(name = "userToken", required = false)Cookie cookie){
        if(cookie == null){
            return "redirect:/auth";
        }else{
            UserTokenCookie tokenCookie = tokenService.get(cookie.getValue());
            User user = userService.get(tokenCookie.idUser);
            if(user == null)
                return "redirect:/index";
            UserData data = new UserData(user);
            model.addAttribute("userData", data);

            List<CommentsData> commentsDataList = new ArrayList<>();
            List<CommentsMovie> commentsMovieList = commentsMovieService.getByUser(user.id);
            for(CommentsMovie comment : commentsMovieList){
                CommentsData dataComments = new CommentsData(comment);
                dataComments.movie = movieService.get(comment.idMovie);
                commentsDataList.add(dataComments);
            }
            model.addAttribute("commentList", commentsDataList);

            List<AssessmentMovie> assessmentMovieList = assessmentService.getByUser(user.id);
            List<AssessmentData> assessmentDataList = new ArrayList<>();
            for(AssessmentMovie assessmentMovie : assessmentMovieList){
                AssessmentData assessmentData = new AssessmentData();
                assessmentData.movies = movieService.get(assessmentMovie.idMovie);
                assessmentData.assessmentMovieList = assessmentService.getByMovie(assessmentData.movies.id);
                assessmentData.assessmentMovie = assessmentMovie;
                assessmentDataList.add(assessmentData);
            }
            model.addAttribute("assessmentList", assessmentDataList);


            return "profile";
        }
    }

    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public String auth(Model model){
        UserAuthForm form = new UserAuthForm();
        model.addAttribute("userForm", form);
        return "auth";
    }

    @RequestMapping(path = "/api/auth", method = RequestMethod.POST)
    public String authPost(Model model,
                           @ModelAttribute("userForm")UserAuthForm form,
                           HttpServletResponse response){
        User user = userService.findByEmailAndPassword(form.email, form.password);
        if(user == null){
            model.addAttribute("message", "error");
            return "auth";
        }else{
            createCookieAuth(user, response);
            return "redirect:/profile";
        }
    }

    private void createCookieAuth(User user, HttpServletResponse response){
        UserTokenCookie tokenCookie = new UserTokenCookie();
        tokenCookie.idUser = user.id;
        tokenCookie.age = 1000*60;
        tokenCookie.timeCreate = System.currentTimeMillis();
        tokenCookie.token = UUID.randomUUID().toString();
        tokenService.save(tokenCookie);

        Cookie cookie = new Cookie("userToken", tokenCookie.token);
        cookie.setPath("/");
        cookie.setMaxAge(tokenCookie.age);
        response.addCookie(cookie);
    }

    @RequestMapping(path="/profile/exit", method = RequestMethod.GET)
    public String exit(@CookieValue(name = "userToken", required = false)Cookie cookie,
                       HttpServletResponse response,
                       HttpServletRequest request){

        UserTokenCookie token = tokenService.get(cookie.getValue());
        if(token != null){
            User user = userService.get(token.idUser);
            if(user != null){
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                tokenService.delete(token);

                return "redirect:/index";
            }
        }
        return "redirect:/"+request.getHeader("referer");
    }

}
