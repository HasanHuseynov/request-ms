package org.government.requestms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "likes", schema = "request_ms")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Like {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "like_id")
    private Long likeId;

    @Column(name = "email")
    private String email;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "create_by", nullable = false, updatable = false)
    @CreatedBy
    private String createBy;

    @Column(name = "last_modified")
    @LastModifiedDate
    private LocalDateTime lastModified;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Audited
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @PrePersist()
    public void prePersist() {
        lastModified = null;
        lastModifiedBy = null;
    }


}
