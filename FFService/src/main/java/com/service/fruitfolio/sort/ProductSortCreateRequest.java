package com.service.fruitfolio.sort;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSortCreateRequest {
    private int id;
    private ProductClassEnum enumClass;
    private String type;
    private String sort;
    private Double meanGrade;
}
