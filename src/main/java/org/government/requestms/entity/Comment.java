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
    private Long commentId;
    private String email;
    private String commentText;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

}
