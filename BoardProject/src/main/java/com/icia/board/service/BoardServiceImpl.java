package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository br;

    @Override
    public void save(BoardDTO boardDTO) {
        br.save(new BoardEntity().toSaveEntity(boardDTO));
    }

    @Override
    public List<BoardDetailDTO> findAll() {
        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        List<BoardEntity> boardEntityList = br.findAll();

        for(BoardEntity b : boardEntityList){
            boardDetailDTOList.add(new BoardDetailDTO().toBoardDetailDTO(b));
        }

        return boardDetailDTOList;
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        System.out.println("BoardServiceImpl.findById");
        return new  BoardDetailDTO().toBoardDetailDTO(br.findById(boardId).get());
    }
}