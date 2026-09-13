package com.likelion.seminar_hw;

import com.likelion.seminar_hw.entity.Author;
import com.likelion.seminar_hw.entity.Board;
import com.likelion.seminar_hw.entity.Comment;
import com.likelion.seminar_hw.repository.AuthorRepository;
import com.likelion.seminar_hw.repository.BoardRepository;
import com.likelion.seminar_hw.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("assignment-test")
@Transactional
class BoardMappingTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("작성자, 게시글, 댓글을 양방향으로 조회할 수 있다")
    void mappingTest() {
        //게시글 작성자와 댓글 작성자를 다르게 설정
        Author boardAuthor =
                authorRepository.save(new Author("숙명"));

        Author commentAuthor =
                authorRepository.save(new Author("멋사"));

        Board board = new Board(
                "연관관계 매핑 과제",
                "게시글과 댓글을 구현했습니다.",
                boardAuthor
        );

        Comment comment = board.addComment(
                "잘 읽었습니다!",
                commentAuthor
        );

        boardRepository.save(board);

        entityManager.flush();

        Long boardId = board.getId();
        Long commentId = comment.getId();
        Long boardAuthorId = boardAuthor.getId();
        Long commentAuthorId = commentAuthor.getId();

        entityManager.clear();

        //영속성 컨텍스트를 비운 뒤 DB에서 다시 조회
        Board foundBoard = boardRepository.findById(boardId)
                .orElseThrow();

        Comment foundComment = commentRepository.findById(commentId)
                .orElseThrow();

        Author foundBoardAuthor =
                authorRepository.findById(boardAuthorId)
                        .orElseThrow();

        Author foundCommentAuthor =
                authorRepository.findById(commentAuthorId)
                        .orElseThrow();

        //게시글 → 작성자
        assertThat(foundBoard.getAuthor().getId())
                .isEqualTo(boardAuthorId);

        //게시글 → 댓글
        assertThat(foundBoard.getComments())
                .extracting(Comment::getId)
                .containsExactly(commentId);

        //댓글 → 게시글
        assertThat(foundComment.getBoard().getId())
                .isEqualTo(boardId);

        //댓글 → 작성자
        assertThat(foundComment.getAuthor().getId())
                .isEqualTo(commentAuthorId);

        //작성자 → 게시글
        assertThat(foundBoardAuthor.getBoards())
                .extracting(Board::getId)
                .containsExactly(boardId);

        //작성자 → 댓글
        assertThat(foundCommentAuthor.getComments())
                .extracting(Comment::getId)
                .containsExactly(commentId);
    }

    @Test
    @DisplayName("게시글을 저장하면 댓글도 함께 저장된다")
    void cascadePersistTest() {
        // given
        Author author =
                authorRepository.save(new Author("숙명"));

        Board board = new Board(
                "영속성 전이",
                "댓글 저장을 확인합니다.",
                author
        );

        board.addComment("첫 번째 댓글", author);
        board.addComment("두 번째 댓글", author);

        boardRepository.save(board);

        entityManager.flush();

        Long boardId = board.getId();

        entityManager.clear();

        Board foundBoard = boardRepository.findById(boardId)
                .orElseThrow();

        assertThat(foundBoard.getComments()).hasSize(2);

        assertThat(foundBoard.getComments())
                .extracting(Comment::getContent)
                .containsExactlyInAnyOrder(
                        "첫 번째 댓글",
                        "두 번째 댓글"
                );

        for (Comment savedComment : foundBoard.getComments()) {
            assertThat(savedComment.getId()).isNotNull();

            assertThat(
                    commentRepository.existsById(savedComment.getId())
            ).isTrue();
        }
    }

    @Test
    @DisplayName("게시글 삭제 시 댓글은 삭제되고 작성자는 유지된다")
    void cascadeRemoveTest() {
        Author author =
                authorRepository.save(new Author("숙명"));

        Board board = new Board(
                "삭제할 게시글",
                "댓글도 함께 삭제되어야 합니다.",
                author
        );

        Comment comment = board.addComment("삭제될 댓글", author);

        boardRepository.save(board);

        entityManager.flush();

        Long authorId = author.getId();
        Long boardId = board.getId();
        Long commentId = comment.getId();

        entityManager.clear();

        Board foundBoard = boardRepository.findById(boardId)
                .orElseThrow();

        boardRepository.delete(foundBoard);

        entityManager.flush();
        entityManager.clear();

        assertThat(boardRepository.findById(boardId)).isEmpty();
        assertThat(commentRepository.findById(commentId)).isEmpty();

        // 작성자에게는 삭제가 전이되지 않는다!!
        assertThat(authorRepository.findById(authorId)).isPresent();
    }

    @Test
    @DisplayName("게시글의 댓글 목록에서 제거하면 해당 댓글만 삭제된다")
    void orphanRemovalTest() {
        Author author =
                authorRepository.save(new Author("숙명"));

        Board board = new Board(
                "고아 객체 삭제",
                "목록에서 댓글 하나를 제거합니다.",
                author
        );

        Comment firstComment =
                board.addComment("삭제할 댓글", author);

        Comment secondComment =
                board.addComment("남겨둘 댓글", author);

        boardRepository.save(board);

        entityManager.flush();

        Long boardId = board.getId();
        Long authorId = author.getId();
        Long firstCommentId = firstComment.getId();
        Long secondCommentId = secondComment.getId();

        entityManager.clear();

        Board foundBoard = boardRepository.findById(boardId)
                .orElseThrow();

        Comment target = foundBoard.getComments().stream()
                .filter(comment ->
                        comment.getId().equals(firstCommentId))
                .findFirst()
                .orElseThrow();

        foundBoard.removeComment(target);

        entityManager.flush();
        entityManager.clear();


        assertThat(commentRepository.findById(firstCommentId))
                .isEmpty();

        assertThat(commentRepository.findById(secondCommentId))
                .isPresent();

        assertThat(boardRepository.findById(boardId))
                .isPresent();

        assertThat(authorRepository.findById(authorId))
                .isPresent();

        Board reloadedBoard = boardRepository.findById(boardId)
                .orElseThrow();

        assertThat(reloadedBoard.getComments())
                .extracting(Comment::getId)
                .containsExactly(secondCommentId);
    }
}

//테스트 확인 목록
//작성자·게시글·댓글의 양방향 조회
//게시글 저장 시 댓글 저장 전이
//게시글 삭제 시 댓글 삭제 전이 및 작성자 유지
//댓글 목록에서 제거 시 고아 객체 삭제