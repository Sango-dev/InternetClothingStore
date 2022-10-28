package ua.com.alevel.internetclothingstore.service;

import ua.com.alevel.internetclothingstore.dto.OrderDTO;
import ua.com.alevel.internetclothingstore.model.Order;

import java.util.List;

public interface OrderService {
    String saveOrder(Order order);

    OrderDTO getOrderById(String id);

    Order findOrderById(String id);

    Order saveOrderFromDto(OrderDTO orderDto);

    List<OrderDTO> getOrdersByNickName(String nickName);
}
