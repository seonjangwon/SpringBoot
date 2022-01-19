package com.sjw.memberboard.dto;

import com.sjw.memberboard.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDTO {

    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private String boardFilename;
    private LocalDateTime boardTime;

    public static BoardDetailDTO toDetailDTO(BoardEntity boardEntity){
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setId(boardEntity.getId());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBoardHits(boardDetailDTO.getBoardHits());
        if(boardEntity.getUpdateTime()==null){
            boardDetailDTO.setBoardTime(boardEntity.getCreateTime());
        } else {
            boardDetailDTO.setBoardTime(boardEntity.getUpdateTime());
        }
        return boardDetailDTO;
    }
}
