package com.icia.board.entity;

import com.icia.board.dto.BoardDTO;
import com.icia.board.dto.BoardDetailDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto
    @Column(name = "board_id")
    private Long id;

    @Column(length = 20)
    private String boardWriter;

    @Column(length = 20)
    private String boardPassword;

    @Column(length = 30)
    private String boardTitle;

    private String boardContents;

    private int boardHits;

    @OneToMany(mappedBy = "boardEntity" , cascade = CascadeType.ALL, orphanRemoval = true, fetch =  FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;



//    private LocalDateTime boardDate;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO, MemberEntity memberEntity){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setMemberEntity(memberEntity);
//        boardEntity.setBoardDate(LocalDateTime.now());
        return boardEntity;
    }
    public static BoardEntity toUpdateEntity(BoardDetailDTO boardDetailDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDetailDTO.getBoardId());
        boardEntity.setBoardWriter(boardDetailDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardDetailDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardDetailDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDetailDTO.getBoardContents());
        boardEntity.setBoardHits(boardDetailDTO.getBoardHits());
//        boardEntity.setBoardDate(LocalDateTime.now());
        return boardEntity;
    }
}
