package com.jovana.nsibackend.resource;

import javax.validation.constraints.NotNull;

/**
 * Created by jovana on 05.11.2018
 */
public class VoteRequest {
    @NotNull
    private Long optionId;

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }
}
