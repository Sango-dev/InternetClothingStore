package ua.com.alevel.internetclothingstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.internetclothingstore.model.Order;

public interface OrderDao extends JpaRepository<Order, String> {
}
