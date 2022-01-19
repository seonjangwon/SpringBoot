package com.sjw.memberboard.service;

import com.sjw.memberboard.dto.BoardDetailDTO;
import com.sjw.memberboard.dto.BoardSaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BoardService {
    void save(BoardSaveDTO boardSaveDTO) throws IOException;

    Page<BoardDetailDTO> findAll(Pageable pageable);
}
