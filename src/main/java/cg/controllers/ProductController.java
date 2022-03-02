package cg.controllers;

import cg.model.Category;
import cg.model.Product;
import cg.service.ICategoryService;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @Value("${file-upload}")
    private String fileUpload;

    @Value("${view}")
    private String view;

    @GetMapping
    public ModelAndView showAll(@SortDefault(sort = {"name"}, direction = Sort.Direction.ASC) @PageableDefault(value = 3) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("product/index");
        Page<Product> products = productService.findAll(pageable);
        if (products.isEmpty()) {
            modelAndView.addObject("message", "No Product!!!");
        }
//        String searchByName = "";
//        double firstPrice = 0;
//        double secondPrice = 0;
//        modelAndView.addObject("searchByName", searchByName);
//        modelAndView.addObject("firstPrice", firstPrice);
//        modelAndView.addObject("secondPrice", secondPrice);
        modelAndView.addObject("products", products);
        modelAndView.addObject("view", view);
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }

    @GetMapping("/view")
    ModelAndView getDetail(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("product/detail");
        Product product = productService.findById(id);
        modelAndView.addObject("view", view);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/delete")
    ModelAndView deleteProduct(@RequestParam("id") Long id, @SortDefault(sort = {"name"}, direction = Sort.Direction.ASC) @PageableDefault(value = 3) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("product/index");
        productService.deleteById(id);
        return showAll(pageable);
    }

    @GetMapping("/create-product")
    ModelAndView createProduct() {
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView save(@ModelAttribute Product product){
        ModelAndView modelAndView = new ModelAndView("product/create");
        MultipartFile multipartFile = product.getImageFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(product.getImageFile().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setImageUrl(fileName);
        Product product1 = productService.save(product);
        if (product1 != null){
            modelAndView.addObject("message", "Create Product Successfully !!!");
        }
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editProduct(@RequestParam("id") Long id){
        ModelAndView modelAndView = new ModelAndView("product/edit");
        Product product = productService.findById(id);
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(@RequestParam("id") Long id,@ModelAttribute Product product){
        ModelAndView modelAndView = new ModelAndView("product/edit");
        if (product.getImageFile().getOriginalFilename().equals("")){
            product.setImageFile(productService.findById(id).getImageFile());
            product.setImageUrl(productService.findById(id).getImageUrl());
        } else {
            MultipartFile multipartFile = product.getImageFile();
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(product.getImageFile().getBytes(), new File(fileUpload + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setImageUrl(fileName);
        }
        product.setId(id);
        productService.save(product);
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.addObject("message", "Update Product Successfully !!!");
        return modelAndView;

    }



}
