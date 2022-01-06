package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDTO {

    @NotBlank(message = "이메일은 필수 입니다다")
   private String memberEmail;
    @NotBlank(message = "비밀 번호를 꼭 적어주세요")
    private String memberPassword;

}
