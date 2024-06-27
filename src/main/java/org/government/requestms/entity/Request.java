package org.government.requestms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.government.requestms.enums.Status;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request", schema = "request_ms")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "email")
    private String email;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

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
    @OneToMany(mappedBy = "request", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    @Audited
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Like> like;

    @Audited
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist()
    public void prePersist() {
        lastModified = null;
        lastModifiedBy = null;
    }

}
