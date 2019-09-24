package com.jovana.nsibackend.model;

/**
 * Created by jovana on 05.11.2018
 */
public class OptionVotes {
    private Long optionId;
    private Long voteCount;

    public OptionVotes() {
    }

    public OptionVotes(Long optionId, Long voteCount) {
        this.optionId = optionId;
        this.voteCount = voteCount;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
