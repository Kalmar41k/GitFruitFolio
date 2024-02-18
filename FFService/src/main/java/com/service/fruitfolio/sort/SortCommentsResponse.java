package com.service.fruitfolio.sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortCommentsResponse {

    private Integer id;
    private String text;
    private Integer userId;
    private String firstname;
    private String lastname;
    private LocalDateTime createDate;
}
