package ua.com.alevel.internetclothingstore.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.alevel.internetclothingstore.model.Product;

import java.util.List;


public interface ProductDao extends JpaRepository<Product, String> {
    @Override
    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByBrandId(String id);

    List<Product> findAllByCategoryId(String id);

    @Query(value = "select * from products p where lower(p.title) like %:word% or lower(p.description) like %:word% or lower(p.code) like %:word%", nativeQuery = true)
    List<Product> findAllByWord(@Param("word") String word);
}
