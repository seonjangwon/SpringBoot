package com.icia.member.entity;

import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name="member_id")
    private Long id;

    @Column(length = 50,unique = true)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    private String memberName;

    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO){
        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.memberEmail=memberSaveDTO.getMemberEmail();
//        memberEntity.memberPassword=memberSaveDTO.getMemberPassword();
//        memberEntity.memberName=memberSaveDTO.getMemberName();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;
    }
}
