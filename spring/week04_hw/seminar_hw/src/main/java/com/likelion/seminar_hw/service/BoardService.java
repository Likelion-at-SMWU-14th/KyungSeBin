package com.likelion.seminar_hw.service;

import com.likelion.seminar_hw.dto.BoardDTO;
import com.likelion.seminar_hw.entity.Board;
import com.likelion.seminar_hw.repository.BoardRepository;
import com.likelion.seminar_hw.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Transactional
    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = new Board(boardDTO.getName());

        Board savedBoard = boardRepository.save(board);

        return new BoardDTO(
                savedBoard.getId(),
                savedBoard.getName()
        );
    }
    //개별조회
    @Transactional(readOnly = true)
    public BoardDTO getBoard(Long id) {
        Board board = findBoard(id);

        return new BoardDTO(
                board.getId(),
                board.getName()
        );
    }
    // Board 전체 조회
    @Transactional(readOnly = true)
    public List<BoardDTO> getBoards() {
        return boardRepository.findAll()
                .stream()
                .map(board -> new BoardDTO(
                        board.getId(),
                        board.getName()
                ))
                .collect(Collectors.toList());
    }

    //존재하는 Board 조회
    private Board findBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 게시판입니다."
                ));
    }
    // Board 수정
    @Transactional
    public BoardDTO updateBoard(Long id, BoardDTO boardDTO) {
        Board board = findBoard(id);

        board.setName(boardDTO.getName());

        return new BoardDTO(
                board.getId(),
                board.getName()
        );
    }
    // Board 삭제
    @Transactional
    public void deleteBoard(Long id) {
        Board board = findBoard(id);

        boolean hasPosts = postRepository.existsByBoard_Id(id);

        if (hasPosts) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "게시글이 존재하는 게시판은 삭제할 수 없습니다."
            );
        }

        boardRepository.delete(board);
    }

}