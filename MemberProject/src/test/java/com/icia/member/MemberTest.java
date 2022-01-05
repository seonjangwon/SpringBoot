package com.icia.member;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("testemail1");
        memberSaveDTO.setMemberPassword("testpassword1");
        memberSaveDTO.setMemberName("testname1");

        ms.save(memberSaveDTO);
    }
}
