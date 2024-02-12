package org.example.serverfruitfolio.Controller;

import org.example.serverfruitfolio.Service.ProductTypeService;
import org.example.serverfruitfolio.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping("/all")
    public List<ProductType> getAllProductTypes() {
        return productTypeService.getAllProductTypes();
    }

    @GetMapping("/byId/{id}")
    public ProductType getProductTypeById(@PathVariable("id") Integer id) {
        return productTypeService.getProductTypeById(id);
    }

    @PostMapping("/create")
    public ProductType createProductType(@RequestBody ProductType productType) {
        return productTypeService.createProductType(productType);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductTypeById(@PathVariable("id") Integer id) {
        productTypeService.deleteProductTypeById(id);
    }
}
