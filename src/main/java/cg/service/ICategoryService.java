package cg.service;

import cg.model.Category;
import cg.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    Iterable<Category> findAll();

    Category findById(Long id);

    void deleteById(Long id);

    Category save(Category category);
}
