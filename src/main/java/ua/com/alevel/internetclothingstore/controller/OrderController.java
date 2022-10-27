package ua.com.alevel.internetclothingstore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.internetclothingstore.dto.OrderDTO;
import ua.com.alevel.internetclothingstore.dto.OrderStatusDTO;
import ua.com.alevel.internetclothingstore.model.Order;
import ua.com.alevel.internetclothingstore.model.OrderStatus;
import ua.com.alevel.internetclothingstore.service.OrderService;
import ua.com.alevel.internetclothingstore.service.UserService;

import java.security.Principal;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/order-details")
    public String getRecipientData(@PathVariable String id, Model model) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        model.addAttribute("order", orderDTO);
        return "orderDetails";
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/make-order")
    public String setData(OrderDTO orderDTO) {
        orderService.saveOrderFromDto(orderDTO);
        return "redirect:/orders/success-order";
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/success-order")
    public String successOrder() {
        return "successOrder";
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/order-history")
    public String getOrderHistoryByUserId(Principal principal, Model model) {
        List<OrderDTO> dtos = orderService.getOrdersByNickName(principal.getName());
        model.addAttribute("orders", dtos);
        return "ordersHistory";
    }

    //TODO DONE
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

    //TODO DONE
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{nickname}/order-history", params = "submit")
    public String updateStatus(@PathVariable String nickname, @ModelAttribute("elect") OrderStatusDTO dto) {
        Order order = orderService.findOrderById(dto.getId());
        order.setStatus(dto.getStatus());
        orderService.saveOrder(order);
        return "redirect:/orders/{nickname}/order-history";
    }
}