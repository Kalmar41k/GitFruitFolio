package com.service.fruitfolio.grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeRequest {

    private Integer productSortId;
    private Integer userId;
    private Integer grade;
}
