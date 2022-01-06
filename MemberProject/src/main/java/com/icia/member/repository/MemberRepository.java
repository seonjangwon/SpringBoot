package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당 Entity클래스 이름, PK타입>
// Jpa를 상속받아서 사용하면 Repository어노테이션은 필요가 없다
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 이메일을 조건으로 회원 조회 select * from member_table where member_email = ?
    // 메서드 리턴 타입 : MemberEntity
    // 이름 : findByMemberEmail
    // 매개변수 : String memberEmail

    MemberEntity findByMemberEmail(String memberEmail);

}
