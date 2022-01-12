package com.icia.board.dto;

import com.icia.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    @NotBlank(message = "작성자는 필수입니다")
    @Length(min = 2, max = 20,message = "2~20자로 적어주세요")
    private String boardWriter;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Length(min = 2, max = 20,message = "2~20자로 적어주세요")
    private String boardPassword;

    @NotBlank(message = "제목은 필수입니다")
    @Length(min = 2, max = 30,message = "2~30자로 적어주세요")
    private String boardTitle;

    @NotBlank(message = "내용은 필수 입니다")
    private String boardContents;

    private LocalDateTime boardDate;


}
