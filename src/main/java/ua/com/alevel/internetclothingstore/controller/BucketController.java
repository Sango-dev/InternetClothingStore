package ua.com.alevel.internetclothingstore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.internetclothingstore.dto.BucketDTO;
import ua.com.alevel.internetclothingstore.service.BucketService;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String showBucket(Model model, Principal principal) {
        BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
        model.addAttribute("bucket", bucketDTO);
        return "bucket";
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete")
    public String deleteProductFromBucket(@RequestParam("id") String productId, Principal principal) {
        bucketService.deleteProductFromBucket(principal.getName(), productId);
        return "redirect:/bucket";
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "clear")
    public String clearBucket(Principal principal) {
        bucketService.clearBucket(principal.getName());
        return "redirect:/bucket";
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "submit")
    public String saveBucketToOrder(Principal principal) {
        String orderId = bucketService.makeOrder(principal.getName());
        return "redirect:/orders/" + orderId + "/order-details";
    }
}