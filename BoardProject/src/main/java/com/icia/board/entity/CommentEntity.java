package com.icia.board.entity;

import com.icia.board.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    // 댓글번호 작성자 내용 원글
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long Id;
    // 원글의 게시글 번호를 참조하기 위한 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")// 부모테이블(참고하고자하는 테이블)PK값 (DB 이름으로)
    private BoardEntity boardEntity; // 참고하고자하는 테이블을 관리하는 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
    @Column
    private String commentWriter;
    @Column
    private String commentContents;


    public static CommentEntity toSaveEntity (CommentSaveDTO commentSaveDTO,
                                              BoardEntity boardEntity,MemberEntity memberEntity){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setCommentWriter(memberEntity.getMemberEmail());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setMemberEntity(memberEntity);
        return commentEntity;
    }
}
