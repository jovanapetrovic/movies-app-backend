package com.jovana.nsibackend.resource;

import com.jovana.nsibackend.util.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by jovana on 05.11.2018
 */
public class OptionRequest {
    @NotBlank
    @Size(max = Constants.POLL_OPTION_MAX_LENGTH)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
