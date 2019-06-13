package com.art.service.movie.controllers;

import com.art.service.movie.form.UserData;
import com.art.service.movie.services.TokenService;
import com.art.service.movie.services.UserService;
import com.art.service.movie.tables.User;
import com.art.service.movie.tables.UserTokenCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.Cookie;

@ControllerAdvice
public class FragmentAttributes {

    @Autowired
    public TokenService tokenService;
    @Autowired
    public UserService userService;

    @ModelAttribute("header")
    public void header(Model model,
                       @CookieValue(name = "userToken", required = false) Cookie cookie){
        if(cookie != null){
            UserTokenCookie token = tokenService.get(cookie.getValue());
            if(token != null){
                User user = userService.get(token.idUser);
                if(user != null){
                    UserData data = new UserData(user);
                    model.addAttribute("headerUserData", data);
                }
            }
        }
    }
}
