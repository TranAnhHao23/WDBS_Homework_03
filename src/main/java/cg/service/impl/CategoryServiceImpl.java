package cg.service.impl;

import cg.model.Category;
import cg.repository.ICategoryRepository;
import cg.repository.IProductRepository;
import cg.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IProductRepository productRepository;


    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Category category = findById(id);
        productRepository.deleteAllByCategory(category);
        categoryRepository.deleteById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
