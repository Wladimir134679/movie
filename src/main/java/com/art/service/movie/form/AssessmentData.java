package com.art.service.movie.form;

import com.art.service.movie.tables.AssessmentMovie;
import com.art.service.movie.tables.Movies;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AssessmentData {

    public List<AssessmentMovie> assessmentMovieList;
    public AssessmentMovie assessmentMovie;
    public Movies movies;

    public String getMovie(){
        return movies.name;
    }

    public float getMovieAssessment(){
        if(assessmentMovieList.size() == 0)
            return 0;
        float s = 0;
        for(AssessmentMovie am : assessmentMovieList){
            s+= am.assessment;
        }
        return (s/assessmentMovieList.size());
    }

    public int getUserAssessment(){
        return assessmentMovie.assessment;
    }

    public String getTime(){
        String pattern = "HH:mm / dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();
        date.setTime(assessmentMovie.timeCreate);
        return format.format(date);
    }
}
