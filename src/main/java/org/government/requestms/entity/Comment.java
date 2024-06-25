package org.government.requestms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment", schema = "request_ms")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "email")
    private String email;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

}
