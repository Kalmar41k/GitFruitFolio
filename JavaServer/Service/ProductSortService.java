package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.model.ProductSort;

import java.util.List;

public interface ProductSortService {

    List<ProductSort> getAllProductSorts();
    ProductSort getProductSortById(int id);
    ProductSort createProductSort(ProductSort productSort);
    void deleteProductSortById(int id);
}
