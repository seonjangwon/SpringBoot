package com.icia.member;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("테스트")
    public void newMembers(){
        //given
        IntStream.rangeClosed(1,15).forEach(i->{
            ms.save(new MemberSaveDTO("mEamil"+i,"mPassword"+i,"mName"+i));
        });
        // when

    }
}
