package com.jovana.nsibackend.resource;

/**
 * Created by jovana on 05.11.2018
 */
public class OptionResponse {
    private long id;
    private String text;
    private long voteCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }
}
