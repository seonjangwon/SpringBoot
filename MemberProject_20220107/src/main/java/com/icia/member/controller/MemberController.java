package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Enumeration;
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

    @PostMapping("save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO){
        System.out.println("memberSaveDTO = " + memberSaveDTO);
        ms.save(memberSaveDTO);
        return "member/login";
    }

    @GetMapping("login")
    public String loginForm(@RequestParam(defaultValue = "/") String redirectURL,Model model) {
        System.out.println("MemberController.loginForm");
        model.addAttribute("redirectURL",redirectURL);
        return "/member/login";
    }

    @PostMapping("login")
    public String login(//@RequestParam(defaultValue = "/") String redirectURL,
                        @ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session){
        System.out.println("MemberController.login");
        System.out.println("memberLoginDTO = " + memberLoginDTO.getRedirectURL());
        if(ms.login(memberLoginDTO)){
            session.setAttribute(LOGIN_EMAIL,memberLoginDTO.getMemberEmail());
            // return "/member/mypage";
            return "redirect:"+memberLoginDTO.getRedirectURL();
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

    /*@DeleteMapping("{memberId}")
    @ResponseBody
    public List<MemberDetailDTO> deleteAjax(@PathVariable("memberId") Long memberId){
        ms.deleteById(memberId);
        List<MemberDetailDTO> memberDetailDTOList = ms.findAll();
        return memberDetailDTOList;
    }*/

    @DeleteMapping("{memberId}")
    public ResponseEntity deleteAjax(@PathVariable("memberId") Long memberId){
        ms.deleteById(memberId);
        //ResponseEntity : 데이터 & 상태코드를 함께 리턴할 수 있음.
        //@ResponseBody : 데이터를 리턴할 수 있음.
        // 200 리턴
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("update")
    public String updateForm(HttpSession session, Model model){
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO memberDetailDTO = ms.findByMemberEmail(memberEmail);
        model.addAttribute("member",memberDetailDTO);
        return "/member/update";
    }
    @PostMapping("update")
    public String update(@ModelAttribute MemberDetailDTO memberDetailDTO){
        Long memberId = ms.update(memberDetailDTO);
        return "redirect:/member/"+memberDetailDTO.getMemberId();
    }

    @PutMapping("{memberId}")
    public ResponseEntity updateAjax(@RequestBody MemberDetailDTO memberDetailDTO){
        Long memberId = ms.update(memberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity updateAjax2(@ModelAttribute MemberDetailDTO memberDetailDTO){
        Long memberId = ms.update(memberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
