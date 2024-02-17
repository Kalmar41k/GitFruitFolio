package com.service.fruitfolio.grade;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Grade {

    @Id
    @GeneratedValue
    public Integer id;

    private Integer userId;

//    @ManyToOne
//    @JoinColumn(name = "Grade_Sort_ID")
//    private ProductSort productSort;

    private Integer productSortId;

    public Integer grade;

}