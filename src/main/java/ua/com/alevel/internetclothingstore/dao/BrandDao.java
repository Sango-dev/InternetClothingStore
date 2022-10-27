package ua.com.alevel.internetclothingstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.internetclothingstore.model.Brand;

public interface BrandDao extends JpaRepository<Brand, String> {
}
