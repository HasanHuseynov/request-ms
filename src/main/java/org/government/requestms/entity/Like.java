package org.government.requestms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "likes", schema = "request_ms")
public class Like {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "like_id")
    private Long likeId;

    @Column(name = "email")
    private String email;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;


}
