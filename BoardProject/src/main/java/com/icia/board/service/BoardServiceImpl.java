package com.icia.board.service;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository br;

    @Override
    public Long save(BoardDTO boardDTO) {
        return br.save(new BoardEntity().toSaveEntity(boardDTO)).getId();
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

    @Override
    public void update(BoardDetailDTO boardDetailDTO) {
        BoardDetailDTO boardDetailDTO1 = findById(boardDetailDTO.getBoardId());
        if(boardDetailDTO1.getBoardPassword().equals(boardDetailDTO.getBoardPassword())){
            br.save(BoardEntity.toUpdateEntity(boardDetailDTO));
        } else
            throw new IllegalStateException("비밀번호가 틀립니다.");

    }

    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요펑한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청한 페이지에서 1을 뺀다.
//        page = page -1;
        page = (page==1)? 0: (page-1);
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT,
                Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardPagingDTO> boardList = boardEntities.map(
                // Entity를 담기 위한 반복용 변수(Entity객체) -> DTO로 변환
                board -> new BoardPagingDTO(board.getId(), board.getBoardWriter(),
                        board.getBoardTitle(), board.getBoardHits())
        );
        return boardList;
    }

    @Override
    public List<BoardDetailDTO> search(String searchType, String keyword){
        List<BoardEntity> searchList = new ArrayList<>();
        if(searchType=="title"){
            searchList = br.findByBoardTitleContaining(keyword);
        } else if (searchType=="writer"){
            searchList = br.findByBoardWriterContaining(keyword);
        } else {
            searchList = br.findByBoardContentsContaining(keyword);
        }
        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for(BoardEntity b:searchList){
            boardDetailDTOList.add(BoardDetailDTO.toBoardDetailDTO(b));
        }
        return boardDetailDTOList;
    }

    @Override
    public void hits(Long boardId) {
        br.hits(boardId);
    }
}
