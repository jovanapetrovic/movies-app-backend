package com.jovana.nsibackend.resource;

/**
 * Created by jovana on 05.11.2018
 */
public class UsernameOrEmailAvailability {
    private Boolean available;

    public UsernameOrEmailAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
