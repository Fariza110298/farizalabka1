package com.example.kzfood.repositories;

import com.example.kzfood.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public Category findByTitle(String title);
}
