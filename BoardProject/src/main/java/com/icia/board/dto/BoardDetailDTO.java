package com.icia.board.dto;

import com.icia.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDTO {
    private Long boardId;
    private String boardWriter;
    private String boardPassword;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime boardDate;

    public static BoardDetailDTO toBoardDetailDTO(BoardEntity boardEntity){
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO(
                boardEntity.getId(), boardEntity.getBoardWriter(),
                boardEntity.getBoardPassword(), boardEntity.getBoardTitle(),
                boardEntity.getBoardContents(), boardEntity.getBoardDate()
        );

        return boardDetailDTO;
    }
}
