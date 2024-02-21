package com.service.fruitfolio.grade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/create")
    public Grade create(@RequestBody GradeRequest gradeRequest, Principal connectedUser) {
        return gradeService.create(gradeRequest, connectedUser);
    }

    @GetMapping("/all")
    public List<Grade> findAll() {
        return gradeService.findAll();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleExceptionEmail(IllegalStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}