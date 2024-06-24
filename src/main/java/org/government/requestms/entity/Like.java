package org.government.requestms.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(
    name = "like", schema = "request_ms"
)
public class Like {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long likeId;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    @ManyToOne
    private Request request;


}
