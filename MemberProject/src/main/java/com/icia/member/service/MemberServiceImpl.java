package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        // 1. MemberSaveDTO -> MemberEntity에 옮기기 (MemberEntity의 saveMember메서드)
        // 2. MemberRepository의 save메서드 호출 하면서 MemberEntity 객체 전달
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        // 사용자가 입력한 이메일 중복 체크
        MemberEntity emailCheckResult = mr.findByMemberEmail(memberSaveDTO.getMemberEmail());
        // 이메일 중복 체크 결과가 null이 아니라면 예외를 발생시킴
        // 예외 종류 : IllegaStateException, 예외메세지 : 중복된 이메일 입니다
        if(emailCheckResult != null){
            throw new IllegalStateException("중복된 이메일 입니다") ;
        }
        return mr.save(memberEntity).getId();
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        // 1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴
        // 2. MemberEntity를 MemberDetailDTO로 바꿔서 컨트롤러로 리턴

        // 1
        MemberEntity member = mr.findById(memberId).get();
        // 2
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(member);
        System.out.println("memberDetailDTO.toString() = " + memberDetailDTO.toString());
        return memberDetailDTO;
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        // 1. 사용자가 입력한 이메일을 조건으로 DB에서 조회
        // select * from member_table where member_email = ?
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        // Email 확인
        if(memberEntity!=null) {
            // 2. 비밀번호 일치여부 확인
            if (memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        // List<MemberEntity> >> List<MemberDetail>
        List<MemberDetailDTO> memberDetailDTOList = new ArrayList<MemberDetailDTO>();
//        for(int i=0; i<memberEntityList.size(); i++){
//            memberDetailDTOList.add(MemberDetailDTO.toMemberDetailDTO(memberEntityList.get(i)));
//        }
        for (MemberEntity e : memberEntityList) {
            memberDetailDTOList.add(MemberDetailDTO.toMemberDetailDTO(e));
        }
        System.out.println("MemberServiceImpl.findAll"+memberDetailDTOList);
        return memberDetailDTOList;
    }
}
