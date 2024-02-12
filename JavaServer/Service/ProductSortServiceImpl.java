package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.Repository.ProductSortRepository;
import org.example.serverfruitfolio.model.ProductSort;
import org.example.serverfruitfolio.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSortServiceImpl implements ProductSortService {

    private final ProductSortRepository productSortRepository;

    @Autowired
    public ProductSortServiceImpl(ProductSortRepository productSortRepository) {
        this.productSortRepository = productSortRepository;
    }

    @Override
    public List<ProductSort> getAllProductSorts() {
        return productSortRepository.findAll();
    }

    @Override
    public ProductSort getProductSortById(int id) {
        Optional<ProductSort> optionalProductSort = productSortRepository.findById(id);
        if (optionalProductSort.isEmpty()) {
            throw new IllegalStateException("Product Sort with id " + id + " does not exist");
        }
        return optionalProductSort.get();
    }
    @Override
    public ProductSort createProductSort(ProductSort productSort) {
        return productSortRepository.save(productSort);
    }

    @Override
    public void deleteProductSortById(int id) {
        productSortRepository.deleteById(id);
    }
}
