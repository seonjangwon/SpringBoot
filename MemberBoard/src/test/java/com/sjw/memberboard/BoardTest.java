package com.sjw.memberboard;

import com.sjw.memberboard.dto.BoardSaveDTO;
import com.sjw.memberboard.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;

    @Test
    @DisplayName("게시글 생성용")
    public void saveTest() {
        IntStream.rangeClosed(1,40).forEach(i->{
            BoardSaveDTO boardSaveDTO = new BoardSaveDTO();
            boardSaveDTO.setBoardTitle("testTitle"+i);
            boardSaveDTO.setBoardWriter("123");
            boardSaveDTO.setBoardContents("testContents"+i);
            try {
                bs.save(boardSaveDTO);
            } catch (IOException e) {
            }
        });
    }
}
