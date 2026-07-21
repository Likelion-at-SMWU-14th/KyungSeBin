package com.likelion.seminar.post.service;

import com.likelion.seminar.post.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PostService {

    private final List<PostDTO> postDTOList;

    public void createPost(PostDTO postDTO){
        this.postDTOList.add(postDTO);
    }

    public List<PostDTO> getPosts(){
        return this.postDTOList;
    }

    public PostDTO getPostById(int id){
        return this.postDTOList.get(id);
    }
}
