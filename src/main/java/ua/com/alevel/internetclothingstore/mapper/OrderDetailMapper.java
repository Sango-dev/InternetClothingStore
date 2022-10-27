package ua.com.alevel.internetclothingstore.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.com.alevel.internetclothingstore.dto.OrderDetailDTO;
import ua.com.alevel.internetclothingstore.model.OrderDetails;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    OrderDetailMapper MAPPER = Mappers.getMapper(OrderDetailMapper.class);

    OrderDetails toOrderDetail(OrderDetailDTO dto);

    @InheritInverseConfiguration
    OrderDetailDTO fromOrderDetail(OrderDetails orderDetail);

    List<OrderDetails> toOrderDetailList(List<OrderDetailDTO> dtos);

    List<OrderDetailDTO> fromOrderDetailList(List<OrderDetails> orders);
}