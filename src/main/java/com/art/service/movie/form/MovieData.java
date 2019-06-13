package com.art.service.movie.form;

import com.art.service.movie.tables.AssessmentMovie;
import com.art.service.movie.tables.Movies;

import java.util.List;

public class MovieData {

    public Movies movies;
    public int numComments;
    public double assessment;
    public List<AssessmentMovie> assessmentMovieList;
    public boolean isAssessment = false;

    public int assessmentUser = 0;

    public MovieData(Movies movies) {
        this.movies = movies;
    }

    public long getId(){
        return movies.id;
    }

    public String getName(){
        return movies.name;
    }

    public String getDescription(){
        return movies.description;
    }

    public int getNumComments(){
        return numComments;
    }

    public double getAssessment(){
        return assessment;
    }

    public int getAssessmentNum(int i){
        int num = 0;
        for(AssessmentMovie am : assessmentMovieList){
            if(am.assessment == i)
                num++;
        }
        return num;
    }

    public boolean isAssessmentUser(){
        return isAssessment;
    }

    public int getAssessmentUser(){
        return assessmentUser;
    }

    public boolean isAssessmentUser(int i){
        return (assessmentUser == i);
    }
}
