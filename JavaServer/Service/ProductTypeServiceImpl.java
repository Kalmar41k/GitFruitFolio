package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.Repository.ProductTypeRepository;
import org.example.serverfruitfolio.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{

    public final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @Override
    public ProductType getProductTypeById(int id) {
        Optional<ProductType> optionalProductType = productTypeRepository.findById(id);
        if (optionalProductType.isEmpty()) {
            throw new IllegalStateException("Product Type with id " + id + " does not exist");
        }
        return optionalProductType.get();
    }

    @Override
    public ProductType createProductType(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    @Override
    public void deleteProductTypeById(int id) {
        productTypeRepository.deleteById(id);
    }
}
