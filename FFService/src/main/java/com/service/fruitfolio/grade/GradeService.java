package com.service.fruitfolio.grade;

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
    private final ProductSortRepository productSortRepository;

    public Double create(GradeRequest gradeRequest, Principal connectedUser) {

        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<ProductSort> productSort = productSortRepository.findById(gradeRequest.getProductSortId());
        if (productSort.isEmpty()) {
            throw new IllegalStateException("Product Sort is not found!");
        }

        Grade grade = new Grade();
        Optional<Grade> updateGrade = Optional.ofNullable(gradeRepository.findByUserIdAndProductSortId(
                user.getId(), gradeRequest.getProductSortId()));
        if (updateGrade.isPresent()) {
            updateGrade.get().setGrade(gradeRequest.getGrade());
            gradeRepository.save(updateGrade.orElse(null));
        }

        else {
            grade.setUser(user);
            grade.setProductSort(productSort.get());
            grade.setGrade(gradeRequest.getGrade());
            gradeRepository.save(grade);
        }

        List<Grade> grades = getSortGrades(productSort.get());
        Double meanGrade = grades.stream().mapToInt(Grade::getGrade).average().orElse(0);
        productSort.get().setMeanGrade(meanGrade);
        productSortRepository.save(productSort.get());
        return meanGrade;
    }


    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public List<Grade> getSortGrades(ProductSort productSort) {
        return gradeRepository.getByProductSort(productSort);
    }
}
