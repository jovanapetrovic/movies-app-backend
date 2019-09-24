package com.jovana.nsibackend.util;

/**
 * Created by jovana on 05.11.2018
 */
public class Constants {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "30";

    public static final int MAX_PAGE_SIZE = 50;

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 50;
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 15;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 20;
    public static final int EMAIL_MAX_LENGTH = 50;

    public static final int POLL_DURATION_MAX_DAYS = 7;
    public static final int POLL_DURATION_MAX_HOURS = 23;
    public static final int POLL_QUESTION_MAX_LENGTH = 150;
    public static final int POLL_OPTION_MAX_LENGTH = 50;
    public static final int MIN_OPTIONS = 2;
    public static final int MAX_OPTIONS = 6;

    public static final int CATEGORY_NAME_MAX_LENGTH = 50;

    public static final int MOVIE_NAME_MAX_LENGTH = 50;
    public static final int MOVIE_YEAR_MIN = 1910;
    public static final int MOVIE_YEAR_MAX = 2100;
    public static final int MOVIE_RATING_MIN = 1;
    public static final int MOVIE_RATING_MAX = 10;
    public static final int MOVIE_COMMENT_MAX_LENGTH = 150;

    private Constants() {}

}
