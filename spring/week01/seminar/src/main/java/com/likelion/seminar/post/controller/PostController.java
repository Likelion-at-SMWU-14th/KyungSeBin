package com.likelion.seminar.post.controller;

import com.likelion.seminar.post.dto.PostDTO;
import com.likelion.seminar.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")

public class PostController {
    private final PostService postService;
    @PostMapping
    public void createPost(@RequestBody PostDTO postDTO){
        postService.createPost(postDTO);

    }
}
