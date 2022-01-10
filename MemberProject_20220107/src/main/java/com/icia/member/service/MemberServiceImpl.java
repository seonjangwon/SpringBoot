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
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        System.out.println("memberSaveDTO = " + memberSaveDTO);
        return mr.save(memberEntity).getId();
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        System.out.println("MemberServiceImpl.login"+memberLoginDTO);
        if(memberEntity!=null){
            if(memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword())){
                return true;
            }
        }

        return false;
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDetailDTO> memberDetailDTOList = new ArrayList<>();
        for(MemberEntity e: memberEntityList){
            memberDetailDTOList.add(MemberDetailDTO.memberDetail(e));
        }
        return memberDetailDTOList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        return MemberDetailDTO.memberDetail(mr.findById(memberId).get());
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }

    @Override
    public MemberDetailDTO findByMemberEmail(String loginEmail) {
        return MemberDetailDTO.memberDetail(mr.findByMemberEmail(loginEmail));
    }

    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        // update 처리시 save 메서드 호출.
        // memberDetailDTO > MemberEntity
        return mr.save(MemberEntity.toUpdateMember(memberDetailDTO)).getId();
    }
}
