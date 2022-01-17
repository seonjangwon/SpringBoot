package com.icia.board;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;
import com.icia.board.entity.CommentEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.repository.CommentRepository;
import com.icia.board.service.BoardService;
import com.icia.board.service.CommenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class CommentTest {

    @Autowired
    private BoardService bs;

    @Autowired
    private CommenService cs;
    
    @Autowired
    private CommentRepository cr;
    
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("댓글 테스트")
    public void saveTest() {
        // given
        // 1 게시판 생성
        BoardDTO boardDTO = new BoardDTO("게시판작성자2",
                "게시판비번2", "게시판타이틀2",
                "게시판내용2", LocalDateTime.now());
        Long boardId = bs.save(boardDTO);

        // when
        // 2 댓글 생성
        CommentSaveDTO commentSaveDTO = new CommentSaveDTO(boardId,
                "댓글 작성자","댓글 내용");
        Long commentId = cs.save(commentSaveDTO);

        // then
    }
    
    @Test
    @Transactional
    @DisplayName("댓글 조회")
    public void findById(){
        CommentEntity commentEntity = cr.findById(4l).get();
        System.out.println("commentEntity.toString() = " + commentEntity.toString());
        System.out.println("commentEntity.getId() = " + commentEntity.getId());
        System.out.println("commentEntity.getCommentWriter() = " + commentEntity.getCommentWriter());
        System.out.println("commentEntity.getCommentContents() = " + commentEntity.getCommentContents());
        System.out.println("commentEntity.getBoardEntity() = " + commentEntity.getBoardEntity());
        System.out.println("commentEntity.getBoardEntity().getBoardTitle() = " + commentEntity.getBoardEntity().getBoardTitle());
    }


    @Test
    @Transactional
    @DisplayName("댓글 전체 조회")
    public void findAll(){
        List<CommentDetailDTO> commentDetailDTOList = cs.findAll(35l);
        for(CommentDetailDTO c : commentDetailDTOList){
            System.out.println(c.toString());
        }

    }
    
    
}
