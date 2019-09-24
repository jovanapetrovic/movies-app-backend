package com.jovana.nsibackend.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by jovana on 05.11.2018
 */
public class PollRequest {
    @NotBlank
    @Size(max = 150)
    private String question;

    @NotNull
    @Size(min = 2, max = 6)
    @Valid
    private List<OptionRequest> options;

    @NotNull
    @Valid
    private PollDuration pollDuration;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<OptionRequest> getOptions() {
        return options;
    }

    public void setOptions(List<OptionRequest> options) {
        this.options = options;
    }

    public PollDuration getPollDuration() {
        return pollDuration;
    }

    public void setPollDuration(PollDuration pollDuration) {
        this.pollDuration = pollDuration;
    }
}
