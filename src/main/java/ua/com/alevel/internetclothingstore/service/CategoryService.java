package ua.com.alevel.internetclothingstore.service;

import ua.com.alevel.internetclothingstore.dao.BrandDao;
import ua.com.alevel.internetclothingstore.dao.CategoryDao;
import ua.com.alevel.internetclothingstore.dto.CategoryDTO;
import ua.com.alevel.internetclothingstore.mapper.BrandMapper;
import ua.com.alevel.internetclothingstore.mapper.CategoryMapper;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
}
