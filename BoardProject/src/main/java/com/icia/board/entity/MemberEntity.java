package com.icia.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member_table")
public class MemberEntity {
    // id(Long), email, password, name
    // 회원 : 게시글 = 1 : N
    // 회원 : 댓글 = 1 : N
    // 게시글 : 댓글 = 1: N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    // cascade방법
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    // null방법
//    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
//    private List<BoardEntity> boardEntityList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<CommentEntity> commentEntityList = new ArrayList<>();

//    @PreRemove
//    private void preRemove(){
//        System.out.println("MemberEntity.preRemove");
//        boardEntityList.forEach(board -> board.setMemberEntity(null));
////        for (BoardEntity board: boardEntityList) {
////            board.setMemberEntity(null);
////        }
//        commentEntityList.forEach(comment -> comment.setMemberEntity(null));
//    }

}
