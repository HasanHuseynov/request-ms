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
    private Long likeId;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    private Long count;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;


}
