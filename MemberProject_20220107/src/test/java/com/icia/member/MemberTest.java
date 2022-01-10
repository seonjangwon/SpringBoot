package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("테스트")
    public void newMembers(){
        //given
        IntStream.rangeClosed(16,30).forEach(i->{
            ms.save(new MemberSaveDTO("mEamil"+i,"mPassword"+i,"mName"+i));
        });
        // when

    }
    // 회원 삭제 테스트 코드를 만들어봅시다
    //
    @Test
    @Transactional
    @Rollback
    @DisplayName("삭제 테스트")
    public void deleteMember(){
        // given
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("deleteEmail",
                "deletePassword","deleteName");
        Long memberId = ms.save(memberSaveDTO);
        System.out.println(memberSaveDTO);
        System.out.println(memberId);
        // when
        ms.deleteById(memberId);
        // then
        boolean result = ms.login(new MemberLoginDTO(memberSaveDTO.getMemberEmail(), memberSaveDTO.getMemberPassword()));
        assertThat(result).isEqualTo(false);

        // MemberDetailDTO memberDetailDTO = ms.findById(memberId);
        assertThrows(NoSuchElementException.class, () ->{
            assertThat(ms.findById(memberId)).isNull();
        });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("업데이트 테스트")
    public void updateMember(){
        // given
        MemberSaveDTO memberSaveDTO1 = new MemberSaveDTO("updateEmail",
                "updatePassword","updateName");
        Long memberId = ms.save(memberSaveDTO1);
        MemberDetailDTO memberDetailDTO2 = new MemberDetailDTO(memberId,"updateEmail",
                "updatePassword","updateName2" );
        // when
        System.out.println(memberDetailDTO2);
        ms.update(memberDetailDTO2);
        MemberDetailDTO memberDetailDTO3 = ms.findById(memberId);
        System.out.println(memberDetailDTO3);
        // then
        assertThat(memberSaveDTO1.getMemberName()).isNotEqualTo(memberDetailDTO3.getMemberName());
    }
}
