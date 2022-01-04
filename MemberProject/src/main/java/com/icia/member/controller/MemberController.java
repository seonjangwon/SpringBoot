package com.icia.member.controller;

import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/*")
public class MemberController {

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
    public String saveForm(@Validated @ModelAttribute("member") MemberSaveDTO ms, BindingResult bindingResult) {
        System.out.println("ms = " + ms + ", bindingResult = " + bindingResult);

        if(bindingResult.hasErrors()){
            return "/member/save";
        } else {
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

}
