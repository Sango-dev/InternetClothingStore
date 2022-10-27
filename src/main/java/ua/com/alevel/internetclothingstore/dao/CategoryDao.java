package ua.com.alevel.internetclothingstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.internetclothingstore.model.Category;

public interface CategoryDao extends JpaRepository<Category, String> {
}
