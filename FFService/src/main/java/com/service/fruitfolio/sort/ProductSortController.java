package com.service.fruitfolio.sort;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sorts")
@RequiredArgsConstructor
public class ProductSortController {

    private final ProductSortService productSortService;

    @PostMapping("/create")
    public String create(@RequestBody ProductSortCreateRequest createRequest) {
        productSortService.create(createRequest);
        return "OK";
    }

    @GetMapping("/all")
    public List<ProductSort> findAll() {
        return productSortService.findAll();
    }
}
