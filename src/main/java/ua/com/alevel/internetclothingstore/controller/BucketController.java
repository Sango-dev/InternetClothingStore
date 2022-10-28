package ua.com.alevel.internetclothingstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.internetclothingstore.dto.BucketDTO;
import ua.com.alevel.internetclothingstore.service.BucketService;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BucketController.class);
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String showBucket(Model model, Principal principal) {
        BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
        model.addAttribute("bucket", bucketDTO);
        return "bucket";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete")
    public String deleteProductFromBucket(@RequestParam("id") String productId, Principal principal) {
        bucketService.deleteProductFromBucket(principal.getName(), productId);
        return "redirect:/bucket";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "clear")
    public String clearBucket(Principal principal) {
        bucketService.clearBucket(principal.getName());
        LOGGER.info("User {} has cleared bucket", principal.getName());
        return "redirect:/bucket";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "submit")
    public String saveBucketToOrder(Principal principal) {
        String orderId = bucketService.makeOrder(principal.getName());
        LOGGER.info("User {} has created new order with id: {}", principal.getName(), orderId);
        return "redirect:/orders/" + orderId + "/order-details";
    }
}