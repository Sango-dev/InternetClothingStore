package ua.com.alevel.internetclothingstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.internetclothingstore.dto.OrderDTO;
import ua.com.alevel.internetclothingstore.dto.OrderStatusDTO;
import ua.com.alevel.internetclothingstore.model.Order;
import ua.com.alevel.internetclothingstore.model.OrderStatus;
import ua.com.alevel.internetclothingstore.service.OrderService;

import java.security.Principal;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/order-details")
    public String getRecipientData(@PathVariable String id, Model model) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        model.addAttribute("order", orderDTO);
        model.addAttribute("flag", "true");
        return "orderDetails";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/order-information")
    public String showOrderByID(@PathVariable String id, Model model) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        model.addAttribute("order", orderDTO);
        model.addAttribute("flag", "false");
        return "orderDetails";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/make-order")
    public String setData(OrderDTO orderDTO) {
        orderService.saveOrderFromDto(orderDTO);
        LOGGER.info("Recipient's data has been successfully saved for order with id {}", orderDTO.getId());
        return "redirect:/orders/success-order";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/success-order")
    public String successOrder() {
        return "successOrder";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/order-history")
    public String getOrderHistoryByUserId(Principal principal, Model model) {
        List<OrderDTO> dtos = orderService.getOrdersByNickName(principal.getName());
        model.addAttribute("orders", dtos);
        return "ordersHistory";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{nickname}/order-history")
    public String getOrderHistoryByUserId(@PathVariable String nickname, Model model) {
        List<OrderDTO> dtos = orderService.getOrdersByNickName(nickname);
        model.addAttribute("orders", dtos);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("elect", new OrderStatusDTO());
        model.addAttribute("flag", true);
        return "ordersHistory";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{nickname}/order-history", params = "submit")
    public String updateStatus(@PathVariable String nickname, @ModelAttribute("elect") OrderStatusDTO dto) {
        Order order = orderService.findOrderById(dto.getId());
        order.setStatus(dto.getStatus());
        orderService.saveOrder(order);
        LOGGER.info("Order status for {} order with id {} has been successfully updated", nickname, dto.getId());
        return "redirect:/orders/{nickname}/order-history";
    }
}