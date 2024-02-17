package com.service.fruitfolio.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public Comment create(@RequestBody CommentRequest commentRequest) {
        return commentService.create(commentRequest);
    }

    @GetMapping("/all")
    public List<Comment> findAll() {
        return commentService.findAll();
    }
}
