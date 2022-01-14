package com.icia.board;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;
    @Autowired
    private BoardRepository br;

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
                    "수정내용1",LocalDateTime.now(),0);
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

    @Test
    @DisplayName("삼항연산자")
    public  void test1(){
        int num = 10;
        int num2 = 0;
        if (num==10){
            if(num==5){
                num2=1;
            }else {
                num2=2;
            }
        } else {
            num2=100;
        }
        num2 = (num==10)? (num2=(num==5)? 1:2):100;
    }
    
    @Test
    @Transactional
    @Rollback
    @DisplayName("페이징 테스트")
    public void pagingTest() {
        int page= 5;
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page,
                PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청한 페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글 갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청 페이지 (jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보이는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 처음 페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 인지

        // Page<BoardEntity> boardEntities -> Page<BoardPagingDTO>
        // map() : 엔티티에 담긴 페이지객체를 DTO가 담긴 페이지 객체로 변환해주는 역활
        Page<BoardPagingDTO> boardList = boardEntities.map(
                // Entity를 담기 위한 반복용 변수(Entity객체) -> DTO로 변환
                board -> new BoardPagingDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(),board.getBoardHits())
        );

        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청한 페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글 갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청 페이지 (jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보이는 글 갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 처음 페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막 페이지 인지
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("searchTest")
    public void searchTest(){

        List<BoardDetailDTO> searchT = bs.search("title", "1");
        List<BoardDetailDTO> searchW = bs.search("writer", "2");
        List<BoardDetailDTO> searchC = bs.search("contents", "3");

        System.out.println("search = " + searchT);
        System.out.println("search = " + searchW);
        System.out.println("search = " + searchC);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("조회수 테스트")
    public void hitsTest(){
        BoardDTO boardDTO = new BoardDTO("조회수작성자",
                "조회수비번", "조회수타이틀",
                "조회수내용", LocalDateTime.now());
        Long boardId = bs.save(boardDTO);

        bs.hits(boardId);

        BoardDetailDTO hits = bs.findById(boardId);

        System.out.println("hits.getBoardHits() = " + hits.getBoardHits());
    }

}
