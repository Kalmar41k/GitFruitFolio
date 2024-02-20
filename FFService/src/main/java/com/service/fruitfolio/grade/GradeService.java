package com.service.fruitfolio.grade;

import com.service.fruitfolio.comment.Comment;
import com.service.fruitfolio.sort.ProductSort;
import com.service.fruitfolio.sort.ProductSortRepository;
import com.service.fruitfolio.user.User;
import com.service.fruitfolio.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final ProductSortRepository productSortRepository;

    public Grade create(GradeRequest gradeRequest, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Grade grade = new Grade();
        Optional<Grade> checkGrade = Optional.ofNullable(gradeRepository.findByUserId(user.getId()));
        if (checkGrade.isPresent()) {
            grade = checkGrade.orElse(null);
            grade.setId(checkGrade.get().getId());
            grade.setGrade(gradeRequest.getGrade());
            gradeRepository.save(grade);

            Optional<ProductSort> productSort = productSortRepository.findById(gradeRequest.getProductSortId());
            if (productSort.isEmpty()) {
                return null;
            }
            ProductSort productSort1;
            productSort1 = productSort.orElse(null);
            List<Grade> grades = getSortGrades(productSort.orElse(null));
            Integer sumGrades = 0;
            for (Grade grade1 : grades) {
                sumGrades += grade1.getGrade();
            }
            Double meanGrade = (double) sumGrades / grades.size();
            productSort1.setMeanGrade(meanGrade);
            productSortRepository.save(productSort1);
            return grade;
        }
        else {

            Optional<ProductSort> productSort = productSortRepository.findById(gradeRequest.getProductSortId());
            if (productSort.isEmpty()) {
                return null;
            }
            grade.setUser(user);
            grade.setProductSort(productSort.orElse(null));
            grade.setGrade(gradeRequest.getGrade());
            gradeRepository.save(grade);

            ProductSort productSort1;
            productSort1 = productSort.orElse(null);
            List<Grade> grades = getSortGrades(productSort.orElse(null));
            Integer sumGrades = 0;
            for (Grade grade1 : grades) {
                sumGrades += grade1.getGrade();
            }
            Double meanGrade = (double) sumGrades / grades.size();
            productSort1.setMeanGrade(meanGrade);
            productSortRepository.save(productSort1);
            return grade;
        }

    }

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public List<Grade> getSortGrades(ProductSort productSort) {
        return gradeRepository.getByProductSort(productSort);
    }
}
