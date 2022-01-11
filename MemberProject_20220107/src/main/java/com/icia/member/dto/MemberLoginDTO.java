package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDTO {
    private String memberEmail;
    private String memberPassword;
    private String redirectURL;

    public MemberLoginDTO (String memberEmail, String memberPassword){
        this.memberEmail=memberEmail;
        this.memberPassword=memberPassword;
    }
}
