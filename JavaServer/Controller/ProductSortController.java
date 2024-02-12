package org.example.serverfruitfolio.Controller;

import org.example.serverfruitfolio.Service.ProductSortService;
import org.example.serverfruitfolio.model.ProductSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productSort")
public class ProductSortController {

    private final ProductSortService productSortService;

    @Autowired
    public ProductSortController(ProductSortService productSortService) {
        this.productSortService = productSortService;
    }

    @GetMapping("/all")
    public List<ProductSort> getAllProductSorts() {
        return productSortService.getAllProductSorts();
    }

    @GetMapping("/byId/{id}")
    public ProductSort getProductSortById(@PathVariable("id") Integer id) {
        return productSortService.getProductSortById(id);
    }

    @PostMapping("/create")
    public ProductSort createProductSort(@RequestBody ProductSort productSort) {
        return productSortService.createProductSort(productSort);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductSortById(@PathVariable("id") Integer id) {
        productSortService.deleteProductSortById(id);
    }
}
