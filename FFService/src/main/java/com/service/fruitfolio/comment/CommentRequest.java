package com.service.fruitfolio.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private Integer productSortId;
    private Integer userId;
    private String text;
}
