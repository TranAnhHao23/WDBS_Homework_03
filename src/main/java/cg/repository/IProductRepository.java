package cg.repository;

import cg.model.Category;
import cg.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findAllByNameContaining(String name, Pageable pageable);

    Page<Product> findAllByPriceBetween(double firstPrice, double secondPrice, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);
}
