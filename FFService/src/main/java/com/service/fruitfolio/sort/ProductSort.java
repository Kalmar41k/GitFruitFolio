package com.service.fruitfolio.sort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.fruitfolio.comment.Comment;
import com.service.fruitfolio.grade.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductSort {

    @Id
    @GeneratedValue
    private Integer id;
    private ProductClassEnum enumClass;
    private String type;
    private String sort;

    @JsonIgnore
    @OneToMany(mappedBy = "productSort")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "productSort")
    private List<Grade> grades;

    private Double meanGrade;

}