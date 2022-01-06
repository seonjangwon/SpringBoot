package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // 파이널이 붙은 필드만 생성자를 만들어줌
public class MemberController {

    private final MemberService ms;


    @GetMapping("save")
    public String save(Model model) {
        model.addAttribute("member",new MemberSaveDTO());
        return "/member/save";
    }

    @PostMapping("save")
    public String saveForm(@Validated @ModelAttribute("member") MemberSaveDTO msdto, BindingResult bindingResult) {
        System.out.println("ms = " + msdto + ", bindingResult = " + bindingResult);

        if(bindingResult.hasErrors()){
            return "/member/save";
        }
        try {
            ms.save(msdto);
        } catch (IllegalStateException e){
            bindingResult.reject("emailCheck", e.getMessage());
            return "member/save";
        }
            return "index";

    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("member",new MemberLoginDTO());
        return "/member/login";
    }

    @PostMapping("login")
    public String loginForm(@Validated @ModelAttribute("member") MemberLoginDTO memberLoginDTO,
                            BindingResult bindingResult,
                            HttpSession session) {
        System.out.println("MemberController.loginForm" + memberLoginDTO);

        if(bindingResult.hasErrors()){
            return "/member/login";
        }
        System.out.println("MemberController.loginForm1");
        // boolean loginresult = ms.login(memberLoginDTO);
        // if (loginresult) {
        if(ms.login(memberLoginDTO)) {
            session.setAttribute("loginEmail",memberLoginDTO.getMemberEmail());
            return "redirect:/member/findAll";
        } else {
            System.out.println("MemberController.loginForm_err");
            bindingResult.reject("loginFail","이메일 또는 비밀번호가 틀립니다!");
            return "member/login";
        }
    }

    // 상세조회
    // /member/2, /member/15
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model){
        System.out.println("memberId = " + memberId);
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member",member);
        return "/member/detail";
    }

    // 목록조회(/member)
    @GetMapping
    public String findAll(Model model){
        System.out.println("MemberController.findAll");
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList",memberList);
        return "/member/findAll";
    }

}
