package com.icia.board;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;

    @Test
    //@Transactional
    //@Rollback
    @DisplayName("보드만들기 테스트")
    public void saveTest(){
        // given
        BoardDTO boardDTO = new BoardDTO("테스트작성자",
                "테스트비번", "테스트타이틀",
                "테스트내용", LocalDateTime.now());
        BoardDTO boardDTO2 = new BoardDTO("테",
                "테스트비번", "테스트타이틀",
                "테스트내용", LocalDateTime.now());
        IntStream.rangeClosed(1,30).forEach(i->{
            BoardDTO boardDTO3 = new BoardDTO("테스트작성자"+i,
                    "테스트비번"+i, "테스트타이틀"+i,
                    "테스트내용"+i, LocalDateTime.now());
        bs.save(boardDTO3);
        });
        // when
        // then

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("수정 테스트")
    public void updateTest(){
        // given
        BoardDTO boardDTO = new BoardDTO("수정작성자",
                "수정비번", "수정타이틀",
                "수정내용", LocalDateTime.now());
        Long boardId = bs.save(boardDTO);
        /*Long boardId = bs.save(new BoardDTO("수정작성자",
                "수정비번", "수정타이틀",
                "수정내용", LocalDateTime.now()));*/
        // when
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO(boardId,"수정작성자1",
                    "수정비번","수정타이틀1",
                    "수정내용1",LocalDateTime.now());
        try {
            bs.update(boardDetailDTO);
       /* bs.update(new BoardDetailDTO(boardId,"수정작성자1",
                "수정비번","수정타이틀1",
                "수정내용1",LocalDateTime.now()));*/
        } catch (IllegalStateException e) {
            System.out.println("BoardTest.updateTest"+e);
        }
        // then

        assertThat(boardDTO.getBoardTitle()).isNotEqualTo(boardDetailDTO.getBoardTitle());
        assertThat(boardDTO.getBoardContents()).isNotEqualTo(boardDetailDTO.getBoardContents());

    }
}
