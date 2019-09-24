package com.jovana.nsibackend.resource;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Created by jovana on 05.11.2018
 */
public class PollResponse {
    private Long id;
    private String question;
    private List<OptionResponse> options;
    private UserInfo createdBy;
    private Instant dateCreated;
    private Instant expirationDateTime;
    private Boolean isExpired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long selectedOption;
    private Long totalVotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<OptionResponse> getOptions() {
        return options;
    }

    public void setOptions(List<OptionResponse> options) {
        this.options = options;
    }

    public UserInfo getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserInfo createdBy) {
        this.createdBy = createdBy;
    }


    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Long getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Long selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Long totalVotes) {
        this.totalVotes = totalVotes;
    }
}
