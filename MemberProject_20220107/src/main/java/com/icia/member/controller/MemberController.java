package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.icia.member.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final이 있는 필드로만 생성자를 만들어줌
public class MemberController {
    private final MemberService ms;

    @GetMapping("save")
    public String saveForm(){
        return "/member/save";
    }

    @PostMapping
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO){
        Long memberId = ms.save(memberSaveDTO);
        return "member/login";
    }

    @GetMapping("login")
    public String loginForm(){
        return "/member/login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session){

        if(ms.login(memberLoginDTO)){
            session.setAttribute(LOGIN_EMAIL,memberLoginDTO.getMemberEamil());
            return "/member/findAll";
        }
        return "/member/login";
    }

    @GetMapping
    public String findAll(Model model){
        List<MemberDetailDTO> memberDetailDTOList = ms.findAll();
        model.addAttribute("memberList",memberDetailDTOList);
        return "/member/findAll";
    }

    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model){
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);
        System.out.println("memberId = " + memberId+"memberDetailDTO = "+ memberDetailDTO);
        model.addAttribute("member",memberDetailDTO);
        return "/member/detail";
    }

    @GetMapping("delete/{memberId}")
    public String deleteById(@PathVariable("memberId") Long memberId){
        ms.deleteById(memberId);
        return "redirect:/member/";
    }

    @PostMapping("{memberId}")
    @ResponseBody
    public MemberDetailDTO findByIdAjax(@PathVariable("memberId") Long memberId){
        MemberDetailDTO memberDetailDTO = ms.findById(memberId);
        System.out.println("memberId = " + memberId+"memberDetailDTO = "+ memberDetailDTO);
        return memberDetailDTO;
    }
}
