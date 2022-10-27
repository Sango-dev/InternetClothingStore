package ua.com.alevel.internetclothingstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.alevel.internetclothingstore.model.Brand;
import ua.com.alevel.internetclothingstore.model.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String title;
    private Double price;
    private String description;
    private String pictureCode;
    private Boolean available;
    private String code;
    private Category category;
    private Brand brand;
}
