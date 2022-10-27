package ua.com.alevel.internetclothingstore.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.com.alevel.internetclothingstore.dto.CategoryDTO;
import ua.com.alevel.internetclothingstore.model.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryDTO dto);

    @InheritInverseConfiguration
    CategoryDTO fromCategory(Category category);

    List<Category> toCategoryList(List<CategoryDTO> dtos);

    List<CategoryDTO> fromCategoryList(List<Category> categories);
}
