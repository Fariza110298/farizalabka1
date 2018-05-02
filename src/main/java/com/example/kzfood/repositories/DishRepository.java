package com.example.kzfood.repositories;

import com.example.kzfood.models.Dish;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DishRepository extends CrudRepository<Dish, Long> {

    public List<Dish> findDishesByCategory_Id(Long categoryId);
}
