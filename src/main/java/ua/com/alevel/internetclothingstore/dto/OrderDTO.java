package ua.com.alevel.internetclothingstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.alevel.internetclothingstore.model.OrderStatus;
import ua.com.alevel.internetclothingstore.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private User user;
    private LocalDateTime created;
    private LocalDateTime updated;
    private BigDecimal sum;
    private String address;
    private String recipient;
    private String phone;
    private OrderStatus status;
    private List<OrderDetailDTO> details;
}