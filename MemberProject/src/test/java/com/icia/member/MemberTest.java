package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {
    // 서비스에 만든 메서드들이 제대로 작동을 하는지
    // MemberServiceImpl.save() 메서드가 잘 동작하는지를 테스트

    // 회원가입테스트
    // save.html에서 회원정보 입력 후 가입클릭
    // DB 확인

    @Autowired
    private MemberService ms;

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("testemail1");
        memberSaveDTO.setMemberPassword("testpassword1");
        memberSaveDTO.setMemberName("testname1");

        ms.save(memberSaveDTO);
    }

    @Test
    @Transactional // 테스트 시작할 때 새로운 트랜젝션 시작
    @Rollback // 테스트 종료 후 롤백 수행
    @DisplayName("상세조회 테스트")
    public void memberDetailTest(){
        // given : 테스트 조건 설정
            // 1. 새로운 회원을 등록하고 해당 회원의 번호(member_id)를가져옴
            // 1.1 테스터용 데이터 객체 생성
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("조회용이메일1", "조회용비번","조회용이름");
            // 1.2 테스터용 데이터를 DB에 저장하고 memberId를 가져옴
        Long memberId = ms.save(memberSaveDTO);
        // when : 테스트 수행
            // 2. 위에서 가져온 회원번호를 가지고 조회 기능 수행
        MemberDetailDTO findMember = ms.findById(memberId);
        // then : 테스트 결과 검등
            // 3. 1번에서 가입한 회원의 정보와 2번에서 조회한 회원의 정보가 일치하면 테스트 통과 일치하지 않으면 테스트 실패
        // memberSaveDTO의 이메일 값과 findMember의 이메일 값이 일치하는지 확인
        assertThat(memberSaveDTO.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
    }
}
