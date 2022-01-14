package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    Long save(BoardDTO boardDTO);

    List<BoardDetailDTO> findAll();

    BoardDetailDTO findById(Long boardId);

    void update(BoardDetailDTO boardDetailDTO);

    Page<BoardPagingDTO> paging(Pageable pageable);

    public List<BoardDetailDTO> search(String searchType, String keyword);

    void hits(Long boardId);
}
