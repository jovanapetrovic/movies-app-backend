package com.jovana.nsibackend.resource;

import com.jovana.nsibackend.util.Constants;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * Created by jovana on 05.11.2018
 */
public class PollDuration {
    @NotNull
    @Max(Constants.POLL_DURATION_MAX_DAYS)
    private Integer days;

    @NotNull
    @Max(Constants.POLL_DURATION_MAX_HOURS)
    private Integer hours;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
