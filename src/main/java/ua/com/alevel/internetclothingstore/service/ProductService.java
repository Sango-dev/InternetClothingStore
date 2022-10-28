package ua.com.alevel.internetclothingstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.alevel.internetclothingstore.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll(Pageable pageable);

    void addToUserBucket(String productId, String nickName);

    Page getPage();

    List<ProductDTO> findAllByBrandId(String id);

    List<ProductDTO> findAllByCategoryId(String id);

    List<ProductDTO> findAllByWord(String word);

    ProductDTO findById(String id);

    void updateProduct(String prodId, ProductDTO productDTO);

    void addProduct(ProductDTO dto);

    void updateStateOfAvailability(String id);
}
