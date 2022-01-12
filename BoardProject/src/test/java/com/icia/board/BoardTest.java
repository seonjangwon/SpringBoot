package com.icia.board;

import com.icia.board.dto.BoardDTO;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

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
}
