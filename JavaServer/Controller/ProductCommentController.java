package org.example.serverfruitfolio.Controller;

import org.example.serverfruitfolio.Service.ProductCommentService;
import org.example.serverfruitfolio.model.ProductComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/productComment")
public class ProductCommentController {

    private final ProductCommentService productCommentService;

    @Autowired
    public ProductCommentController(ProductCommentService productCommentService) {
        this.productCommentService = productCommentService;
    }

    @GetMapping("/all")
    public List<ProductComment> getAllProductComments() {
        return productCommentService.getAllProductComments();
    }

    @GetMapping("/byId/{id}")
    public ProductComment getProductCommentById(@PathVariable("id") Integer id) {
        return productCommentService.getProductCommentById(id);
    }

    @PostMapping("/create")
    public ProductComment createProductComment(@RequestBody ProductComment productComment) {
        productComment.setDateTime(OffsetDateTime.now());
        return productCommentService.createProductComment(productComment);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductCommentById(@PathVariable("id") Integer id) {
        productCommentService.deleteProductCommentById(id);
    }
}
