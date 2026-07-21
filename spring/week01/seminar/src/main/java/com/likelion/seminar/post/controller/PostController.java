package com.likelion.seminar.post.controller;

import com.likelion.seminar.post.dto.PostDTO;
import com.likelion.seminar.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")

public class PostController {
    private final PostService postService;

    @PostMapping
    public void createPost(@RequestBody PostDTO postDTO){
        postService.createPost(postDTO);

    }

    @GetMapping
    public List<PostDTO> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public PostDTO getPostByPathVariable(@PathVariable("id") int id){
        return postService.getPostById(id);
    }

    @GetMapping("/param")
    public PostDTO getPostByRequestParam(@RequestParam("id") int id){
        return postService.getPostById(id);
    }
}
