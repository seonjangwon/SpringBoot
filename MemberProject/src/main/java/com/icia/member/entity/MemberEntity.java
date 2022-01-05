package com.icia.member.entity;

import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity {

    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name="member_id") // 컬럼 이름 지정 안하면 변수명으로 자동
    private Long id; // 소문자 long으로 사용하면 값이 없으면 0이 들어온다 그래서 클래스Long으로 하면 null값으로 들어온다

    // memberEmail : 크기 50, unique
    @Column(length = 50, unique = true)
    private String memberEmail;

    // memberPassword : 크기 20*
    @Column(length=20)
    private String memberPassword;


    private String memberName; // 길이 지정을 안하면 255로

    //DTO클래스 객체를 전달 받아 entity 클래스 필드 값으로 세팅하고 Entity객체를 리턴하는 메서드 선언
    // stadtic 메서드(정적메서드) : 클래스 메서드, 객체를 안만들고도 가능

    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;
    }
}
