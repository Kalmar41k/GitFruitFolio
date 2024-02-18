package com.service.fruitfolio.sort;

import com.service.fruitfolio.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sorts")
@RequiredArgsConstructor
public class ProductSortController {

    private final ProductSortService productSortService;

    @PostMapping("/create")
    public ProductSort create(@RequestBody ProductSortCreateRequest createRequest) {
        return productSortService.create(createRequest);
    }

    @GetMapping("/all")
    public List<ProductSort> findAll() {
        return productSortService.findAll();
    }

    @GetMapping("/sort_comments/{id}")
    public List<SortCommentsResponse> getSortCommentsById(@PathVariable("id") Integer id) {
        return productSortService.getSortCommentsById(id);
    }

    @GetMapping("/byId/{id}")
    public ProductSort findById(@PathVariable("id") Integer id) {
        return productSortService.findById(id);
    }
}
