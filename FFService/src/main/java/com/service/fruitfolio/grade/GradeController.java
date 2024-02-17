package com.service.fruitfolio.grade;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/create")
    public Grade create(@RequestBody Grade grade) {
        return gradeService.create(grade);
    }

    @GetMapping("/all")
    public List<Grade> findAll() {
        return gradeService.findAll();
    }

    @GetMapping("/sort_mean_grade/{id}")
    public Double getSortMeanGrade(@PathVariable("id") Integer id) {
        return gradeService.getSortMeanGrade(id);
    }
}