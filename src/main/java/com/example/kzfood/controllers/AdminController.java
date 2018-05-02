package com.example.kzfood.controllers;

import com.example.kzfood.models.Category;
import com.example.kzfood.models.Dish;
import com.example.kzfood.repositories.CategoryRepository;
import com.example.kzfood.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "admin")
public class AdminController {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping
    public String adminPage(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("dishList", dishRepository.findAll());
        return "admin-page";
    }

    @RequestMapping("categories")
    public String categoriesPage(Model model) {
        return "admin-categories";
    }


    @RequestMapping(path = "/get_categories", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Category> getCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @RequestMapping(value = "/add_category", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/remove_category/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return id.toString();
    }

    @RequestMapping(path = "/get_dishes", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Dish> getDishes() {
        return (List<Dish>) dishRepository.findAll();
    }

    @RequestMapping(value = "/add_dish", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addDish(@RequestBody Dish dish) {
        System.out.println(dish.getCategory().toString());
        Category category = categoryRepository.findByTitle(dish.getCategory().getTitle());
        Dish newDish = new Dish(dish.getTitle(), category, dish.getPrice());
        dishRepository.save(newDish);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/remove_dish/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeDish(@PathVariable Long id){
        dishRepository.deleteById(id);
        return id.toString();
    }



    @RequestMapping("/login")
    public String adminLogin() {
        return "admin-login";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

}