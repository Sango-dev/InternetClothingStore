package ua.com.alevel.internetclothingstore.service;

import ua.com.alevel.internetclothingstore.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
}
