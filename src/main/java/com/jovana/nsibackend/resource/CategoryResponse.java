package com.jovana.nsibackend.resource;

import com.jovana.nsibackend.model.Category;

/**
 * Created by jovana on 14.11.2018
 */
public class CategoryResponse {

    private Long id;
    private String name;

    public CategoryResponse() {
    }

    public CategoryResponse(Category c) {
        this.id = c.getId();
        this.name = c.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
