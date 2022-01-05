package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("member",new MemberLoginDTO());
        return "/member/login";
    }

    @PostMapping("save")
    public String saveForm(@Validated @ModelAttribute("member") MemberSaveDTO msdto, BindingResult bindingResult) {
        System.out.println("ms = " + msdto + ", bindingResult = " + bindingResult);

        if(bindingResult.hasErrors()){
            return "/member/save";
        } else {
            ms.save(msdto);
            return "index";
        }
    }

    @PostMapping("login")
    public String loginForm(@Validated @ModelAttribute("member") MemberLoginDTO ml, BindingResult bindingResult) {
        System.out.println("MemberController.loginForm" + ml);

        if(bindingResult.hasErrors()){
            return "/member/login";
        }

        return "index";
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

}
