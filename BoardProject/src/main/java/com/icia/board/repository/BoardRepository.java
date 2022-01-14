package com.icia.board.repository;

import com.icia.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    @Transactional
    @Modifying
    @Query("update BoardEntity as b set b.boardHits = b.boardHits + 1 where b.id = :boardId")
    void hits(Long boardId);


    //List<BoardEntity> findBySearchTypeContainingKeyword(String keyword, String searchType);
    List<BoardEntity> findByBoardTitleContaining(String keyword);
    List<BoardEntity> findByBoardWriterContaining(String keyword);
    List<BoardEntity> findByBoardContentsContaining(String keyword);
}
