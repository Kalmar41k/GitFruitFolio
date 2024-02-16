package com.service.fruitfolio.sort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSortService {

    private final ProductSortRepository productSortRepository;

    public void create(ProductSortCreateRequest createRequest) {

        ProductSort productSort = new ProductSort();

        productSort.setEnumClass(createRequest.getEnumClass());
        productSort.setType(createRequest.getType());
        productSort.setSort(createRequest.getSort());
        productSortRepository.save(productSort);
    }

    public List<ProductSort> findAll() {
        return productSortRepository.findAll();
    }
}
