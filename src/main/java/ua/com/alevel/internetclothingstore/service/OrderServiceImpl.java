package ua.com.alevel.internetclothingstore.service;

import org.springframework.stereotype.Service;
import ua.com.alevel.internetclothingstore.dao.OrderDao;
import ua.com.alevel.internetclothingstore.dto.OrderDTO;
import ua.com.alevel.internetclothingstore.mapper.OrderDetailMapper;
import ua.com.alevel.internetclothingstore.mapper.OrderMapper;
import ua.com.alevel.internetclothingstore.model.Order;

import javax.transaction.Transactional;
import java.util.List;
//TODO DONE
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderMapper orderMapper = OrderMapper.MAPPER;
    private final OrderDetailMapper orderDetailMapper = OrderDetailMapper.MAPPER;
    private final OrderDao orderRepository;
    private final UserService userService;

    public OrderServiceImpl(OrderDao orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public String saveOrder(Order order) {
        Order saved = orderRepository.save(order);
        return saved.getId();
    }

    @Override
    public OrderDTO getOrderById(String id) {
        if (id.isBlank()) {
            throw new RuntimeException("ID must not be empty or blank!!!");
        }
        return orderMapper.fromOrder(orderRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public Order saveOrderFromDto(OrderDTO orderDto) {
        Order order = orderMapper.toOrder(orderDto);
        return orderRepository.save(orderMapper.toOrder(orderDto));
    }

    @Override
    public List<OrderDTO> getOrdersByNickName(String nickname) {
        if (nickname.isBlank()) {
            throw new RuntimeException("Nickname must not be empty or blank!!!");
        }
        return orderMapper.fromOrderList(userService.findFirstByNickName(nickname).getOrders());
    }

    @Override
    public Order findOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }
}
