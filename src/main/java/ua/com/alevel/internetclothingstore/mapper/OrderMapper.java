package ua.com.alevel.internetclothingstore.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.com.alevel.internetclothingstore.dto.OrderDTO;
import ua.com.alevel.internetclothingstore.model.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderDTO dto);

    @InheritInverseConfiguration
    OrderDTO fromOrder(Order order);

    List<Order> toOrderList(List<OrderDTO> dtos);

    List<OrderDTO> fromOrderList(List<Order> orders);

}