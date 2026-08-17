package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.dto.BoardDTO;
import com.likelion.seminar_hw.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    //Board 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDTO createBoard(@RequestBody BoardDTO boardDTO) {
        return boardService.createBoard(boardDTO);
    }

    //Board 개별 조회
    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    //Board 전체 조회
    @GetMapping
    public List<BoardDTO> getBoards() {
        return boardService.getBoards();
    }

    //Board 수정
   @PutMapping("/{id}")
    public BoardDTO updateBoard(
            @PathVariable Long id,
            @RequestBody BoardDTO boardDTO
    ) {
        return boardService.updateBoard(id, boardDTO);
    }

    // Board 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }
}