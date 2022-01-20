package com.sjw.memberboard.repository;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    @Transactional
    @Modifying
    @Query("update BoardEntity b set b.boardHits = b.boardHits + 1 where  b.id = :boardId ")
    void hits(Long boardId);

    Page<BoardEntity> findByBoardTitleContaining(String keyword, Pageable pageable);
    Page<BoardEntity> findByBoardWriterContaining(String keyword, Pageable pageable);
    Page<BoardEntity> findByBoardContentsContaining(String keyword, Pageable pageable);
}
