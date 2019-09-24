package com.jovana.nsibackend.repository;

import com.jovana.nsibackend.model.Category;
import com.jovana.nsibackend.resource.CategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Created by jovana on 06.11.2018
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("select new com.jovana.nsibackend.resource.CategoryResponse(c) from Category c")
    Set<CategoryResponse> findAllCategories();

}
