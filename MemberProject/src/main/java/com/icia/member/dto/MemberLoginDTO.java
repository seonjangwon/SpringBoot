package com.icia.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginDTO {

    @NotBlank(message = "이메일은 필수 입니다다")
   private String memberEmail;
    @NotBlank(message = "비밀 번호를 꼭 적어주세요")
    private String memberPassword;

}
