package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;

import java.util.List;

public interface BoardService {
    Long save(BoardDTO boardDTO);

    List<BoardDetailDTO> findAll();

    BoardDetailDTO findById(Long boardId);

    void update(BoardDetailDTO boardDetailDTO);
}
