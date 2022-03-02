package cg.service;

import cg.model.Category;
import cg.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);

    void deleteById(Long id);

    Product save(Product product);

    Page<Product> findAllByNameContaining(String name, Pageable pageable);

    Page<Product> findAllByPriceBetween(double firstPrice, double secondPrice, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);


}
