package com.icia.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {
    // abstract 추상 클래스
    @CreationTimestamp
    @Column(updatable = false) // 수정 불가능 설정
    private LocalDateTime createTime;// insert 시간

    @UpdateTimestamp
    @Column(insertable = false) // 인설트 할 때 값이 안들어감
    private LocalDateTime updateTime; // update 시간

}
