package com.service.fruitfolio.grade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public Grade create(Grade grade) {
        Optional<Grade> checkGrade = Optional.ofNullable(gradeRepository.findByUserId(grade.getUserId()));
        if (checkGrade.isPresent()) {
            grade.setId(checkGrade.get().getId());
            return gradeRepository.save(grade);
        }
        return gradeRepository.save(grade);

    }

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public Double getSortMeanGrade(Integer id) {

        List<Grade> grades = gradeRepository.findByProductSortId(id);
        Integer sumOfGrades = 0;

        for (Grade grade : grades) {
            sumOfGrades += grade.getGrade();
        }

        Double meanGrade = (double) sumOfGrades / grades.size();
        return meanGrade;
    }
}
