package com.sample.ams.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity implements Serializable {

/*
    @CreatedDate
    @Column(name = "D_CREATED_DATE",nullable = false , updatable = false)
    @NotAudited
    private Date createdDate ;
*/

    @LastModifiedDate
    @Column(name = "D_LAST_MODIFIED_DATE")
    @NotAudited
    private Date lastModifiedDate;

}
