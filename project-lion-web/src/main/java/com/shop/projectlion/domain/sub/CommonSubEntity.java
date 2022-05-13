package com.shop.projectlion.domain.sub;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonSubEntity {

    @Column(name = "update_time",nullable = false)
    @LastModifiedDate
    private LocalDateTime updateTime;

    @Column(name = "create_time", updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "created_by",nullable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by", updatable = false, nullable = false)
    @LastModifiedBy
    private String modifiedBy;

}
