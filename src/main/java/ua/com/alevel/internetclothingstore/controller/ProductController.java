package ua.com.alevel.internetclothingstore.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.internetclothingstore.dto.ProductDTO;
import ua.com.alevel.internetclothingstore.service.BrandService;
import ua.com.alevel.internetclothingstore.service.CategoryService;
import ua.com.alevel.internetclothingstore.service.ProductService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    //TODO DONE
    @GetMapping
    public String list(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        model.addAttribute("products", productService.findAll(pageable));
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        List<Integer> pageNumbers = IntStream.range(0, productService.getPage().getTotalPages())
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());
        model.addAttribute("pages", pageNumbers);
        return "products";
    }

    //TODO DONE
    @RequestMapping("/filter-brand")
    public String filterBrand(Model model, @RequestParam("id") String id) {
        model.addAttribute("products", productService.findAllByBrandId(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("pages", Collections.emptyList());
        return "products";
    }

    //TODO DONE
    @RequestMapping("/filter-category")
    public String filterCategory(Model model, @RequestParam("id") String id) {
        model.addAttribute("products", productService.findAllByCategoryId(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("pages", Collections.emptyList());
        return "products";
    }

    //TODO DONE
    @RequestMapping("/find-by-word")
    public String search(Model model, @RequestParam("word") String word) {
        if (word.isBlank()) {
            model.addAttribute("products", Collections.emptyList());
        } else {
            model.addAttribute("products", productService.findAllByWord(word));
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("pages", Collections.emptyList());
        return "products";
    }

    //TODO DONE
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable String id, Principal principal) {
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

    //TODO DONE
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit-product")
    public String editProd(Model model, @PathVariable("id") String prodId) {
        ProductDTO productDTO = productService.findById(prodId);
        if (productDTO != null) {
            model.addAttribute("method", "edit-product");
            model.addAttribute("product", productDTO);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return "productEdit";
        }
        return "redirect:/products";
    }

    //TODO DONE
    //TODO LOGGING
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{id}/edit-product", params = "submit")
    public String editProd(@PathVariable("id") String prodId, @ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("method", "edit-product");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return "productEdit";
        }
        productService.updateProduct(prodId, productDTO);
        return "redirect:/products";
    }

    //TODO DONE
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{id}/edit-product", params = "cancellation")
    public String cancelEditingProd(@PathVariable("id") String prodId) {
        return "redirect:/products";
    }

    //TODO DONE
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("method", "add-product");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("brands", brandService.findAll());
        return "productEdit";
    }

    //TODO DONE
    //TODO LOGGING
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/add", params = "submit")
    public String addProduct(@ModelAttribute("product") ProductDTO productDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("method", "add-product");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return "productEdit";
        }
        productService.addProduct(productDTO);
        return "redirect:/products";
    }

    //TODO DONE
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/add", params = "cancellation")
    public String cancelAddingProd() {
        return "redirect:/products";
    }

    //TODO DONE
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/update-available")
    public String updateStateOfAvailability(@PathVariable String id, Principal principal) {
        productService.updateStateOfAvailability(id);
        return "redirect:/products";
    }
}
