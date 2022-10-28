package ua.com.alevel.internetclothingstore.service;

import org.springframework.stereotype.Service;
import ua.com.alevel.internetclothingstore.dao.CategoryDao;
import ua.com.alevel.internetclothingstore.dto.CategoryDTO;
import ua.com.alevel.internetclothingstore.mapper.CategoryMapper;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper = CategoryMapper.MAPPER;
    private final CategoryDao categoryRepository;

    public CategoryServiceImpl(CategoryDao categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryMapper.fromCategoryList(categoryRepository.findAll());
    }
}
