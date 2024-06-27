package org.government.requestms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.government.requestms.audit.CustomRevisionListener;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Getter
@Setter
@Entity
@Table(name = "rev_info", schema = "request_ms")
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "id", nullable = false)
    private Long id;

    @RevisionTimestamp
    @Column(name = "timestamp", nullable = false)
    private long timestamp;

    @Column(name = "email")
    private String email;
}
