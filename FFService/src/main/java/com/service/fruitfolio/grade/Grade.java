package com.service.fruitfolio.grade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.fruitfolio.sort.ProductSort;
import com.service.fruitfolio.user.User;
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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Grade_Sort_ID")
    private ProductSort productSort;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Grade_User_ID")
    private User user;

    private Integer grade;

}