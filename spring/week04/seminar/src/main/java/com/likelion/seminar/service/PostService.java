package com.likelion.seminar.service;

import com.likelion.seminar.dto.PostDTO;
import com.likelion.seminar.entity.Board;
import com.likelion.seminar.entity.Post;
import com.likelion.seminar.repository.BoardRepository;
import com.likelion.seminar.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createPost(PostDTO postDTO){
        Board board=boardRepository.findById(postDTO.getBoardId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 게시판입니다."));

        Post post=new Post(
                postDTO.getTitle(),
                postDTO.getContent(),
                board
        );

        postRepository.save(post);
    }

    public PostDTO getPost(Long id){
        Post post=postRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 게시물입니다."));

        return new PostDTO(
                id,
                post.getTitle(),
                post.getContent(),
                post.getBoard().getId()
        );
    }

    /** 전체 Post 조회 */
    public List<PostDTO> getPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : posts) {
            postDTOList.add(
                    new PostDTO(
                            post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getBoard().getId()
                    )
            );
        }

        return postDTOList;
    }

    /** Post 수정 */
    @Transactional
    public void updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        // postRepository.save(post);
    }

    /** Post 삭제 */
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."));

        postRepository.delete(post);
    }
}
