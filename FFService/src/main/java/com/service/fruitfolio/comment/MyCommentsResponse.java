package com.service.fruitfolio.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCommentsResponse {

    private int id;
    private String text;
    private LocalDateTime createDate;
}
