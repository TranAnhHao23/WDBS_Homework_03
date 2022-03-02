package cg.controllers;

import cg.model.Category;
import cg.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ModelAndView showAllCategory() {
        ModelAndView modelAndView = new ModelAndView("category/list");
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("message", "success");
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView deleteCategory(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("category/list");
        categoryService.deleteById(id);
        return showAllCategory();
    }

    @GetMapping("/create-category")
    public ModelAndView createCategory() {
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@Validated @ModelAttribute Category category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("category/create");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        if (category != null) {
            modelAndView.addObject("message", "Create Success!!!");
            categoryService.save(category);
        }
        return modelAndView;
    }
}
