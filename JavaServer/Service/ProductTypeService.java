package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.model.ProductType;

import java.util.List;

public interface ProductTypeService {
    List<ProductType> getAllProductTypes();

    ProductType getProductTypeById(int id);
    ProductType createProductType(ProductType productType);

    void deleteProductTypeById(int id);
}
