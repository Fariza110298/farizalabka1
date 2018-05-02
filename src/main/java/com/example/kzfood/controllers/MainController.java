package com.example.kzfood.controllers;

import com.example.kzfood.models.Category;
import com.example.kzfood.repositories.CategoryRepository;
import com.example.kzfood.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DishRepository dishRepository;

    @RequestMapping("/")
    public String homePage(Model model) {
        model.addAttribute("dishList", dishRepository.findAll());
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "index";
    }
    @RequestMapping("/delivery")
    public String delivery(){
        return "delivery";
    }
    @RequestMapping("/order")
    public String order(){
        return "order";
    }

    @RequestMapping("/{categoryId}")
    public String homePageFilterCategory(Model model, @PathVariable Long categoryId) {
        model.addAttribute("dishList", dishRepository.findDishesByCategory_Id(categoryId));
        model.addAttribute("categoryList", categoryRepository.findAll());
        Optional<Category> category = categoryRepository.findById(categoryId);
        model.addAttribute("categoryOpt", category);
        return "index";
    }

}
