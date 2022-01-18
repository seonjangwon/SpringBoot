package com.sjw.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailDTO {

    private Long boardId;
    private String memberEmail;
    private String commentContents;
    private LocalDateTime commentTime;
}
